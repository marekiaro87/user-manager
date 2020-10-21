# User Manager
This application exposes RESTful APIs to manage users.

# Prerequisites

In order to successfully use/test this application you need the following software:

- [Java](https://www.java.com/en/download/) - version 8 or above
- [Git](https://git-scm.com/) 
- [Docker](https://www.docker.com/get-started)
- [Kafka](https://kafka.apache.org/) - not mandatory

# Getting Started
## Create and run the application
Download this repository and create the application artifact running the following commands:
```bash
git clone https://github.com/marekiaro87/user-manager.git
cd user-manager
.\mvnw package
```

Then create the Docker Image:
```bash
docker build -t ricardo/usermanager .
```

Finally run the application:
```bash
docker-compose up -d
```

Wait some seconds to let all the servers start (the micro-service, the MySQL database, the Kafka Zookeeper and the Kakfa Broker).

## Testing the application
As said the application exposes some RESTful API. You can access the following link  with your browser 
to have a look to the Swagger API Documentation:

```bash
http://localhost:8080/swagger-ui.htm
```

You can test from here all the APIs. Since the service allows user creation only from
Switzerland you need to pass by a VPN to simulate your location. Another option can be to pass
the **X-FORWARDED-FOR** attribute in the **HTTP Header** with a Swiss IP address, as shown below:

```bash
curl -d '{"firstName": "Test User", "password": "password", "address": "Address 1", "email" : "test@email.com"}' -H "Content-Type: application/json" --header "X-Forwarded-For: 212.51.159.2" -X POST http://localhost:8080/user
```

## Retrieving the Events
For each mutating operation the application sends an event on a Kafka Topic named
**user.updates**. In order to test that everything is working properly you can start
a simple **Kafka Console Consumer** to retrieve the messages as soon as they are produced
with the following command (use the .sh script if you are using a linux machine):

```bash
cd %KAFKA_HOME_FOLDER%\bin\windows
.\kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9093 --topic user.updates --from-beginning
```

## Accessing to the DB
A practice UI has been provided to access to the DB data. Access to the following link with your browser:

```bash
http://localhost:9000/
```

Use the following credentials to log-in:
- Server: mysql-db
- User: root
- Password: root
- Database: user_manager

# Improvements
The application has been developed for didactic purposes so some important points haven't been addressed and could still be improved, such as:

- Add a proper Java Documentation: classes have not been commented (they are simple, but it is a good practice to provide some info)
- Increase test coverage: usually a good software has at least 80%/90% of testing coverage
- Add meaningful logging: it is quite hard to find out what it is going on in the application if something goes wrong
- Add object validation: REST API models don't have a proper validation layer
- Avoid storing password in clear: the application stores password in the DB, and it is a good practice to not save them in clear (encryption)
- Security: Micro-service, DB and Kafka don't have proper security settings
- Event Structure: the events could be defined following a CNCF approved standard like [cloud events](https://github.com/cloudevents/spec)