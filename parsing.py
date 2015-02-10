import json
import utm


def show_at_google_maps(coordinate):
    lat, lon = coordinate
    print "https://www.google.no/maps/@"+str(lat)+","+str(lon)+",21z?hl=no"
class Road:
    def __init__(self, veglenke_id, coordinates):
        self.veglenke_id = veglenke_id
        self.coordinates = coordinates

    @property
    def coordinates_as_latlon(self):
        return [utm.to_latlon(c[0], c[1], 33, 'U') for c in self.coordinates]

    def __str__(self):
        return self.__dict__.__str__()

    def __repr__(self):
        return self.__str__()

    def show_veg_lenke_in_nvdb(self):
        print "https://www.vegvesen.no/nvdb/api/vegreferanse/lenke?id="+str(self.veglenke_id)+"&posisjon=0.0"
        print "https://www.vegvesen.no/nvdb/api/vegreferanse/lenke?id="+str(self.veglenke_id)+"&posisjon=1.0"

vegnett = json.load(open("vegnett_trondheim.json", "r"))


class NodeTree:
    def __init__(self):
        self.nodes = {}

    def add_node(self, node_id, road):
        if node_id in self.nodes:
            self.nodes[node_id].append(road)
        else:
            self.nodes[node_id] = [road]


nodes = NodeTree()

for feature in vegnett['features']:
    veglenke_id = feature['properties']['lokalId']
    coordinates = feature['geometry']['coordinates']

    road = Road(veglenke_id, coordinates)

    show_at_google_maps(road.coordinates_as_latlon[0])

    road.show_veg_lenke_in_nvdb()

    startnode = feature['properties']['startnode']['Identifikator']['lokalId']
    sluttnode = feature['properties']['sluttnode']['Identifikator']['lokalId']

    nodes.add_node(startnode, road)
    nodes.add_node(sluttnode, road)

for (node_id, roads) in nodes.nodes.items():
    print node_id, len(roads)
