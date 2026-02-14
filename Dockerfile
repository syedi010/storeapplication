FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/storeapplication-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java","-jar","app.jar"]