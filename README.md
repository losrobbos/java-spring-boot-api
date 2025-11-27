# Java Spring Boot Demo

## Intro

For Java Backend API Development the Framework "Spring" is by far the most commonly used. But Spring is a super complex multi purpose framework. 
So a pre-configured version of Spring exists, called "Spring Boot" which gives you a lot of basic libraries and configurations out of the box, to e.g. start quickly with creating a RESTful API with operations against a database of your choice.

In order to checkout the fundamentals of the Spring Framework you might checkout this helpful high level videos first:
- Spring Concepts (High Level): https://www.youtube.com/watch?v=gq4S-ovWVlM
- Spring Flow with Container & Beans: https://www.youtube.com/watch?v=aS9SQITRocc


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
  - You could register a free postgres database on Render: https://render.com/docs/postgresql-creating-connecting

## Setup

### Create configuration / environment

In Spring Boot environment variable configuration is not done with .env files like e.g. in the Node or PHP world.

Instead so called "properties" files are used.

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


jwt.secret=${JWT_SECRET:"myHolySecret"}
```

In order to setup your environment for LOCAL DEVELOPMENT:
Set the local environment by a separate properties file that will override (!) the settings from application.properties. That local environment file must get added to .gitignore to keep it out of Git

#### Create local properties / environment file

Create a file "application-local.properties" inside Folder src/main/resources.

This file pretty much has the same purpose as your .env file from other languages. Provide an Environment Config for local development.

Add the following config variables and replace the values with the details from your database.

```
server.port=5000

spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=postgres
spring.datasource.password=yourDbPw

jwt.secret=myLocalSecretio

```

#### Loading the local environment file

Now how to load that file instead of application.properties?

In order that Spring Boot loads the given file on startup, you need to launch the app with a so called "profile".

"Profile" is the term Spring Boot uses for "Environment". So don't get confused here ;)

Profiles are used to serve different ENVIRONMENTS with different configurations, e.g. a different Database connection.

You can read more on profiles here: https://www.baeldung.com/spring-profiles 

Depending on the profile that you run your app with, a matching properties file (if it exists) with the config values for that environment will get loaded automatically (and will overwrite the settings in application.properties!)

The configuration file just must contain the profile name.

Examples:
- Profile "local" => Configuration filename: application-local.properties
- Profile "development" => Configuration filename: application-development.properties

So the profile name determines the file name that Spring will look for and will then autodetect and load the file.

In your application.properties the "local" profile is already pre-configured to be picked on startup:

`spring.profiles.active=local`

So you just need to provide the file "application-local.properties" in src/main/resources and your env for local development should get loaded.

Spring will automatically OVERWRITE all settings in application.properties with the ones in your local properties file!


#### Production environment

For production you could create another profile, e.g. "prod" or "production".

But actually you do not need that, because on production your "application.properties" file will kick in and load the configuration from the environment. So you just need to setup all environment variables on your server and it should work out of the box.

In production, e.g. when you deploy to Heroku, Spring Boot will try to lookup an application-local.properties first.

But if it does not find it, it will default to loading the values from application.properties. Which loads the configuration from the environment / config vars you set on the server.

So please: Keep the application-local.properties out of git to prevent accidental deployment!
(it is already added to .gitignore file in this project, so usually nothing to do here)


## Authentication

The App utilizes JWT (JSON Web Token) for protecting routes.

In order to receive a token, call the /auth/login route and provide 
the fields "email" and "password" in the request JSON body.

### Configuring protected routes

The configuration of which routes are public and which private (=protected by token) is configured in the File [SecurityConfig.java](src/main/java/com/example/springbootdemo/config/SecurityConfig.java)


## Running the app with Maven (Java / JRE required on your PC)

If you have configured your VScode with the Spring Boot Extension, you can easily navigate to the main entry file [/src/main/java/com/example/springbootdemo/DemoApplication.java](/src/main/java/com/example/springbootdemo/DemoApplication.java) and open it.

A little "Play" icon should appear at the top right of your file. 

Click that and check if the app starts up!

Alternatively you can also launch the app from the terminal:

`./mvnw spring-boot:run`

Under the hood now the build tool "Maven" will launch, install and bind all necessary dependencies and then starts your app. Hopefully successfully :)

## Running the app with docker (no Java required)

You can use the attached docker compose file to build the app image & run a container in one go.

### Prepare .env

Create a .env file. See the .env.sample file for the needed env variables.
Docker compose will automatically inject the contents of this .env file into your container when starting it. So the .env vars will be available to your app at runtime.

### Build image & start the container

`docker compuse up -d`

It will create a container based on the image in the given "Dockerfile".
Here a java environment is setup.


## Building a jar for deployment

You can use this command to create a Jar File into the folder "target" 
(comparable to the classical "build" output folder in the JS World)

`./mvnw clean package -DskipTests`

Running the build with JRE:

`java target/<nameOfYourGeneratedJar>.jar`


## Deployment

You can deploy a Spring boot app for free e.g. Render.com.

A short & concise tutorial:
https://medium.com/@pmanaktala/deploying-a-spring-boot-application-on-render-4e757dfe92ed

Please ignore the Dockerfile in the article, because you already have one.
Instead jump right away to the section "Setting up on Render"

In the Environment section you need to define the same Variables that are defined in the .env / .env.sample file. 

```
DATABASE_URL=yourDbUrl
DATABASE_USERNAME=yourDbUser
DATABASE_PASSWORD=yourDbPw
JWT_SECRET=verySecretio
```

Render will inject those ENV vars into your container when starting it,
so they should be available to your app.

And that is it. 

Usually you do not need to configure anything extra.
It should run out of the box.

Enjoy!

## Resources / Learning

In order to understand the big picture of Spring, this video series - even though a bit dated - is still excellent for all general concepts: 
- https://www.youtube.com/watch?v=msXL2oDexqw&list=PLqq-6Pq4lTTbx8p2oCgcAQGQyqN8XeA1x (most of the info is still relevant)

In order to understand how you work with databases with Spring Boot there is a lot of jargon out there (JDBC, JPA, Hibernate, Spring Boot Data JPA). This video here explains how it all works together nicely:
- https://www.youtube.com/watch?v=GX3D0OIFOhE

And here a great article that shows how to develop a Spring Boot REST API with Database CRUD (Create / Read / Update / Delete):
- https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/ (it is very compact and dense, so this is a quick way to a first running API)

Authentication using JWT:
- https://www.youtube.com/watch?v=mn5UZYtPLjg (german - sehr gute, einfache Einführung in JWT mit Code)
- https://www.youtube.com/watch?v=X80nJ5T7YpE (bit dated, but JWT has not changed in a while too :))
- https://www.youtube.com/watch?v=VVn9OG9nfH0 (more recent and complete walkthrough. pretty long, but worth it)
- https://www.bezkoder.com/spring-boot-jwt-authentication (in depth article)
