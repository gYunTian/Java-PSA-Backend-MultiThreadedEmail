# G1-T9 Server Application


## Deployment Instructions

### Prerequisites 
1. Ensure that your local computer has the `JAVA_HOME` environment variable set to the sdk location.
2. Turn on your WAMP server.
3. If not done so, import the `deploy.sql` script which is located in the `sql` folder.
4. Run `run.bat`. 

### Post-Deployment
- The app will now be accessible on `localhost:8080`.
- To terminate the connection, simply close the command prompt window opened.


## Database Design

### Non-voyage storage tables

configuration (time_interval, api_key)

user (id, name, email, password) 

domain (name)

vessel (name)

### Voyage tables

voyage_id (id, vessel_name, voyage_num)

voyage (voyage_id, berth_num, status, change_count)

voyage_in (id, berth_dt, first_berth_dt)

voyage_out (id, depart_dt)

favourites (user_id, voyage_id)