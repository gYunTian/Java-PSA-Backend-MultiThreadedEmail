# G1-T9 Server Application


## Deployment Instructions

### Prerequisites 
1. Ensure that your local computer has the `JAVA_HOME` environment variable set to the sdk location.
2. Turn on your WAMP server.
3. If not done so, import the `deploy.sql` script which is located in the `sql` folder. Alternatively, let Spring Boot auto-create the tables (lose precision).
4. Run `run.bat`. 

### Post-Deployment
- The app will now be accessible on `localhost:8080`.
- To terminate the connection, simply close the command prompt window opened.


## Database Design

### Non-voyage storage tables

user (id, name, email, password) 

vessel ()

### Voyage tables

voyage_fav (user_id, voyage_id)

voyage_sub (user_id, voyage_id)


voyage (voyage_id, berth_num, status, change_count)

voyage_in (id, berth_dt, first_berth_dt)

voyage_out (id, depart_dt)
