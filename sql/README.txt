Before running our server application, ensure the following prerequisites are fulfilled:
- Ensure your WAMP/MAMP server is running to allow access to your local database. 
- If not done so, import the deploy.sql script. This can be done in phpmyadmin, MySQL Workbench or otherwise. 
- If not done so, ensure your local computer has the JAVA_HOME environment variable set to your Java SDK location.

To run the server application, open server-start.bat in the root directory, or do a `./mvnw spring-boot:run`. 

The server will be accessible on localhost:8080 by default. To terminate the connection, simply close the terminal.