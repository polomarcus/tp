## TP - [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)

### Why Kafka ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Use Kafka with docker
Start multiples kakfa servers (called brokers) using the docker compose recipe `docker-compose.yml` : 

```bash
docker compose up --detach
```

Check on the docker hub the image used : 
* https://hub.docker.com/r/confluentinc/cp-kafka

### Verify
```
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.1.3       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```

### Kafka User Interface - Conduktor
Download and install : https://www.conduktor.io/download/

0. Using Conduktor, create a topic "mytopic"
1. Find the `lyrics` topic
2. Read the first 10 messages of this topic
3. Using Conduktor, Produce 3 messages into it

### Produce messages using a Scala Client

### Consume messages using a Scala client


#### Command CLI
1. Connect to your kafka cluster with 2 command-line-interface (CLI)

Using [Docker exec](https://docs.docker.com/engine/reference/commandline/exec/#description)

```
docker exec -ti tp-docker-kafka_kafka1_1 bash
> pwd
```

```
> kafka-topics # to get help on this command
# To list all topic you can use :
> kafka-topics --describe --bootstrap-server localhost:19092
```

Pay attention to the `KAFKA_ADVERTISED_LISTENERS` config from the docker-compose file.

2. Create a "mailbox" - a topic with the default config : https://kafka.apache.org/documentation/#quickstart_createtopic
3. Check on which Kafka broker the topic is located using `--describe`
5. Send events to a topic on one terminal : https://kafka.apache.org/documentation/#quickstart_send
4. Keep reading events from a topic from one terminal : https://kafka.apache.org/documentation/#quickstart_consume
* try the default config
* what does the `--from-beginning` config do ?
* what about using the `--group` option for your producer ?
6. stop reading
7. Keep sending some messages to the topic

#### Partition 
1. Check consumer group with `kafka-console-consumer` : https://kafka.apache.org/documentation/#basic_ops_consumer_group
* notice if there is [lag](https://univalence.io/blog/articles/kafka-et-les-groupes-de-consommateurs/) for your group
2. read from a new group, what happened ?
3. read from a already existing group, what happened ?
4. Recheck consumer group

#### Replication - High Availability
0. use `docker-compose-multiple-kafka.yml` to start 2 more brokers
1. Increase replication in case one of your broker goes down : https://kafka.apache.org/documentation/#topicconfigs
2. Stop one of your brokers with docker
3. Describe your topic, check the ISR (in-sync replica) config : https://kafka.apache.org/documentation/#design_ha
4. Restart your stopped broker
5. Check again your topic
