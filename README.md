# G1-T9 Vessel Schedule Tracking Application (VSTA)

## Table of Contents
- [Introduction](#introduction)
- [**Using the Deployed Application**](#using-the-deployed-application)
- [**Local Deployment Guide for Server Application**](#local-deployment-guide-for-server-application)
- [**Configurable Settings at Server Application**](#configurable-settings-at-server-application)
- [Local Deployment Guide for Client Application](#local-deployment-guide-for-client-application)
- [Database Design](#database-design)


## Introduction
VSTA is a web application for Port of Singapore Authority (PSA) forwarders to plan the schedule for truckers. 

The Java server application calls PORTNET's *retrieveByBerthingDate* web service Application Programming Interface (API) on a regular interval to obtain real-time information about vessel berthing a.k.a. arrival timings. 

The data is then stored in a relational database to be displayed on the user interface (UI), which is built using ReactJS.


## Using the Deployed Application
The client application is currently deployed and available on https://g1t9-vsta.netlify.app/. 

However, to use the functionalities, the server has to be started as with the procedure below.


## Local Deployment Guide for Server Application
<small><i><a href='sql/README.txt'>This section can also be viewed in *sql/README.txt*.</a></i></small>

Before running our server application, ensure the following prerequisites are fulfilled:
- Ensure your WAMP/MAMP server is running to allow access to your local database. 
- If not done so, import the [*deploy.sql*](sql/deploy.sql) script which is located in the *sql* folder. This can be done in phpmyadmin, MySQL Workbench or otherwise. 
- If not done so, ensure your local computer has the *JAVA_HOME* environment variable set to your Java SDK location.

To run the server application, open [*server-run.bat*](server-run.bat) or do a `./mvnw spring-boot:run` at the [*server*](server) folder.

The server will be accessible on *localhost:8080* by default. To terminate the connection, simply close the terminal.


## Configurable Settings at Server Application
The following settings can be found in [*server/src/main/resources*](server/src/main/resources):

*application.properties*: **port number** & credentials for the **database & Spring Security**. 
> Spring Security is used for API authentication, and change in credentials also requires change in [*client/src/js/views/base.js*](client/src/js/views/base.js) for the authorization header to be passed in every request handler.

*quartz.properties*: **Cron job** settings.

*reload.properties*: Settings that are configurable without the need to recompile code. This includes the following: 
- Website **URL**. <small><i>By default, https://g1t9-vsta.netlify.app/login.html.</i></small>
- Accepted **email domains** at user registration. <small><i>By default, sis.smu.edu.sg & gmail.com.</i></small>
- Data retrieval
    - Web service **Access key**.
    - Web service **URL**.
    - **Interval** to call API.
        - <small><i>By default, web service is called at 00:00 hours daily to retrieve the vessels’ arrival timings for the next 7 days (excluding the current day). 
        - The web service is also called hourly to retrieve the vessels’ arrival timing for the current day (00:00 - 23:59).</i></small>
- **Support email** for subscription notifications & password reset functionalities
    - Username. <small><i>By default, g1t9.vsta@gmail.com.</small></i>
    - Password.
    - [Provider Host](https://www.jhipster.tech/tips/011_tip_configuring_email_in_jhipster.html). <small><i>Note: If gmail account, to ensure emails can be sent: Manage your Google Account > Security > Less secure app access</i></small>


## Local Deployment Guide for Client Application
To use the development version,

1. Ensure that the dependencies are installed. If *node_modules* is not already present locally in the [*client*](client) folder, 
    > open [*client-dependencies.bat*](client-dependencies.bat) or do a `npm run install`.
2. To run the client application, 
    > open [*client-run.bat*](client-run.bat) or do `npm run start`. 

The UI will be accessible on *localhost:9001* by default. To terminate the connection, simply close the terminal.


## Database Design
<small><i><a href='sql/info.txt'>This section can also be viewed in *sql/info.txt*.</a></i></small>

Below are the information on the tables for [*deploy.sql*](sql/deploy.sql):

| Table Name     | Columns Name                                        | Primary Key | Foreign Key              |
|----------------|-----------------------------------------------------|-------------|--------------------------|
| user           | id: int(11) AUTO_INCREMENT                          | id          | -                        |
|                | name: varchar(32) NOT NULL                          |             |                          |
|                | email: varchar(32) NOT NULL                         |             |                          |
|                | password: varchar(60) NOT NULL                      |             |                          |
|                | token: varchar(60)                                  |             |                          |
|                |                                                     |             |                          |
| vessel         | uniqueId: varchar(60) NOT NULL                      | uniqueId    | vessel_history: uniqueId |
|                | imoN: varchar(12) DEFAULT NULL                      |             |                          |
|                | fullVslM: varchar(48) NOT NULL                      |             |                          |
|                | abbrVslM: varchar(32) NOT NULL                      |             |                          |
|                | fullInVoyN: varchar(500) DEFAULT NULL               |             |                          |
|                | inVoyN: varchar(8) NOT NULL                         |             |                          |
|                | fullOutVoyN: varchar(500) DEFAULT NULL              |             |                          |
|                | outVoyN: varchar(8) NOT NULL                        |             |                          |
|                | shiftSeqN: varchar(5) NOT NULL                      |             |                          |
|                | bthgDt: varchar(21) NOT NULL                        |             |                          |
|                | unbthgDt: varchar(21) NOT NULL                      |             |                          |
|                | berthN: varchar(10) DEFAULT NULL                    |             |                          |
|                | status: varchar(12) NOT NULL                        |             |                          |
|                | abbrTerminalM: varchar(8) DEFAULT NULL              |             |                          |
|                |                                                     |             |                          |
| vessel_history | uniqueId: varchar(60) NOT NULL                      | uniqueId    | -                        |
|                | last_bthgDt: varchar(21) NOT NULL                   |             |                          |
|                | last_unbthgDt: varchar(21) NOT NULL                 |             |                          |
|                | bthgDt_change_count: int(2) NOT NULL DEFAULT '0'    |             |                          |
|                | unbthgDt_change_count: int(2) NOT NULL DEFAULT '0'  |             |                          |
|                | first_arrival: varchar(21) NOT NULL                 |             |                          |
|                |                                                     |             |                          |
| subscription   | id: int(11) AUTO_INCREMENT                          | id          | user: user_id           |
|                | user_id: int(11) NOT NULL                           |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |
|                |                                                     |             |                          |
| favourite      | id: int(11) AUTO_INCREMENT                          | id          | user: user_id           |
|                | user_id: int(11) NOT NULL                           |             | vessel: voyage_id        |
|                | voyage_id: varchar(60) NOT NULL                     |             |                          |


Below are the information on the 2 triggers created for the *vessel* table:
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
