## TP - [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)

### Why Kafka ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Use Kafka with docker
Start multiples kakfa servers (called brokers) using the docker compose recipe `docker-compose.yml` :

```bash
docker-compose -f docker-compose.yml up --detached
```
Check on the docker hub the image used :
* https://hub.docker.com/r/confluentinc/cp-kafka

#### Verify Docker containers
```
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.1.3       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```


### Kafka User Interface - Conduktor
Download and install : https://www.conduktor.io/download/

0. Using Conduktor, create a topic "mytopic" with 5 partitions
1. Find the `mytopic` topic on Conduktor and its differents configs (ISR, Replication Factor...)
2. Produce 10 messages (without a key) into it and read them
3. Look on which topic's partitions they are located.
4. Send another 10 messages but with a key called "epf"
5. Look again on which topic's partitions they are located.

Questions:
* [ ] When should we use a key when producing a message into Kafka ? What are the risks ? [Help](https://stackoverflow.com/a/61912094/3535853)
* [ ] How does the default partitioner (sticky partition) work with kafka ? [Help1](https://www.confluent.io/fr-fr/blog/apache-kafka-producer-improvements-sticky-partitioner/) and [Help2](https://www.conduktor.io/kafka/producer-default-partitioner-and-sticky-partitioner#Sticky-Partitioner-(Kafka-%E2%89%A5-2.4)-3)

### Kafka Client
Instead of using the command line interface (CLI) or Conduktor to produce and consume, we are going to code our first app.

Replace `???` and `//@TODO` inside `main` and `utils` folders

#### Producer
Using the `scala/com.github.polomarcus/utis/KafkaProducerService`, send messages to Kafka and **read them with Conduktor**

Questions :
* What are serializers (and deserializers) ? What is the one used here ? Why use them ?

#### To run your program 
```aidl
sbt "runMain com.github.polomarcus.main.MainKafkaProducer"
# OR
sbt run
# and type "2" to run "com.github.polomarcus.main.MainKafkaProducer"

# OR
docker-compose run my-scala-app bash
> sbt
> run
```

##### Question 1
Your ops team tells your app is slow and the CPU is not used much, they were hoping to help you but they are not Kafka experts.

* [ ] Look at the method `producer.flush()`, can you improve the speed of the program ? 
* [ ] What about batching the messages ? [Help](https://www.conduktor.io/kafka/kafka-producer-batching)

##### Question 2
Your friendly ops team warns you about kafka disks starting to be full. What can you do ?

Tips : 
* [ ] What about [messages compression](https://kafka.apache.org/documentation/#producerconfigs_compression.type) ? Can you implement it ? [You heard that snappy compression is great.](https://www.conduktor.io/kafka/producer-default-partitioner-and-sticky-partitioner)
* [ ] What about [messages lifetime](https://kafka.apache.org/documentation/#topicconfigs_delete.retention.ms) on your kafka brokers ? Can you change your topic config ?

##### Question 3
After a while and a lot of deployments and autoscaling (adding and removing due to traffic spikes), on your data quality dashboard you are seeing some messages are duplicates or missing. What can you do ?

* [ ] What are ["acks"](https://kafka.apache.org/documentation/#producerconfigs_acks) ? when to use acks=0 ? when to use acks=all?
* [ ] Can [idempotence](https://kafka.apache.org/documentation/#producerconfigs_enable.idempotence) help us ?
* [ ] what is ["min.insync.replicas"](https://kafka.apache.org/documentation/#brokerconfigs_min.insync.replicas) ?

#### Consumer
The goal is to read messages from our producer thanks to the "KafkaConsumerService" class.

To run your program
```aidl
sbt "runMain MainKafkaConsumer"
```

You should see messages being read.

Open Conduktor to the "Consumers" tab and look what information you can get.

Now, resend messages to kafka and look at the consumer group lag inside Conduktor.

What are we noticing ? What we change a configuration to not re read the same data always and always ?

##### Question 1
* [ ] What happens if your consumer crash while processing data ? What is the "at most once" / "at least once" / "exactly once" semantics ? [Help](https://www.conduktor.io/kafka/complete-kafka-consumer-with-java#Automatic-Offset-Committing-Strategy-1)

##### Question 2
We have introduced a bug in our program, and we would like to replay some data. Can we use Conduktor to help our consumer group? Should we create a new consumer group ?
* [ ][Help](https://kafka.apache.org/documentation.html#basic_ops_consumer_group)

#### Schema Registry
##### Intro
Look at :
* your docker-compose.yml, and the schema-registry service.
* Inside Conduktor, configure the connection with your schema-registry (http://localhost:8081)

##### Questions
* [ ] What are the benefits to use a Schema Registry for messages ? [Help](https://docs.confluent.io/platform/current/schema-registry/index.html)
* [ ] Where are stored schemas information ?
* [ ] What is serialization ? [Help](https://developer.confluent.io/learn-kafka/kafka-streams/serialization/#serialization)
* [ ] What serialization format are supported ? [Help](https://docs.confluent.io/platform/current/schema-registry/index.html#avro-json-and-protobuf-supported-formats-and-extensibility)
* [ ] Why is the Avro format so compact ? [Help](https://docs.confluent.io/platform/current/schema-registry/index.html#ak-serializers-and-deserializers-background)
* [ ] What are the best practices to run a Schema Registry in production ? [Help1](https://docs.confluent.io/platform/current/schema-registry/index.html#sr-high-availability-single-primary) and [Help2](https://docs.confluent.io/platform/current/schema-registry/installation/deployment.html#running-sr-in-production)

##### Code

[How to create a custom serializer ?](https://developer.confluent.io/learn-kafka/kafka-streams/serialization/#custom-serdes)

[Kafka Streams Data Types and Serialization](https://docs.confluent.io/platform/current/streams/developer-guide/datatypes.html#avro)

1. Inside `KafkaAvroProducerService`, replace `???` to send your first message using Avro and the Schema Registry.
2. Add a new property to the class `News` called `test: String`
3. What happens on your console log when sending messages ?
4. Modify the class `News` from `test: Option[String] = None`
5. Send another message and on Conduktor Schema Registry tab, see what happens

[About schema evolution](https://docs.confluent.io/platform/current/schema-registry/avro.html#schema-evolution)

#### Kafka Connect 
Kafka Connect is a free, open-source component of Apache Kafka® that works as a centralized data hub for simple data integration between databases, key-value stores, search indexes, and file systems. [Learn more here](https://docs.confluent.io/platform/current/connect/index.html)

[!](https://images.ctfassets.net/gt6dp23g0g38/5vGOBwLiNaRedNyB0yaiIu/529a29a059d8971541309f7f57502dd2/ingest-data-upstream-systems.jpg)

Videos to learn :
* [Video series with Confluent](https://developer.confluent.io/learn-kafka/kafka-connect/intro/)
* [Video series with Conduktor](https://www.youtube.com/watch?v=4GSmIE9ji9c&list=PLYmXYyXCMsfMMhiKPw4k1FF7KWxOEajsA&index=25)

##### Where to find connectors ?
Already-made connectors can be found on [the Confluent Hub](https://www.confluent.io/hub/)

One has already being installed via docker-compose.yml, can you spot it ?

This File Sink connector read from a topic, and write it as a file on your local machine.

##### How to start a connector ?
For tests purposes, we are going to use the [Standalone run mode](https://docs.confluent.io/kafka-connectors/self-managed/userguide.html#standalone-mode)

In order to run a [FileStream connector](https://docs.confluent.io/platform/current/connect/filestream_connector.html#kconnect-long-filestream-connectors), to read from our topics set inside the `kafka-connect-configs/connect-file-sink-properties`

```bash
docker-compose run kafka-connect bash
> ls
> cat connect-file-sink.properties
> connect-standalone connect-standalone.properties connect-file-sink.properties
```

A file should have been produced on your local container
```bash
ls 
cat test.sink.txt
```

**How can we use this kind of connector for a production use ?** 
* [ ] Can we find another connector [on the Confluent Hub](https://www.confluent.io/hub/) that can write inside a data lake ?

##### How do Serializers work for Kafka connect ?
Inside `kafka-connect-config/connect-file-sink.properties`, we need to set the serializer we used to produce the data, for our case we want to use the **String Serializer** inside our config.

Tips : [Kafka Connect Deep Dive – Converters and Serialization Explained](https://www.confluent.io/fr-fr/blog/kafka-connect-deep-dive-converters-serialization-explained/)

##### How do consumer group work for Kafka connect ?
Look on Conduktor to see if a Connector use a consumer group to bookmark partitions' offsets.

#### Monitoring and Operations
##### Questions
* [ ] Which metrics should we monitor ?
[Datadog's Kafka dashboard overview](https://www.datadoghq.com/dashboards/kafka-dashboard/)

### Useful links
* https://sparkbyexamples.com/kafka/apache-kafka-consumer-producer-in-scala/
* https://www.confluent.io/fr-fr/blog/kafka-scala-tutorial-for-beginners/
* https://developer.confluent.io/learn-kafka/kafka-streams/get-started/
* [Hands-on Kafka Streams in Scala](https://softwaremill.com/hands-on-kafka-streams-in-scala/)
* [Scala, Avro Serde et Schema registry](https://univalence.io/blog/drafts/scala-avro-serde-et-schema-registry/)
* [Usage as a Kafka Serde (kafka lib for avro)](https://github.com/sksamuel/avro4s#usage-as-a-kafka-serde)