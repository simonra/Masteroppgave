Uthenting av all veidata i trondheim:

`$ curl -H "Accept: application/json" https://www.vegvesen.no/nvdb/api/vegnett/16/01`

Her står 16 for Sør-Trønderlag og 01 for Trondheim.

# Dataformat

Interessant data ligger i `'features'` i objektet man får fra NVDB. Dette er en liste med veilenker.

Veilenkene har følgende form

```json
{
  "geometry": {
    "coordinates": [[270548.300048828, 7041797.90002441], ..],
    ...
  },
  "properties": {
    "lokalId": 41306
    ...
  }
}
```

`lenke['properties']['lokalId']` tilsvarer veglenkeid i NVDB, og kan hentes ut ved følgende url:

`https://www.vegvesen.no/nvdb/api/vegreferanse/lenke?id=41306&posisjon=0.0` (Der posisjon 0.0 vil si start av veilenke og 1.0 er slutt.)

Ut fra url'en over kan man finne vegobjektid, som man kan bruke til å få mer utfyllende data om veilenken.

Koordinatene i `'geometry'` er på formen UTM33, men kan enkelt konverteres til latlang.
