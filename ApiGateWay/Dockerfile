FROM openjdk:19-alpine
COPY /build/libs/*.jar app.jar
COPY /src/main/resources/application-docker.properties application-docker.properties
COPY /src/main/resources/application.properties application.properties
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.name=application,application-docker"]
