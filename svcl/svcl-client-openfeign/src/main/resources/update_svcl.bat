curl -X POST %1/b1s/v1/Login --cookie "cookies.txt" --cookie-jar "cookies.txt"  -H "Content-Type: application/json" -d @login.json -k 
curl -X GET %1/b1s/v1/$metadata --cookie "cookies.txt" --cookie-jar "cookies.txt"  -H "B1S-WithMappingInfo: 1" -k  > metadata.xml
del cookie.txt
