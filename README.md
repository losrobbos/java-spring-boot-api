# Java Spring Boot Demo

## Prerequisites

- Java SDK or OpenJDK 17
- Spring Boot with Maven
  - Checkout the recommended Vscode plugins for easy setup and startup of a Spring Boot App: https://code.visualstudio.com/docs/java/java-spring-boot
- An existing Postgres or MySQL database to connect to
  - You could install either Postgres or MySQL locally on your system
  - You could register a free postgres database on Heroku: https://devcenter.heroku.com/articles/heroku-postgresql
  - You could register a free MySQL database on: https://www.freesqldatabase.com/

## Setup

### Create a local configuration / environment

Create a file "application-local.properties" inside Folder src/main/resources

Add the following environment variables and point them to your database.
Replace the values with the details from your database

```
server.port=5000

spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=postgres
spring.datasource.password=yourDbPw

jwt.secret=myLocalSecretio

```

In order that Spring Boot loads the given file on startup, you 
have to run the app in development with a profile, e.g. "local".

Profiles are used to create different ENVIRONMENTS with different configurations, e.g. a different Database connection. 

You can read more on profiles here: https://www.baeldung.com/spring-profiles 

Depending on the profile that you run your app with, a correspondig properties file (if it exists) with the config values for that environment will get loaded automatically. 

The configuration file must contain the profile name.

Examples:
Profile "local" => Configuration filename: application-local.properties
Profile "development" => Configuration filename: application-development.properties

So the profile name determines the file name that Spring will look for and will then autodetect and load the file.

There are several ways how you can define your profile for local development.

One is to export an environment variable "spring_profiles_active" which will be checked by Spring Boot on startup:

`export spring_profiles_active=local`

Another is to start the app from the command line specifying the profile:

`./mvnw -Dspring.profiles.active=local spring-boot:run `

`./mvnw spring-boot:run -Drun.jvmArguments="spring.profiles.active=local"`


That's it.

