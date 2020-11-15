# G1-T9 Vessel Schedule Tracking Application

VSTA is a web application for PSA forwarders to plan the schedule for truckers. 

The Java server application calls PORTNET's `retrieveByBerthingDate` web service Application Programming Interface (API) on a regular interval to obtain real-time information about vessel berthing a.k.a arrival timings. 
- By default, the web service is called at 00:00 hours daily to retrieve the vessels’ arrival timings for the next 7 days (excluding the current day). 
- The web service is also called hourly to retrieve the vessels’ arrival timing for the current day (00:00 - 23:59).

The data is then stored in a relational database to be displayed on the user interface, which is built using ReactJS.

## Configurable Settings
The following settings can be found in `server/src/main/resources`.

`application.properties`: Credentials for the **database & Spring Security**. 
> Spring Security is used for API authentication, and change in credentials also requires change in `client/src/js/views/base.js` for the authorization header to be passed in every request handler.

`quartz.properties`: **Cron job** settings.

`reload.properties`: Settings that are configurable without the need to recompile code. This includes the following: 
- Accepted **email domains** at user registration
- Data retrieval **API**
    - Interval to call the API
    - Web service access key
    - Web service URL
- **Support email** for subscription notifications & password reset functionalities
    - Username
    - Password
    - [Host](https://www.jhipster.tech/tips/011_tip_configuring_email_in_jhipster.html) **Note: If gmail account, enable this so emails may be sent smoothly:*
> Manage your Google Account > Security > Less secure app access


## Development

Before running our server application, ensure the following prerequisites are fulfilled:
- Ensure your WAMP/MAMP server is running to allow access to the database. 
- If not done so, import the `deploy.sql` script which is located in the `server/sql` folder. This can be done in phpmyadmin, MySQL Workbench or otherwise. 
- If not done so, ensure your local computer has the `JAVA_HOME` environment variable set to your Java SDK location.

Before running our client application, ensure that the dependencies are installed with `npm run install` if `node_modules` not already present in the `client` folder.

To run both server & client applications, open `start.bat`.
- The server will be accessible on `localhost:8080`.
- The UI will be accessible on `localhost:9001`.

Alternatively, to run each individual application, use the following in the in terminal at the respective folders:
- Server: `./mvnw spring-boot:run` or open `server.bat`
- Client: `npm run start` or open `client.bat`

To terminate the connections, simply close the terminals.


## Production
The client application is currently available on https://g1t9-vsta.netlify.app/.

To use the functionalities, the server has to be started as with the procedure above.


## Database Design

Below are the information on the tables for `deploy.sql`:

| Table Name     | Columns Name                                        | Primary Key | Foreign Key              |
|----------------|-----------------------------------------------------|-------------|--------------------------|
| user           | id: int(11) AUTO_INCREMENT,                         | id          | -                        |
|                | name: varchar(32) NOT NULL,                         |             |                          |
|                | email: varchar(32) NOT NULL,                        |             |                          |
|                | password: varchar(60) NOT NULL,                     |             |                          |
|                | token: varchar(60)                                  |             |                          |
|                |                                                     |             |                          |
| vessel         | uniqueId: varchar(60) NOT NULL,                     | uniqueId    | vessel_history: uniqueId |
|                | imoN: varchar(12) DEFAULT NULL,                     |             |                          |
|                | fullVslM: varchar(48) NOT NULL,                     |             |                          |
|                | abbrVslM: varchar(32) NOT NULL                      |             |                          |
|                | fullInVoyN: varchar(500) DEFAULT NULL,              |             |                          |
|                | inVoyN: varchar(8) NOT NULL,                        |             |                          |
|                | fullOutVoyN: varchar(500) DEFAULT NULL              |             |                          |
|                | outVoyN: varchar(8) NOT NULL,                       |             |                          |
|                | shiftSeqN: varchar(5) NOT NULL,                     |             |                          |
|                | bthgDt: varchar(21) NOT NULL,                       |             |                          |
|                | unbthgDt: varchar(21) NOT NULL,                     |             |                          |
|                | berthN: varchar(10) DEFAULT NULL,                   |             |                          |
|                | status: varchar(12) NOT NULL,                       |             |                          |
|                | abbrTerminalM: varchar(8) DEFAULT NULL              |             |                          |
|                |                                                     |             |                          |
| vessel_history | uniqueId: varchar(60) NOT NULL,                     | uniqueId    | -                        |
|                | last_bthgDt: varchar(21) NOT NULL,                  |             |                          |
|                | last_unbthgDt: varchar(21) NOT NULL,                |             |                          |
|                | bthgDt_change_count: int(2) NOT NULL DEFAULT '0',   |             |                          |
|                | unbthgDt_change_count: int(2) NOT NULL DEFAULT '0', |             |                          |
|                | first_arrival: varchar(21) NOT NULL                 |             |                          |
|                |                                                     |             |                          |
| subscription   | id: int(11) AUTO_INCREMENT,                         | id          | user: user_id,           |
|                | user_id: int(11) NOT NULL,                          |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |
|                |                                                     |             |                          |
| favourite      | id: int(11) AUTO_INCREMENT,                         | id          | user: user_id,           |
|                | user_id: int(11) NOT NULL,                          |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |


Below are the information on the 2 triggers created for the `vessel` table:
- before_insert_set_history, triggered before insertion of data into vessel table

    > For each matching row:
        - set check foreign key constraints to do not
        - check and insert data that are passed in into
        - vessel_history table 

- if_berth_changed_update_history, triggered before update of data into vessel table

    > if new unbthgDt is different from old unbthgDt,
    and bthgDt is different from old bthgDt:
    update vessel_history table by setting:
        - the last_bthgDt as old bthgDt,
        - bthgDt_change_count to +1,
        - last_unbthgDt as the old unbthgDt
        - unbthgDt_change_count to +1
    else if new bthgDt is different from old bthgDt:
    update vessel_history table by setting:
        - last_bthgDt to old bthgDt
        - bthgDt_change_count to +1
    else if new unbthgDt is different from old unbthgDt:
    update vessel_history table by setting:
        - last_unbthgDt as old unbthgDt
        - unbthgDt_change_count to +1
