# Java Spring Boot Demo

## Intro

In case you wanna specialize on backend development, Java is a natural choice. It is still widely used in companies.

There are many more programming language options for backend development. This video here nicely summarizes the Pro and Cons of using Java instead of other prominent web development languages like PHP, Python, C# or Node (even though from 2019, it is still very accurate): https://www.youtube.com/watch?v=mCOSgYilwNs.

However: Before getting into Java, you will have to say "YES" to the so called "Object Oriented Programming" (OOP). In case you have no idea what that is, getting right into the Spring framework is probably not a good first step. It makes sense to train it a bit in the language you are currently using (e.g. in JavaScript you can do OOP to), then trying out your first Java program (without a framework) using some classes calling each other and get a little bit used to Java terminology. Only afterwards it makes sense to then dive into the Spring experience.

For Backend API Development the Framework "Spring" is by far the most commonly used. But Spring is a super complex multi purpose framework. 
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
  - You could register a free postgres database on Heroku: https://devcenter.heroku.com/articles/heroku-postgresql

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

The Environment variables are the one inside the ${...} blocks (you can compare it to process.env.YOUR_ENV_VAR in Node)

This way the app will be easily deployable to production, grabbing all its config like database from the Server Environment / Config vars.

But in order to setup your environment for LOCAL DEVELOPMENT, you have two choices:
- set the same environment variables on your system (not recommended - it would get shared with other apps)
- set the local environment by a separate properties file that will overwrite (!) the settings from application.properties. That local environment file must get added to .gitignore to keep it out of Git

So we will choose the second option.
 
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


## Running the app

If you have configured your VScode with the Spring Boot Extension, you can easily navigate to the main entry file /src/main/java/com/example/springbootdemo/DemoApplication.java and open it.

A little "Play" icon should appear at the top right of your file. 

Click that and check if the app starts up!

Alternatively you can also launch the app from the terminal:

`./mvnw spring-boot:run`

Under the hood now the build tool "Maven" will launch, install and bind all necessary dependencies and then starts your app. Hopefully successfully :)


## Deployment

You can deploy a Spring boot app for free e.g. on Heroku:
https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku

In order to deploy this app, you do not need to run the spring-boot CLI, because you already have the app created. So you can skip that part

Also please setup the Postgres Addon in your created Heroku App like shown in the Heroku Devcenter article above!
This way you get a Postgres Database out of the box

In case you already have your own SQL database:
Please provide the Environment / Config Vars in your Heroku Dashboard of your App => Tab Settings:

```
DATABASE_URL=yourDbUrl
DATABASE_USERNAME=yourDbUser
DATABASE_PASSWORD=yourDbPw
```

Heroku will make these variables available and Spring Boot will loadthose into you application.properties file on startup. 

And that is it. 

Usually you do not need to configure anything extra on Heroku.
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
