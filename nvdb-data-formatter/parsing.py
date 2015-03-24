import json
import utm


def show_at_google_maps(coordinate):
    lat, lon = coordinate
    print "https://www.google.no/maps/@"+str(lat)+","+str(lon)+",21z?hl=no"


class RoadPart:
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

vegnett = json.load(open("nvdb-data/vegnett_trondheim.json", "r"))


class NodeTree:
    def __init__(self):
        self.nodes = {}

    def add_node(self, node_id, road):
        if node_id in self.nodes:
            self.nodes[node_id].append(road)
        else:
            self.nodes[node_id] = [road]


nodes = NodeTree()

vegreferanser = json.load(open("nvdb-data/532.json", "r"))

veglenke_ids = set()
for vegreferanse in vegreferanser.values():
    veglenke_ids.add(vegreferanse['lokasjon']['veglenker'][0]['id'])


class Edge:
    def __init__(self, veglenke_id, from_node, to_node):
        # edge id (to be converted into EDGE INDEX)
        self.veglenke_id = veglenke_id

        # FROM NODE
        self.from_node = from_node

        # TO NODE
        self.to_node = to_node

        # traversal cost
        # TODO

        # demand
        # TODO

        # service cost
        # TODO

edges = []

for feature in vegnett['features']:
    veglenke_id = feature['properties']['lokalId']
    coordinates = feature['geometry']['coordinates']

    road = RoadPart(veglenke_id, coordinates)

    if veglenke_id == 41628:
        print json.dumps(feature, indent=4)

    # show_at_google_maps(road.coordinates_as_latlon[0])

    # road.show_veg_lenke_in_nvdb()

    startnode = feature['properties']['startnode']['Identifikator']['lokalId']
    sluttnode = feature['properties']['sluttnode']['Identifikator']['lokalId']

    edges.append(Edge(veglenke_id, startnode, sluttnode))

    nodes.add_node(startnode, road)
    nodes.add_node(sluttnode, road)

print edges[0].__dict__

# max = 0
# for (node_id, roads) in nodes.nodes.items():
#     if len(roads) > max:
#         max = len(roads)
#         print "Node: ", node_id
#         print "Roads crossing: ", len(roads)
#         print [road.veglenke_id for road in roads]
#         show_at_google_maps(roads[0].coordinates_as_latlon[0])
