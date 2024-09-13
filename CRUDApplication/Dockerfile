FROM openjdk:22
VOLUME /tmp
EXPOSE 8080
WORKDIR /app
COPY target/CRUDApplication-0.0.1-SNAPSHOT.jar /app/CRUDApplication-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/CRUDApplication-0.0.1-SNAPSHOT.jar"]
