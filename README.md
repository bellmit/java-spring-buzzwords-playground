# Java & Spring Playground
This repository contains my personal experiments with Spring and Java ecosystem. 

The code is published under Beerware license.

## Used technology (so far)

* [x] Spring 5 and Reactive Web Server
* [x] Flyway (for database migrations)
* [x] Docker

## Fully Dockerized 
The application can be run using Docker
```
docker-compose up
```
Changes are pushed to Docker using
```
./gradlew bootJar && docker-compose restart app
```
Remote debugging is possible by connecting to JVM inside Docker.

### Ports

* 8080 - Spring Web Application
* 5005 - JVM Remote Debugging
* 5433 - PostgreSQL database

## Things to do in the nearest future

* [ ] Docker with JVM allowing for hot-swapping code even with bigger changes
* [ ] Queue communication
* [ ] Event Sourcing
* [ ] Polyglot Persistence