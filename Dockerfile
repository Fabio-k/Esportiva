FROM amazoncorretto:23-alpine3.21

WORKDIR /app

COPY target/esportiva-0.0.1-SNAPSHOT.jar /app/app.jar

CMD [ "java", "-jar", "/app/app.jar"]