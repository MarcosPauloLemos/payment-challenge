FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target target
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/app.jar"]