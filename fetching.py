import json
import math

import requests


TRONDHEIM_KOMMUNE = 1601
VEGREFERANSE = 532
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

    objects = []
    for i in range(0, int(math.ceil(float(number_of_objects)/OBJECTS_PER_FETCH))):
        offset = OBJECTS_PER_FETCH * i
        print offset, "/", number_of_objects

        pagination = query['objektTyper'][0]
        pagination['start'] = offset + 1  # NVDB is one indexed
        pagination['antall'] = min(OBJECTS_PER_FETCH, number_of_objects-offset)

        r = requests.get("https://www.vegvesen.no/nvdb/api/sok?kriterie="+json.dumps(query))

        result = r.json()

        for veg_objekt in result['resultater'][0]['vegObjekter']:
            objects.append(veg_objekt)

    print json.dumps(objects[0], indent=2)
    print len(objects)


print get_road_objects(FARTSGRENSE, count=10)
print get_road_objects(VEGREFERANSE, count=10)
