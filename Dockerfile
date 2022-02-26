FROM maven:3.6.0-jdk-8-alpine as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn package
COPY target/poseidon*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
EXPOSE 8080