# Utilizza l'immagine base di OpenJDK 17
FROM openjdk:17-jdk-slim

# Imposta il volume temporaneo
VOLUME /tmp

# Copia il jar costruito nel container
COPY target/elasticsearch-service-0.0.1-SNAPSHOT.jar elasticsearch-service.jar

# Esponi la porta 8081
EXPOSE 8081

# Definisci il comando di avvio
ENTRYPOINT ["java", "-jar", "/elasticsearch-service.jar"]
