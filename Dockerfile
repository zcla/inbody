FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/inbody-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
