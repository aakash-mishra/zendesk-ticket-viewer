#Zendesk Ticket Viewer

* **Installation Guide**  
*Note*: This is a Java based application that uses Spring Boot Framework and relies on Gradle for build and dependency management. Install [Java](https://www.oracle.com/java/technologies/downloads/) and [Gradle](https://gradle.org/install/) before moving on to next steps.
    1. Encrypt api token and username
        * This project uses Jasypt, a handy library to encrypt secret credentials such as passwords, api tokens, etc at compile time and decrypts them at runtime as required. Read more about jasypt [here](http://www.jasypt.org/)
        * I have used Basic Authentication with API Tokens to authenticate API requests in this project. Both username and API token is encrypted and stored in the config file. Username takes the following format: `{registered email id}/token`. For example, abc@example.com/token. The password is simply the API token that you can generate from Zendesk Admin Center. To encrypt your API credentials, head over to [this](https://www.devglan.com/online-tools/jasypt-online-encryption-decryption) link and fill in the required info. Keep the secret key you used for encryption secure - we will need to pass that key while starting the application.
    2. Add the encrypted keys to config
        * We now need to use the encrypted keys and add it to our configuration. In the application.properties file of this project (src/main/resource/application.properties), you will see two properties `api.owner.username` and `api.token` followed by the ENC(...) values. Replace your encrypted keys corresponding to both the properties.  
    
* **Usage Guide**
    1. To run the CLI application, first build the gradle project. You can read more about gradle builds [here](https://spring.io/guides/gs/gradle/). A convenient way to build projects is using gradle wrapper (gradlew). From within the project directory, open up a terminal and issue the command `./gradlew build`. This will create necessary artefacts of the project and place it in `build\libs` directory. To keep things simple, I have not versioned the builds for now and all exported jars will be called `zendesk-ticket-viewer-0.0.1-SNAPSHOT.jar`. You can customize this in `build.gradle`.
    2. Once the project is built, you can run the application using `java -jar` command: `java -Djasypt.encryptor.password={your_secret_key} -jar build/libs/zendesk-ticket-viewer-0.0.1-SNAPSHOT.jar`  
    The `-Djasypt.encryptor.password` is a runtime argument that we are providing for jasypt to decrypt encrypted properties at runtime. Replace `{your_secret_key}` with your own secret key that you used while encrypting credentials in step 1 of the Installation Guide
    3. The project is now ready to use from the command line.
    
        

