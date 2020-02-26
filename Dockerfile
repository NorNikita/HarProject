FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/hartask-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} test_task.jar

ENTRYPOINT ["java", "-jar", "/test_task.jar"]

HEALTHCHECK --interval=10s --timeout=3s \
 CMD curl -f -s http://localhost:8080/check 2>&1 | grep OK || exit 1