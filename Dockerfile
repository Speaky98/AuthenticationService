FROM openjdk:8
EXPOSE 8084
ADD /target/authenticationservice.jar authenticationservice.jar
ENTRYPOINT ["java", "-jar", "/authenticationservice.jar"]