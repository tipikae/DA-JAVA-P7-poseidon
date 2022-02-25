FROM openjdk:8-jdk-alpine
COPY target/poseidon*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
EXPOSE 8080