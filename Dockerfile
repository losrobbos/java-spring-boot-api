FROM eclipse-temurin:17 AS build
# FROM eclipse-temurin:17-jre-alpine AS build 

WORKDIR /app
COPY . .

# create executable jar from source code
# make the Maven wrapper executable (if present) then run it; otherwise install maven and use it
RUN ./mvnw clean package -DskipTests

# the executable jar will be placed in the target/ directory
# so the absolute path will be /app/target/<jar-file>.jar

# Second stage: runtime image
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# # copy the executable jar file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 5000

CMD ["java", "-jar", "/app/app.jar"]