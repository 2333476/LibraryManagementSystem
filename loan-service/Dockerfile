# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR
COPY build/libs/loan-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080
#8085

# Ensure the correct Spring profile is active
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
