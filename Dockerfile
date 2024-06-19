# Stage 1: Build the application
FROM maven:3.8.5 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]
