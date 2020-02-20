FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/har.task.com-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} test_task.jar

ENTRYPOINT ["java", "-jar", "/test_task.jar"]