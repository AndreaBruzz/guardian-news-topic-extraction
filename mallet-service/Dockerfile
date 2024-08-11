# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml pom.xml
COPY src/ src/
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17
COPY --from=build /app/target/mallet-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
