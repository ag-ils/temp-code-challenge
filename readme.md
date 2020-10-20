# Athena 

### Project Setup

1. Download and install Win 64 MariaDB. https://downloads.mariadb.org/
1. Download and install Intellij Community Edition. https://www.jetbrains.com/idea/download/
1. Download and install Maven. https://maven.apache.org/download.cgi
1. Clone the API from this GitHub repository in to a local folder. `Documents\github\athena-api`
1. Start Intellij. It should load in project settings.
1. Setup Intellij with the following project structure:
    * Java SDK: 11.0.7 (Amazon Corretto)
    * Project Level: 11

Setup System Environment Variables located in resources/application.properties

Environment Variable setup:

* `spring.datasource.url=${ATHENA_DB_URL}`
* `spring.datasource.username=${ATHENA_DB_USERNAME}`
* `spring.datasource.password=${ATHENA_DB_PASSWORD}`
* `spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MariaDBDialect`
* `spring.jpa.hibernate.ddl-auto=${ATHENA_DB_DDL_TYPE}`
* `security.user.name=${ATHENA_API_USERNAME}`
* `security.user.password=${ATHENA_API_PASSWORD}`

## Starting the application

Install maven, then, while in the project directory, run:

* `mvn spring-boot:run`

This will spin up the application with the given application.properties configuration.

## Testing the application

With Intellij Community Edition, you can Right Click the name of the class in the file to run a test for that file, to Run Tests to setup a test run evironment for that specific file or you can run test all with: ``mvn clean verify``

Alternatively, you can spin up the application and use PostMan to connect to the various API end-points.

TODO: Setup security access layers for different roles.

TODO: Refactor login process: https://www.websparrow.org/spring/spring-boot-spring-security-with-jpa-authentication-and-mysql

## API End-Points

URI Prefix: `/api/v1`

### Users

* POST `/users` - Creates a new user.
* PUT `/users/{id}` - Updates an existing user.
* GET `/users` - Gets all users.
* GET `/users/{id}` - Gets a user by id.
* DELETE `/users/{id}` - Deletes a user by id.

## Treks

* POST `/treks` - Creates a new trek.
* PUT `/treks/{id}` - Updates an existing trek.
* GET `/treks` - Gets all treks.
* GET `/treks/{id}` - Gets a trek by id.
* DELETE `/treks/{id}` - Deletes a trek by id.


### Trek Locations

* POST `/trekLocations` - Creates a new trek location.
* PUT `/trekLocations/{id}` - Updates an existing trek location.
* GET `/trekLocations` - Gets all trek locations.
* GET `/trekLocations/{id}` - Gets a trek location by id.
* DELETE `/trekLocations/{id}` - Deletes a trek location by id.

### Devices

* POST `/devices` - Creates a new device.
* PUT `/devices/{id}` - Updates an existing device.
* GET `/devices` - Gets all devices.
* GET `/devices/{id}` - Gets a device by id.
* DELETE `/devices/{id}` - Deletes a device by id.

### Clients

* POST `/clients` - Creates a new client.
* PUT `/clients/{id}` - Updates an existing client.
* GET `/clients` - Gets all clients.
* GET `/clients/{id}` - Gets a client by id.
* DELETE `/clients/{id}` - Deletes a client by id.

### User Client Devices

* POST `/userClientDevices` - Creates a new user client device link.
* PUT `/userClientDevices/{id}` - Updates an existing user client device link.
* GET `/userClientDevices` - Gets all user client device links.
* GET `/userClientDevices/{id}` - Gets a user client device link by id.
* DELETE `/userClientDevices/{id}` - Deletes a user client device link by id.
* GET `/userClientDevices/user/{id}` - Gets all user client device links by user id.
* GET `/userClientDevices/client/{id}` - Gets all user client device links by client id.
* GET `/userClientDevices/device/{id}` - Gets all user client device links by device id.

Example change