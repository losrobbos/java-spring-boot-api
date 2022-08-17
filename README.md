# Java Spring Boot Demo

## Prerequisites

- Java SDK or OpenJDK 17
  - Install e.g. using apt on Ubuntu: `sudo apt install openjdk-17`
  - For other Operating systems - please checkout the official documentation: https://docs.oracle.com/en/java/javase/18/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A
- Spring Boot with Maven
  - Checkout the recommended Vscode plugins for easy setup and startup of a Spring Boot App: https://code.visualstudio.com/docs/java/java-spring-boot
  - Or if you prefer a short video with all the steps: https://www.youtube.com/watch?v=dq1z9t03mXI
- An existing Postgres or MySQL database to connect to
  - You could install either Postgres or MySQL locally on your system
  - You could register a free MySQL database on: https://www.freesqldatabase.com/
  - You could register a free postgres database on Heroku: https://devcenter.heroku.com/articles/heroku-postgresql

## Setup

### Create configuration / environment

In Spring Boot environment variable configuration is not done with .env files like e.g. in the Node or PHP world.

Instead so calles "properties" Files are used.

You have a default "application.properties" file, with relevant config settings. It is located in the folder src/main/resources of a Spring Boot App.

Ideally this file loads its values from the ENVIRONMENT.

As an Example - the application.properties from this app:
```
spring.profiles.active=local

# in case no port is set in environment => use default 5000
server.port=${PORT:5000}

spring.datasource.url=${DATABASE_URL:""}
spring.datasource.username=${DATABASE_USERNAME:""}
spring.datasource.password=${DATABASE_PASSWORD:""}

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update


jwt.secret=${JWT_SECRET:""}
```

This way the app will be easily deployable to production, grabbing all its config like database from the Server Environment / Config vars.

But in order to setup your environment for LOCAL DEVELOPMENT, you have two choices:
- set the same environment variables on your system (not recommended - it would get shared with other apps)
- set the local environment by a separate properties file!

#### Create local properties / environment file

Create a file "application-local.properties" inside Folder src/main/resources:

Add the following environment variables and replace the values with the details from your database.

```
server.port=5000

spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=postgres
spring.datasource.password=yourDbPw

jwt.secret=myLocalSecretio

```

Now how to load that file instead of application.properties?

In order that Spring Boot loads the given file on startup, you have to launch the app launch with a profile, e.g. "local". 

"Profile" is the term Spring Boot uses for "Environment". So don't get confused here ;)

Profiles are used to serve different ENVIRONMENTS with different configurations, e.g. a different Database connection.

You can read more on profiles here: https://www.baeldung.com/spring-profiles 

Depending on the profile that you run your app with, a correspondig properties file (if it exists) with the config values for that environment will get loaded automatically. 

The configuration file just must contain the profile name.

Examples:
Profile "local" => Configuration filename: application-local.properties
Profile "development" => Configuration filename: application-development.properties

So the profile name determines the file name that Spring will look for and will then autodetect and load the file.

In your application.properties the "local" profile is already pre-configured to be picked on startup:

`spring.profiles.active=local`

So when just need to provide the file "application-local.properties" and your env for local development should get loaded.

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

## Resources / Learning

In order to understand the big picture of Spring, this video series - even though a bit dated, is still excellent: 

https://www.youtube.com/watch?v=msXL2oDexqw&list=PLqq-6Pq4lTTbx8p2oCgcAQGQyqN8XeA1x
(most of the info is still relevant)

In order to understand how you work with databases with Spring Boot there is a lot of jargon out there (JDBC, JPA, Hibernate, Spring Boot Data JPA). This video here explains how it all works together nicely:

https://www.youtube.com/watch?v=GX3D0OIFOhE

And here a great article that shows how to develop a Spring Boot REST API with Database CRUD (Create / Read / Update / Delete):

https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/
