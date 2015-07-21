@echo off
set DCS_URL=http://localhost:8080/dcs/rest 

curl -s %DCS_URL% -# ^
     -F "dcs.c2stream=@../shared/data-mining.xml" ^
     -o clusters-from-local-file-multipart.xml

echo Results saved to clusters-from-local-file-multipart.xml

curl -s %DCS_URL% -# ^
     --data-urlencode "dcs.c2stream@../shared/data-mining.xml" ^
     -o clusters-from-local-file-formencoded.xml

echo Results saved to clusters-from-local-file-formencoded.xml
