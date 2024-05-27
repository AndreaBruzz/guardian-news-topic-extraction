--- PER FARE GIRARE IL SERVIZIO ---
(non so se si possa fare meglio)
docker compose up

mvn spring-boot:run

--- PER PROVARLO ---
# Test di successo
curl -X POST http://localhost:8080/upload \
     -H "Content-Type: application/json" \
     -d '{
           "label": "AI",
           "issue": "artificial intelligence",
           "startDate": "1984-01-01",
           "endDate": "2024-03-31"
         }'

# Test di errore (File non trovato)
curl -X POST http://localhost:8080/upload \
     -H "Content-Type: application/json" \
     -d '{
           "label": "failure",
           "issue": "invalid query",
           "startDate": "1984-01-01",
           "endDate": "2024-03-31"
         }'

--- PER VEDERE ARTICOLI A DB ---
mongosh
use articles
db.articles.find().pretty()

