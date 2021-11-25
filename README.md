# Zendesk Ticket Viewer

* **Installation Guide**  
*Note*: This is a Java(11) based application that uses Spring Boot Framework and relies on Gradle for build and dependency management. Install [Java](https://www.oracle.com/java/technologies/downloads/) as a prerequisite for next steps.
 
    1. Encrypt API token and username
        - This project uses Jasypt, a handy Java library to encrypt secret credentials at compile time and decrypt them at runtime. Read more about Jasypt [here](http://www.jasypt.org/)
        - I have used Basic Authentication with API Tokens to authenticate Zendesk API requests. Both username and API token are encrypted and stored in the application.properties file (see ii below). Username takes the following format: `{registered email id}/token`. For example, abc@example.com/token. The password is simply the API token that you can generate from Zendesk Admin Center. 
        - To encrypt your API credentials, head over to [this](https://www.devglan.com/online-tools/jasypt-online-encryption-decryption) link. First, encrypt the username by following the pattern mentioned above. Then, encrypt the API token. Keep the secret key you used for encryption secure - we will need to pass that key while launching the application.
    2. Add the encrypted keys to config
        * We now need to add the encrypted keys from step (i) in our configuration. In the application.properties file of this project (`src/main/resource/application.properties`), you will see two properties `api.owner.username` and `api.token` followed by ENC(...) values. Replace your encrypted keys corresponding to both the properties.
    3. Build the application
        * To build the app, open up a terminal and cd into the project directory. Then, type `./gradlew build`. This will create the necessary jar artifact for the application and place it in the `build\libs` directory. To keep things simple, I have not implemented auto-versioning of builds and all exported jars will be called `zendesk-ticket-viewer-0.0.1-SNAPSHOT.jar`. You can customize this in `build.gradle`.      
      
* **Usage Guide**
    1. Once the project is built, you can run it using `java -jar` command along with a VM argument that is the secret key you used to encrypt credentials in step (i) above: `java -Djasypt.encryptor.password={your_secret_key} -jar build/libs/zendesk-ticket-viewer-0.0.1-SNAPSHOT.jar`  
     Replace `{your_secret_key}` with your own secret key
    2. The app is now ready to use from the terminal   
    ![Zendesk Ticket Viewer](static/zendesk-ticket-viewer-cli.jpg)