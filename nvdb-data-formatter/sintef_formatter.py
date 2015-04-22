import json

edges = json.load(open("edges.json", "r"))
vegreferanser = json.load(open("nvdb-data/532.json", "r"))
fartsgrenser = json.load(open("nvdb-data/105.json", "r"))

print edges[0]
print json.dumps(fartsgrenser.values()[0], indent=4)

edge_dict = {edge['veglenke_id']: edge for edge in edges}

SPEED_LIMIT_ID = 2021

max = 0
links = set()
for fartsgrense in fartsgrenser.values():
    lenker = fartsgrense['lokasjon']['veglenker']
    ids = [lenke['id'] for lenke in lenker]
    if len(ids) > max:
        max = len(ids)
        print max, ids
    links |= set(ids)

    speed_limit = next(x['verdi'] for x in fartsgrense['egenskaper'] if x['id'] == SPEED_LIMIT_ID)
    distance_in_meters = fartsgrense['strekningslengde']

    # TODO: this might be wrong if distance_in_meters doesn't correspond to veglenke length
    for id in ids:
        if id not in edge_dict:
            # TODO: what do?
            continue
        edge_dict[id]['speed_limit'] = speed_limit
        edge_dict[id]['distance_in_meters'] = distance_in_meters

print len(links)
print len(set([edge['veglenke_id'] for edge in edges]))

edge_id_to_veglenke_id = {el[0]: el[1] for el in list(enumerate([edge['veglenke_id'] for edge in edge_dict.values()], start=1))}
veglenke_id_to_edge_id = {el[1]: el[0] for el in list(enumerate([edge['veglenke_id'] for edge in edge_dict.values()], start=1))}

with open('edge_id_to_veglenke_id.json', 'w') as outfile:
    json.dump(edge_id_to_veglenke_id, outfile)

with open('veglenke_id_to_edge_id.json', 'w') as outfile:
    json.dump(veglenke_id_to_edge_id, outfile)

nodes = set()
for edge in edges:
    nodes.add(edge['to_node'])
    nodes.add(edge['from_node'])

node_id_to_nvdb_node_id = {el[0]: el[1] for el in list(enumerate(nodes, start=1))}
nvdb_node_id_to_node_id = {el[1]: el[0] for el in list(enumerate(nodes, start=1))}

with open('node_id_to_nvdb_node_id.json', 'w') as outfile:
    json.dump(node_id_to_nvdb_node_id, outfile)

with open('nvdb_node_id_to_node_id.json', 'w') as outfile:
    json.dump(nvdb_node_id_to_node_id, outfile)


def node_to_sintef_format(node):
    # NODE INDEX, DEMAND, SERVICE COST
    print str(nvdb_node_id_to_node_id[node])+"\t1\t0"

def edge_to_sintef_format(edge):
    # EDGE INDEX, FROM NODE, TO NODE, TRAVERSAL COST, DEMAND, SERVICE COST
    if 'distance_in_meters' in edge and 'speed_limit' in edge:
        print str(veglenke_id_to_edge_id[edge['veglenke_id']])+"\t"+str(nvdb_node_id_to_node_id[edge['from_node']])+"\t"+str(nvdb_node_id_to_node_id[edge['to_node']])+"\t"+str(edge['distance_in_meters']*1000.0 / float(edge['speed_limit']))+"\t1\t"+str(edge['distance_in_meters']*1000)
    else:
        # TODO: what do
        print str(veglenke_id_to_edge_id[edge['veglenke_id']])+"\t"+str(nvdb_node_id_to_node_id[edge['from_node']])+"\t"+ str(nvdb_node_id_to_node_id[edge['to_node']])+"\t0\t0\t0"

print "Name:\tnvdb-data"
print "Optimal value:\t-1"
print "#Vehicles:\t-1"
print "Capacity:\t1234" # TODO
print "Depot Node:\t1" #TODO
print "#Nodes:\t\t"+str(len(nodes))
print "#Edges:\t\t"+str(len(edges))
print "#Arcs:\t\t0" # TODO
print "#Required N:\t"+str(len(nodes))
print "#Required E:\t"+str(len(edges))
print "#Required A:\t0"
print
print "% Required nodes:  Ni q_i s_ij"
for node in nodes:
    node_to_sintef_format(node)

print ""
print "% Required edges: Ek i j q_ij c_ij s_ij "
for edge in edge_dict.values():
    edge_to_sintef_format(edge)

print ""
print "% Non-required edges: NrEl i j c_ij"
print
print "% Required arcs: Ar i j q_ij c_ij"
print
print "% Non-required arcs: NrAs i j c_ij"
print
