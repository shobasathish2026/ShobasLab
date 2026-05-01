# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY target/InsecureBankApp-1.0.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]