Directories Guide:
Server files are at "project-g1-t9/server/"
Server's externalized parameters are configurable at "project-g1-t9/server/src/main/resources/"
SQL deployment file is at "project-g1-t9/sql/deploy.sql"
Project's Java Documentation is at "project-g1-t9/java-documentation/index.html"
Client files are at "project-g1-t9/client/"

---------------------------------------------------------------------------------------------------------------------------------

Deployment Guide (Server):
1) Make sure WAMP is running
2) Make sure your local computer has the JAVA_HOME environment variable set to your Java SDK location
3) Using MySQL Workbench/phpMyAdmin (port 3306), import the SQL script at "project-g1-t9/sql/deploy.sql", excute
4) To run the server application, open "project-g1-t9/server-run.bat" (Runs at localhost:8080 by default)

---------------------------------------------------------------------------------------------------------------------------------

You can access the client at: https://g1t9-vsta.netlify.app/
Otherwise, follow the deployment guide below to run the client application locally

Deployment Guide (Client):
1) Make sure you have npm installed (Get npm at https://www.npmjs.com/get-npm)
2) Open "project-g1-t9/client-dependencies.bat", this installs all dependencies needed 
3) Open "project-g1-t9/client-run.bat", this serves the client application at localhost:9001

---------------------------------------------------------------------------------------------------------------------------------

User Guide (Externalized Configuration Parameters):
All configurable files are located at "project-g1-t9/server/src/main/resources/", and can be updated without redeploying server

a) Time interval to call the API
	1) Go to "reload.properties"
	2) Update the variable "quartz.properties.interval" to your desired value (must be in cron expression)
	3) You can use this link to generate cron expressions: https://www.freeformatter.com/cron-expression-generator-quartz.html

b) Web Service Access key
	1) Go to "application.properties"
	2) Update the variable "spring.security.user.name" and "spring.security.user.password" to your desired values (e.g. userABC, password123)
	3) This updates the web service access key to require the following key-value pair in the request headers:
		{"Authorization": "Basic dXNlckFCQzpwYXNzd29yZDEyMw=="}
	4) Note that "dXNlckFCQzpwYXNzd29yZDEyMw==" is "userABC:password123" encoded in base-64

c) Email domains allowed
	1) Go to "reload.properties"
	2) Update the variable "domain.acceptedDomains" to include the email domains allowed
	3) Note that for this variable, allowed email domains are separated by commas
		e.g. "domain.acceptedDomains = sis.smu.edu.sg, gmail" allows "xxxx@gmail.com" and "xxxx@sis.smu.edu.sg"

d) Email used to send emails for user subscriptions/reset password
	1) Go to "reload.properties"
	2) Update "spring.mail.username" with email address
	3) Update "spring.mail.password" with email password
	4) Update "spring.mail.host" and "spring.mail.ssl.trust" to match your email domain's SMTP server (e.g. "smtp.gmail.com" for gmail)

e) Frontend client url (This affects the link sent to users for changing password)
	1) Go to "reload.properties"
	2) Update "url" to the full url of the deployed frontend web application

 