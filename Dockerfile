FROM maven:3-jdk-8
ARG SW_VERSION=2.7.3
ARG JAR_FILE=./target/LayeredArchitectureDemo-${SW_VERSION}.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","/app.jar"]