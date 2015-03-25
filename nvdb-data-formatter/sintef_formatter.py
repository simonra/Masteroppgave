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

print edge_dict.values()[0]
