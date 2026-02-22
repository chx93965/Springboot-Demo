#FROM maven:3-jdk-8
#ARG SW_VERSION=2.7.3
#ARG JAR_FILE=./target/LayeredArchitectureDemo-${SW_VERSION}.jar
#COPY ${JAR_FILE} /app.jar
#EXPOSE 8085
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:8-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]