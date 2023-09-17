# Use the official OpenJDK 17 base image from Docker Hub
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY jar/weather-service-api-0.1.jar /app/weather-service-api-0.1.jar

# Command to run your Java application
CMD ["java", "-jar", "weather-service-api-0.1.jar"]
