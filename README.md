# spring-kafka-mongodb-reactive

This project is a MVP for creating accounts using Spring boot reactive app with MongoDB & Kafka

# Running

You can configure the connection to MongoDB & Kafka on application.properties file or use docker-compose to run them locally. Then you can start the server with the following command:
```sh
./mvnw spring-boot:run
```

# Packaging

You can package the Spring server by doing:
```sh
./mvnw clean package
```

# Tooling

You can run kafka & mongoDB with docker-compose:
```sh
docker-compose up -d
```

You can use Kafka console consumer to observe records in the topic
```sh
docker exec -it kafka ./opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic account --from-beginning
```

# Use case

Get all accounts:
```
curl reactive:8081/account/
```

Get an accounts with id = 256479:
```
curl reactive:8081/account/256479
```

Create account:
```
curl -X POST -H "Content-Type: application/json" -d "{\"id\":\"4748\",\"owner\":\"Toto\",\"value\":\"7.54\"}" reactive:8081/account/add 
{"id":"4748","owner":"Toto","value":7.54}
```
