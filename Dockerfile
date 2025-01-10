FROM openjdk:17-jdk-slim

WORKDIR /app

COPY app.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]