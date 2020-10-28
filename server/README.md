# G1-T9 Server Application

## Deployment Instructions

### Prerequisites 
1. Ensure that your local computer has the `JAVA_HOME` environment variable set to the sdk location.
2. If not done so, import the `deploy.sql` script which is located in the `sql` folder.
3. Run `run.bat`. 

### Post-Deployment
- The app will now be accessible on `localhost:8080`.
- To terminate the connection, simply close the command prompt window opened.


## Database Design
user (id, name, email, password, token) 
voyage_fav (user_id, voyage_id)
voyage_sub (user_id, voyage_id)
