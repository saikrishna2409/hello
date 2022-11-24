FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY target/TweetApp-API-0.0.1-SNAPSHOT.jar TweetApp-API-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/TweetApp-API-0.0.1-SNAPSHOT.jar"]