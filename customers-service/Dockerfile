# Étape 1 : builder
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .

# Fix ici 👇 (enlève "bash")
RUN chmod +x gradlew && ./gradlew clean build -x test

# Étape 2 : image finale
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
