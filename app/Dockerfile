# Builder stage (остался без изменений, так как уже с JDK)
FROM gradle:8.10-jdk21 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew && ./gradlew clean generateJte shadowJar -x test --no-daemon

# Runtime stage: исправлено на JDK для поддержки JTE on-demand
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
# порт динамический из ENV
ENTRYPOINT ["java", "-jar", "app.jar"]