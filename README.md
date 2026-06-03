### Hotel Application

- Application  
L'application doit se lancer en plus du conteneur Docker.

- Docker  
`docker compose up -d`

- Ports Docker  

SQL : 3406 (3306 en interne)  
SMTP : 1025  
Menu web des mails : 8025  
Grafana : 3000  
OLTP (télémétrie) : 4317  
Point HTML OLTP (télémétrie) : 4318  

- API  

API : 8060  
/dashboard  
/room-types  
/bookings  
/availability  

- Dashboard Grafana application  

Le dashboard rapportant les télémétriques peut être consulté de cette manière :  
- Une fois l'application et le conteneur Docker lancés, aller sur `localhost:3000` 
- Importer le json se trouvant dans `docker/grafana/dashboards_json`