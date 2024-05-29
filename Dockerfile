# Build
FROM maven:3.8.8-eclipse-temurin-21-alpine as build

WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn package

FROM openjdk:21-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ARG FRONTEND_URL
RUN echo "$FRONTEND_URL"

CMD ["java", "-jar", "app.jar", "--websocket.frontend.url=", "$FRONTEND_URL"]