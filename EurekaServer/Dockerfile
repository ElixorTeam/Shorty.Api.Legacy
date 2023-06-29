FROM openjdk:19-slim

VOLUME /tmp

COPY /build/libs/*.jar app.jar
COPY /src/main/resources/application-prod.properties application-prod.properties
COPY /src/main/resources/application.properties application.properties

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
