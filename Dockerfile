FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/esportiva-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "app.jar"]