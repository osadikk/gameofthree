# Game of Three Assignment

This project aims to play a game which is developed based on distributed systems. Those systems are designed through 
microservices,CQRS,DDD,event sourcing and websocket. In this project, four modules run separately from each other.

The project is developed through Spring Boot, Kafka, Axon (which is very powerful framework for developing DDD and CQRS projects),
Java 8 (which is required),Mongo DB(whis is for event store and CRUD) and Euraka Server.For UI Bootstrap,SockJS,Stomp,Thymleaf,
Jquery were used.

# How to Play

When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender.
The same rules are applied until one player reaches the number 1(after the division).

Players can play the game on a browser which is located on http://localhost:8080/

Alternatively, this project can be played on a Swagger UI(actually,Swagger is a documentation UI for REST API) which those are located on

(Query Project) http://localhost:8088/swagger-ui.html 

(Command Project) http://localhost:8087/swagger-ui.html 


# Installation

## Requirements

* JDK8 https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

* Kafka https://kafka.apache.org/quickstart

* Mongo DB https://docs.mongodb.com/manual/installation/

* Maven https://maven.apache.org/install.html

## How to build

* git clone https://github.com/osadikk/gameofthree.git
* cd GameofThreeAsignment
* mvn clean package

## How to run

Make sure Mongo DB and Kafka(include Zookeper) servers are up. Database running on port 27017 which is default for MongoDB.
Kafka running on port 9092 and Zookeper on 2181(Default port).

```
For Windows
Start Zookeper zookeeper-server-start.bat ../../config/zookeeper.properties (under bin/windows file)

Start Kafka kafka-server-start.bat ../../config/server.properties (under bin/windows file)

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic game_topic


For Linux
zookeeper-server-start.sh config/zookeeper.properties (under bin file)

kafka-server-start.sh config/server.properties (under bin file)

kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic game_topic
```

After all servers are up you can start all modules separately

```
cd GameofThreeAsignment

java -jar EurakaServer/target/EurakaServer-0.0.1-SNAPSHOT.jar

java -jar gameofthreeassignment-command/target/gameofthreeassignment-command-0.0.1-SNAPSHOT.jar

java -jar gameofthreeassignment-query/target/gameofthreeassignment-query-0.0.1-SNAPSHOT.jar

java -jar gameofthreeassignment-web/target/gameofthreeassignment-web-0.0.1-SNAPSHOT.jar

```


