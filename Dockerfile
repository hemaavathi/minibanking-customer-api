FROM openjdk:8-jdk-alpine
COPY target/*.jar customer-api.jar
CMD ["java", "-jar", "customer-api.jar"]