# Stage 1 — Build with Maven
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2 — Extract layers from the built .jar
FROM openjdk:17-slim as builder

WORKDIR /application
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} service.jar

RUN java -Djarmode=layertools -jar service.jar extract

# Stage 3 — Minimal runtime image
FROM openjdk:17-slim

WORKDIR /application

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
# EXPOSE 8081
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
