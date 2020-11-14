# G1-T9 Vessel Schedule Tracking Application

VSTA is a Java application for forwarders to plan the schedule for truckers. 
The server application calls PORTNET's `retrieveByBerthingDate` web service Application Programming Interface (API) 
on a regular interval to obtain real-time information about vessel berthing/arrival timings and 
stores them in a relational database to be displayed on the user interface. 
- By default, the web service is called at 00:00 hours daily to retrieve the vessels’ arrival timings for the next 7 days (excluding the current day). 
- The web service is also called hourly to retrieve the vessels’ arrival timing for the current day (00:00 - 23:59).

## Configurable Settings
The following settings can be found in `server/src/main/resources`.

Credentials for the **database & Spring Security** can be found in `application.properties`.

Settings for the following are configurable at `reload.properties` without the need to recompile code: 
- Accepted **email domains** at user registration
- Data retrieval **API**
    - Interval to call the API
    - Web service access key & URL
- **Support email** for subscription notifications & resetting password functionalities
    - Username
    - Password
    - [Host](https://www.jhipster.tech/tips/011_tip_configuring_email_in_jhipster.html)

*Note: If gmail host is used, ensure the following for the account so emails can be sent smoothly:*
> Manage your Google Account > Security > Less secure app access > On

Other cron job settings can also be found in `quartz.properties`.


## Production
The application is currently available on https://g1t9-vsta.netlify.app/.

## Development

Prerequisites 
- Ensure your *WAMP* server is turned on to allow access to the database. 
- If not done so, import the `deploy.sql` script which is located in the `server/sql` folder.
- If not done so, ensure your local computer has the `JAVA_HOME` environment variable set to your Java SDK location.
- If `node_modules` not already present in the `client` folder, install the dependencies with `npm run install`.

To start the server application, use `run.bat` which is located in the `server` folder.
> The server will be accessible on `localhost:8080`.

To start the client application, open the `client` folder in your terminal and do a `npm run start`. 
> The UI will be accessible on `localhost:9001`.

To terminate the connections, simply close the terminals.
