FROM openjdk:17-alpine
COPY "target/kusta-0.0.1-SNAPSHOT.jar" "kusta.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","kusta.jar"]