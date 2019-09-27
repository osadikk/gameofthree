# Game of Three Assignment

This project aim to play a game which is developed on distributed systems. Those systems are designed with 
microservices,CQRS,DDD,event sourcing and websocket. In this project, four modules runs separately each other.

This project developed with Spring Boot, Kafka, Axon (which is very powerful framework for developing DDD and CQRS projects),
Java 8 (which is required),Mongo DB(whis is for event store and CRUD) and Euraka Server.For UI Bootstrap,SockJS,Stomp,Thymleaf,
Jquery were used.

# How to Play

When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender.
The same rules are applied until one player reaches the number 1(after the division).

Players can play the game on a browser which is located on http://localhost:8080/

Alternatively, this project can be play on a Swagger UI(actually,Swagger is a documentation UI for REST API) which those are located on

http://localhost:8088/swagger-ui.html (Query Project)

http://localhost:8087/swagger-ui.html (Command Project)


# Installation

## Requirements

JDK8 https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Kafka https://kafka.apache.org/quickstart

Mongo DB https://docs.mongodb.com/manual/installation/

Maven https://maven.apache.org/install.html

## How to build





