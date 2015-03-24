import json
import math

import requests


TRONDHEIM_KOMMUNE = 1601
VEGREFERANSE = 532
VEGBREDDE = 583
FARTSGRENSE = 105

OBJECTS_PER_FETCH = 1000

def get_road_objects(object_type_id, count=None):
    query = {
        "lokasjon": {
            "kommune": [TRONDHEIM_KOMMUNE]
        },
        "objektTyper": [{
            "id": object_type_id,
            "antall": 0,
        }]
    }

    if not count:
        r = requests.get("https://www.vegvesen.no/nvdb/api/sok?kriterie="+json.dumps(query))

        initial_result = r.json()

        number_of_objects = initial_result['resultater'][0]['statistikk']['antallFunnet']
    else:
        number_of_objects = count

    objects = {}
    for i in range(0, int(math.ceil(float(number_of_objects)/OBJECTS_PER_FETCH))):
        offset = OBJECTS_PER_FETCH * i
        print offset, "/", number_of_objects

        pagination = query['objektTyper'][0]
        pagination['start'] = offset + 1  # NVDB is one indexed
        pagination['antall'] = min(OBJECTS_PER_FETCH, number_of_objects-offset)

        r = requests.get("https://www.vegvesen.no/nvdb/api/sok?kriterie="+json.dumps(query))

        result = r.json()

        for veg_objekt in result['resultater'][0]['vegObjekter']:
            object_id = veg_objekt['objektId']
            objects[object_id] = veg_objekt

    return objects

for object_type_id in [FARTSGRENSE, VEGBREDDE, VEGREFERANSE]:
    objects = get_road_objects(object_type_id)
    with open('nvdb-data/'+str(object_type_id)+'.json', 'w') as outfile:
        json.dump(objects, outfile)
