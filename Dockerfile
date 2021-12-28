FROM openjdk:11

EXPOSE 8080
COPY . .
WORKDIR /app

ENTRYPOINT ["java", "-Djasypt.encryptor.password=zccucsc",  "-jar", "app-artifact.jar"]