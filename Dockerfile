# 1. Build the Gradle project inside the container
FROM gradle:8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# 2. Run the application in a lighter image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]