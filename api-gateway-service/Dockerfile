# Use the official OpenJDK 21 base image from Docker Hub
FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/api-gateway-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]