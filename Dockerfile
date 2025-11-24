FROM eclipse-temurin:17 AS build
# FROM eclipse-temurin:17-jre-alpine AS build 

WORKDIR /app
COPY . .

# RUN echo ">>> DEBUG LISTING" && ls -la /app && echo ">>> END"
# RUN echo ">>> MVNW CONTENT" && head -n 5 mvnw || echo "mvnw not found" && echo ">>> END"

# RUN chmod +x mvnw
# RUN sed -n '1p' mvnw | od -c

# CMD ["ls", "-la", "/app"]

# create executable jar from source code
# make the Maven wrapper executable (if present) then run it; otherwise install maven and use it
# RUN if [ -f ./mvnw ]; then chmod +x ./mvnw && ./mvnw clean package -DskipTests; else apk add --no-cache maven && mvn clean package -DskipTests; fi
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# # copy the executable jar file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]