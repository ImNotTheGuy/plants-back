# Build
FROM maven:3.8.8-eclipse-temurin-21-alpine as build

WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn package

FROM openjdk:21-alpine

ARG FRONTEND_URL
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--websocket.frontend.url=", "$FRONTEND_URL"]