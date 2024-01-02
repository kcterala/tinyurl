# Stage 1: Build Stage
FROM bellsoft/liberica-openjdk-alpine:17.0.7-7 as builder
WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew --no-daemon clean build -x test

FROM bellsoft/liberica-runtime-container:jre-17-slim-musl
WORKDIR /app
COPY --from=builder /app/build/libs/application.jar /app/build/libs/application.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/build/libs/application.jar"]
