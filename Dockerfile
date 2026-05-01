# Use OpenJDK base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY target/InsecureBankApp-1.0.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]