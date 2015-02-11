import json
import math

import requests


TRONDHEIM_KOMMUNE = 1601
VEGREFERANSE = 532
FARTSGRENSE = 105


def get_all(object_type_id):
    query = {
        "lokasjon": {
            "kommune": [TRONDHEIM_KOMMUNE]
        },
        "objektTyper": [{
            "id": object_type_id,
            "antall": 0,
        }]
    }

    r = requests.get("https://www.vegvesen.no/nvdb/api/sok?kriterie="+json.dumps(query))

    initial_result = r.json()

    number_of_objects = initial_result['resultater'][0]['statistikk']['antallFunnet']
    OBJECTS_PER_FETCH = 1000

    query['objektTyper'][0]['antall'] = OBJECTS_PER_FETCH

    objects = []
    for i in range(0, int(math.ceil(float(number_of_objects)/OBJECTS_PER_FETCH))):
        offset = OBJECTS_PER_FETCH * i
        print offset, "/", number_of_objects, len(objects)

        query['objektTyper'][0]['start'] = offset + 1  # NVDB is one indexed

        r = requests.get("https://www.vegvesen.no/nvdb/api/sok?kriterie="+json.dumps(query))

        result = r.json()

        for veg_objekt in result['resultater'][0]['vegObjekter']:
            objects.append(veg_objekt)

    print json.dumps(objects[0], indent=2)
    print len(objects)


print get_all(FARTSGRENSE)
print get_all(VEGREFERANSE)
