# Use OpenJDK 21 as base image
FROM openjdk:21-jdk

# Label for maintainers
LABEL maintainer="oligo"

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container (adjust the name if needed)
COPY build/libs/employee-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port used by this service
EXPOSE 8080
#8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
