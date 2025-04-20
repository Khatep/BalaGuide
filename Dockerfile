# Stage 1 — Build with Maven using JDK 21
FROM eclipse-temurin:21 AS build

WORKDIR /app

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2 — Extract layers from the built .jar using JDK 21
FROM eclipse-temurin:21 AS builder

WORKDIR /application
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} service.jar

RUN java -Djarmode=layertools -jar service.jar extract

# Stage 3 — Runtime with JDK 21
FROM eclipse-temurin:21-jre

WORKDIR /application

COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
