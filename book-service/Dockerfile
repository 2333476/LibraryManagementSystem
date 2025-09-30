# Use the official OpenJDK 21 base image from Docker Hub
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from your local machine into the container
COPY build/libs/book-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080
#8083

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
