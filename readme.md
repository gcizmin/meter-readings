# Meter Readings

Application (REST API) that processes metering data (meter reading).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See "Build and run" for notes on how to build and run the project.

### Development prerequisites

Make sure you have java jdk, maven and git installed. 

Application is developed and tested with java 11, but should work with any java version from 8 up.

Intellij IDEA was used as IDE for development. Whatever IDE you use, make sure to install latest *Lombok* plugin and configure proper PATH and HOME variables for java jdk and maven.

## UNIT testing

### From IDE

You can run any test from IDE

### Integration tests

These tests test basic DB access using H2 in-memory database.

H2 in memory database is automatically loaded when any of the tests are being run from IDEA.

```
AddressRepositoryIntegrationTest, ClientRepositoryIntegrationTest, MeterReadingRepositoryIntegrationTest, MeterRepositoryIntegrationTest
```

###  Mock tests

These tests test functionality by mocking the dao layer using Mockito framework.

```
MeterReadingsServiceImplTest
```

### From maven

TBD Not implemented

## Build and run

Download zipped code, extract it and open command prompt in root directory where pom.xml is.

Make sure you have all path variables set (maven, java JDK)

Build application with maven command

```
mvn clean package
```

Run application in the same folder with command

```
java -jar target/meter-readings-0.0.1-SNAPSHOT
```

## Testing

### H2 in-memory DB

You can check database content from browser: 

```
http://localhost:8080/h2
```

Configuration is:

```
JDBC URL : jdbc:h2:mem:mrdb
User Name: sa
Password :
```

### Functionalities

Use cases can be tested from Postman or any other similar tool.

You can check what is already in database to test viewing and adding existing and non existing meter readings-

UC1: Aggregate meter reading for year

```
http://localhost:8080/meter_readings/total/serial_number/327p61/year/2019
```

UC2: Get all meter readings for year

```
http://localhost:8080/meter_readings/yearly_readings/serial_number/327p61/year/2019
```

UC3: Get meter reading for month for year

```
http://localhost:8080/meter_readings/reading/serial_number/327p61/year/2019/month/11
```

UC4: Add additional meter reading

```
http://localhost:8080/meter_readings/add_monthly_reading
Body:
{"serialNumber":"327p61","month":"10","year":2019,"reading":333}
```

## Security

TBD Not implemented

## Logging configuration

TBD Not implemented