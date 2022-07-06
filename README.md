# Poseidon

Poseidon is a trades management application for investors.

Poseidon aims to generate more trades for institutional investors buying and selling fixed income securities.

## Prerequisites
- Java 1.8
- Maven 3.6.0
- MySQL 5.7.36
- Thymeleaf
- Bootstrap 4.0.0

## Installing
A step by step series of examples that tell you how to get an environment running:

1.Install Java:

[https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)

2.Install Maven:

[https://maven.apache.org/install.html](https://maven.apache.org/install.html)

3.Install MySql:

[https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/)

After downloading the mysql installer and installing it, you will be asked to configure the password for the default `root` account.

## Importing the project
Import the project in your favorite IDE.

## Settings
Go to `src/main/resources/config/`:
- open `application.properties`: set your OAuth clients id and secret.
- open `application-prod.properties`: edit all `spring.datasource...` properties with your database settings.

## Testing
Go to the root repository of the project:

Execute `mvn verify` to run unit and integration tests.

## Running App
Spring profile is set by default to `dev` profile (in-memory database).

Go to the `target` repository and execute:

- `java -jar poseidon-0.0.1-SNAPSHOT.jar`

To run the app in `prod` profile (MySQL database), execute:

- `java -Dspring.profiles.active=prod -jar poseidon-0.0.1-SNAPSHOT.jar`

Two users are already registered:

Administrator: username = `admin` and password = `admin`

User: username = `user` and password = `user`

Only administrators manage users: add, update, delete.

Users can only manage items: add, update delete.

In your browser, go to `http://localhost:8080/login`, use one of the credentials above and use the app!

## Documentation
Go to the root repository of the project:

Execute `mvn site` to generate the documentation and the reports.

In your browser, open `Poseidon/target/site/index.html`.
