## TP - Getting started with a data broker - [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)

### Why a Broker such as Kafka ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Use Kafka with docker
Start a kakfa server (called broker) using the docker compose recipe `docker-compose.yml` : 

```bash
docker compose -f docker-compose.yml up --detach
```

Check on the docker hub the image used : 
* https://hub.docker.com/r/confluentinc/cp-kafka

**Note** Using Mac M1 ? You have to change your image name `confluentinc/cp-kafka-connect:7.2.1.arm64` instead of `confluentinc/cp-kafka-connect:7.2.1`: https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui-arm64.yaml#L71

### Verify
```
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.1.3       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```

### Kafka User Interface
As Kafka does not have an interface, we are going to use the web app ["Kafka UI"](https://docs.kafka-ui.provectus.io/) thanks to docker compose.

Using Kafka UI on http://localhost:8080/, connect to **your existing docker kafka cluster** with `localhost:9092`.

0. Using Kafka UI, create a topic "mytopic" with 5 partitions
1. Find the `mytopic` topic on Kafka UI and its differents configs (InSync Replica, Replication Factor...)
2. Produce 10 messages (without a key) into it and read them
3. Look on which topic's partitions they are located.
4. Send another 10 messages but with a key called "my key"
5. Look again on which topic's partitions they are located.

Questions:
* [ ] When should we use a key when producing a message into Kafka ? What are the risks ? [Help](https://stackoverflow.com/a/61912094/3535853)
* [ ] How does the default partitioner (sticky partition) work with kafka ? [Help1](https://www.confluent.io/fr-fr/blog/apache-kafka-producer-improvements-sticky-partitioner/) and [Help2](https://www.conduktor.io/kafka/producer-default-partitioner-and-sticky-partitioner#Sticky-Partitioner-(Kafka-%E2%89%A5-2.4)-3)

#### Command CLI
1. Connect to your kafka cluster with 2 command-line-interface (CLI)

Using [Docker exec](https://docs.docker.com/engine/reference/commandline/exec/#description)

```
docker exec -ti tp-docker-kafka-kafka1-1 bash
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
* what does the `--from-beginning` config do ? What happens when you do not use `--from-beginning` and instead the config `--group` such as --group?
* Keep reading the message in your terminal and using Kafka UI, can you notice something in the **Consumers tab** ? 
* Now, in your terminal stop your consumer, notice the **lag** inside the **Consumer tab** on Kafka UI, it should be **0**
* With a producer, send message to the same topic, and look at the value of **lag**, what's happening ?
* Restart your consumer with the same consumer group, what's happening ?
* Trick question : What about using the `--group` option for your producer ?

#### Partition - consumer group / bookmark
1. Check consumer group with `kafka-console-consumer` : https://kafka.apache.org/documentation/#basic_ops_consumer_group
* notice if there is [lag](https://univalence.io/blog/articles/kafka-et-les-groupes-de-consommateurs/) for your group
2. read from a new group, what happened ?
3. read from an already existing group, what happened ?
4. Recheck consumer group using `kafka-console-consumer`

#### Replication - High Availability
0. Stop your broker using `docker compose down` then with the file `docker-compose-multiple-kafka.yml` start 3 brokers : `docker-compose -f docker-compose-multiple-kafka.yml up -d`
1. Create a new topic with a replication factor (RF) of 3, in case one of your broker goes down : https://kafka.apache.org/documentation/#topicconfigs
* `docker exec -ti tp-docker-kafka-kafka1-1 bash`
* `kafka-topics --create --replication-factor 3 --partitions 2 --topic testreplicated --bootstrap-server localhost:19092`
> Created topic testreplicated.
2. Describe your topic, notice where the different partitions are replicated and where are the leaders
* `kafka-topics --describe --topic testreplicated --bootstrap-server localhost:19092`
3. now, stop one of your brokers with docker : `docker stop your_container`
4. Describe your topic, check and notice the difference with the ISR (in-sync replica) config : https://kafka.apache.org/documentation/#design_ha
5. Restart your stopped broker:  `docker start your_container`
6. Check again your topic
7. Bonus: you can do this operation while keeping producing message to this kafka topic with your command line
