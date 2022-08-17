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

In your application.properties the "local" profile is already pre-configured to be picked on startup:
spring.profiles.active=local

So you just need to provide the file "application-local.properties" and your env for local development should get loaded.

In production, so e.g. when you deploy to Heroku, it will try to lookup an application-local.properties too, but if it does not find it, it will load the defaults from application.properties -> which loads the configuration form the environment / config vars you set on the server.

So please: KEEP the application-local.properties out of git!
(it is already added to .gitignore file, so usually nothing to do here)

That's it!

## Running the app

If you have configured your VScode with the Spring Boot Extension, you can easily navigate to file /src/main/java/com/example/springbootdemo/DemoApplication.java and open it.

A little "Play" icon should appear at the top right of your file. Click and check if the app starts up!

Alternatively you can also launch the app from the terminal:

`mvnw spring-boot:run`


## Deployment

You can deploy a Spring boot app for free e.g. on Heroku:
https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku

In order to deploy this app, you do not need to run the spring-boot CLI, because you already have the app created. So you can skip that part

Also please setup the Postgres Addon like shown in the article!
This way you get a Postgres Database out of the box

In case you already have your own SQL database:
Please provide the Environment / Config Vars in your Heroku Dashboard of your App => Tab Settings:

```
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
```

Usually you do not need to configure anything extra on Heroku.
It should run out of the box.

Enjoy!