## TP - [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)

### Why Kafka ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Use Kafka with docker
Start multiples kafka servers (called brokers) using the docker compose recipe `docker-compose.yml` :

```bash
docker compose -f docker-compose.yml up --detach
```
Check on the docker hub the image used :
* https://hub.docker.com/r/confluentinc/cp-kafka

**Note** Using Mac M1 ? You have to change your image name `confluentinc/cp-kafka-connect:7.2.1.arm64` instead of `confluentinc/cp-kafka-connect:7.2.1`: https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui-arm64.yaml#L71

#### Verify Docker containers
```
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.1.3       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```

### Kafka User Interface
As Kafka does not have an interface, we are going to use the web app ["Kafka UI"](https://docs.kafka-ui.provectus.io/) thanks to docker compose.

### Kafka with an User Interface is better
As Kafka does not have an interface, we are going to use the web app ["Kafka UI"](https://docs.kafka-ui.provectus.io/) thanks to docker compose.

Using Kafka UI on http://localhost:8080/, connect to **your existing docker kafka cluster** with `localhost:9092`.

0. Using Kafka UI, create a topic "mytopic" with 5 partitions
1. Find the `mytopic` topic on Kafka UI and its different configs (InSync Replica, Replication Factor...)
2. Produce 10 messages (without a key) into it and read them
3. Look on which topic's partitions they are located.
4. Send another 10 messages but with a key called "my key"
5. Look again on which topic's partitions they are located.

Questions:
* [ ] When should we use a key when producing a message into Kafka ? What are the risks ? [Help](https://stackoverflow.com/a/61912094/3535853)
* [ ] How does the default partitioner (sticky partition) work with kafka ? [Help1](https://www.confluent.io/fr-fr/blog/apache-kafka-producer-improvements-sticky-partitioner/) and [Help2](https://www.conduktor.io/kafka/producer-default-partitioner-and-sticky-partitioner#Sticky-Partitioner-(Kafka-%E2%89%A5-2.4)-3)

### Coding our own Kafka Client using Scala
Instead of using the command line interface (CLI) or Kafka UI to produce and consume, we are going to code our first app like pros.

#### Producer - the service in charge of sending messages
We are going to replace all `???` and all `//@TODO` inside `src/scala/main/com.github.polomarcus/main` and `src/scala/main/com.github.polomarcus/utils` folders.

First, Using the `scala/com.github.polomarcus/utis/KafkaProducerService`, send messages to Kafka and **read them with the Kafka UI** (Topics / Select topic name / Messages)

Questions :
* What are serializers and deserializers ? What is the one used here ? And why use them ?

#### To run your program with SBT or Docker
There are 3 ways to run your program :
```bash
sbt "runMain com.github.polomarcus.main.MainKafkaProducer"
# OR
sbt run
# and type "4" to run "com.github.polomarcus.main.MainKafkaProducer"

# OR
docker compose run my-scala-app bash
> sbt
> run
```

After running with your sbt shell `runMain com.github.polomarcus.main.MainKafkaProducer`  you should see this kind of log :
```declarative
INFO  c.g.p.utils.KafkaProducerService$ - 
        Sending message with key "key20" and value "filter20"
```

##### Question 1
Your ops team tells your app is slow and the CPU is not used much, they were hoping to help you but they are not Kafka experts.

* [ ] Look at the method `producer.flush()` inside KafkaProducerService (Right click on "tp-kafka-api" and "Find in files" to look for it), can you improve the speed of the program ? 
* [ ] What about batching the messages ? [Help](https://www.conduktor.io/kafka/kafka-producer-batching). Can this help your app performance ?

##### Question 2
Your friendly ops team warns you about kafka disks starting to be full. What can you do ?

Tips : 
* [ ] What about [messages compression](https://kafka.apache.org/documentation/#producerconfigs_compression.type) ? Can you implement it ? [You heard that snappy compression is great.](https://learn.conduktor.io/kafka/kafka-message-compression/)
* [ ] What are the downside of compression ?
* [ ] What about [messages lifetime](https://kafka.apache.org/documentation/#topicconfigs_delete.retention.ms) on your kafka brokers ? Can you change your topic config ?
* [ ] What are the downside of increasing your messages lifetime ?

##### Question 3
After a while and a lot of deployments and autoscaling (adding and removing due to traffic spikes), on your data quality dashboard you are seeing some messages are duplicated or missing. What can you do ?

* [ ] What are ["acks"](https://kafka.apache.org/documentation/#producerconfigs_acks) ? when to use acks=0 ? when to use acks=all?
* [ ] Can [idempotence](https://kafka.apache.org/documentation/#producerconfigs_enable.idempotence) help us ?
* [ ] what is ["min.insync.replicas"](https://kafka.apache.org/documentation/#brokerconfigs_min.insync.replicas) ?

#### Consumer - the service in charge of reading messages
The goal is to read messages from our producer thanks to the ["KafkaConsumerService" class](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/utils/KafkaConsumerService.scala#L34-L35).

To run your program (if you don't change anything there will be a `an implementation is missing` error, **that's normal.**
```bash
sbt "runMain com.github.polomarcus.main.MainKafkaConsumer"
> scala.NotImplementedError: an implementation is missing --> modify the `utils/KafkaConsumerService` class
```

[After modifying the code here](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/utils/KafkaConsumerService.scala#L34-L35) and read the code of KafkaConsumerService, you should see messages being read on your terminal.

```declarative
INFO  c.g.p.utils.KafkaProducerService$ -  Reading :
Offset : 20 from partition 0
Value : value1
Key : key1
(...)
INFO  c.g.p.utils.KafkaProducerService$ - No new messages, waiting 3 seconds
```

Open Kafka UI to the "Consumers" tab and look what new information you can get (*hint: consumer group*).

Now, resend messages to kafka thanks to `runMain com.github.polomarcus.main.MainKafkaProducer` and look at the consumer group lag inside Kafka UI.

What are we noticing ? Can we change a configuration to not read again the same data always and always ? Modify it inside our KafkaConsumerService.

##### Question 1
* [ ] What happens if your consumer crash while processing data ?
* What are the "at most once" / "at least once" / "exactly once" semantics ? [Help](https://www.conduktor.io/kafka/complete-kafka-consumer-with-java#Automatic-Offset-Committing-Strategy-1)
* What should we use ?

##### Question 2
We have introduced a bug in our program, and we would like to replay some data. Can we use Kafka UI to help our consumer group? Should we create a new consumer group ?
* [ ][Help](https://kafka.apache.org/documentation.html#basic_ops_consumer_group)

#### Schema Registry - to have more control on our messages
##### Intro
Look at :
* your docker-compose.yml, and the schema-registry service.
* Inside Kafka UI, you can check the Schema registry tab, it should be empty : http://localhost:8080/ui/clusters/local/schemas

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

1. Inside `KafkaAvroProducerService`, discover the "@TODO" and send your first message using Avro and the Schema Registry using `runMain com.github.polomarcus.main.MainKafkaAvroProducer`
2. Inside `KafkaAvroConsumerService`, modify the 2 `???` and run `runMain com.github.polomarcus.main.MainKafkaAvroConsumer`

##### Schema Evolution
3. Add a new property to the class `News` inside the folder "src/main/scala/.../models" called `test: String` : it means a column "test" with type String
4. What happens on your console log when sending messages again with `runMain com.github.polomarcus.main.MainKafkaAvroProducer`. Why ?
5. Modify the class `News` from `test: Option[String] = None`
6. Send another message and on Kafka UI Schema Registry tab, see what happens

We've experienced a [schema evolution](https://docs.confluent.io/platform/current/schema-registry/avro.html#schema-evolution).

#### Kafka Connect 
> Kafka Connect is a free, open-source component of Apache Kafka® that works as a centralized data hub for simple data integration between databases, key-value stores, search indexes, and file systems. [Learn more here](https://docs.confluent.io/platform/current/connect/index.html)

![](https://images.ctfassets.net/gt6dp23g0g38/5vGOBwLiNaRedNyB0yaiIu/529a29a059d8971541309f7f57502dd2/ingest-data-upstream-systems.jpg)

Some videos that can be helpful :
* [Video series with Confluent](https://developer.confluent.io/learn-kafka/kafka-connect/intro/)
* [Video series with Conduktor](https://www.youtube.com/watch?v=4GSmIE9ji9c&list=PLYmXYyXCMsfMMhiKPw4k1FF7KWxOEajsA&index=25)

##### Where to find connectors ?
Already-made connectors can be found on [the Confluent Hub](https://www.confluent.io/hub/)

One has already being installed via docker-compose.yml, can you spot it ?

This File Sink connector read from a topic, and write it as a file on your local machine.

##### How to start a connector ?
For tests purposes, we are going to use the [Standalone run mode](https://docs.confluent.io/kafka-connectors/self-managed/userguide.html#standalone-mode) as we do not have a cluster.

We'll run a [FileStream connector](https://docs.confluent.io/platform/current/connect/filestream_connector.html#kconnect-long-filestream-connectors) - a connector that read from a topic and write it as a text file on our server.

To read from a specific topic, we need to configure this file [`kafka-connect-configs/connect-file-sink-properties`](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/kafka-connect-configs/connect-file-sink.properties)

To run our FileStream connector we can operate like this (have a look to the docker-compose.yml file to understand first)
```bash
docker compose run kafka-connect bash
> ls
> cat connect-file-sink.properties
> connect-standalone connect-standalone.properties connect-file-sink.properties
```

Congrats, a file **should** have been produced on your **local** container :
```bash
ls 
cat test.sink.txt
```

**How can we use this kind of connector for a production use ( = real life cases ) ?** 
* [ ] Can we find another connector [on the Confluent Hub](https://www.confluent.io/hub/) that can write inside **a data lake** instead of a simple text file in one of our servers ?

##### How do Serializers work for Kafka connect ?
Inside `kafka-connect-config/connect-file-sink.properties`, we need to set the serializer we used to produce the data, for our case we want to use the **String Serializer** inside our config.

Tips : [Kafka Connect Deep Dive – Converters and Serialization Explained](https://www.confluent.io/fr-fr/blog/kafka-connect-deep-dive-converters-serialization-explained/)

##### How do consumer group work for Kafka connect ?
Look on Kafka UI to see if a Connector use a consumer group to bookmark partitions' offsets.

#### Kafka Streams
[Kafka Streams Intro](https://kafka.apache.org/documentation/streams/)

[Streams Developer Guide](https://docs.confluent.io/platform/current/streams/developer-guide/dsl-api.html#overview)

* [ ] What are the differences between the consumer, the producer APIs, and Kafka streams ? [Help1](https://stackoverflow.com/a/44041420/3535853)
* [ ] When to use Kafka Streams instead of the consumer API ?
* [ ] What is a `SerDe`?
* [ ] What is a KStream?
* [ ] What is a KTable? What is a compacted topic ?
* [ ] What is a GlobalKTable?
* [ ] What is a [stateful operation](https://developer.confluent.io/learn-kafka/kafka-streams/stateful-operations/) ?

What are the new [configs](https://kafka.apache.org/documentation/#streamsconfigs) we can use ?


##### Code
![MapReduce](http://coderscat.com/images/2020_09_10_understanding-map-reduce.org_20200918_164359.png)

We are going to aggregate (count) how many times we receive the same word inside the topic [ConfService.TOPIC_OUT](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/conf/ConfService.scala#L8). For this we need to `map` every message to separate every words.

After this, we are going to filter any messages that contains `"filter"`

Then, after we filter these messages, we are going to read another topic, to join it with our aggregate


Inside `KafkaStreamsService` write code to perform :
* Write a filter function that remove every message containing the letter "filter"
* Write a function that join a KTable (our word count) with a new KStream that read from a new topic you need to create `word`.
This should display in the console the value of the KTable and the new KStream based on the same key. If we send a message with a key "value10" and the value "active", it should return key : "value10" and value (6, active), that is to say the joined values.


To execute the code you would need this command :
```bash
sbt "runMain com.github.polomarcus.main.MainKafkaStream"
```
* [ ] Stop your application once you have processed messages. If you restart your applicaton and resend messages, pay attention to your values inside the KTable. [How is the KTable state saved ?](https://docs.confluent.io/platform/current/streams/architecture.html#fault-tolerance)

Lot of examples can be found [here](https://blog.rockthejvm.com/kafka-streams/)

#### Monitoring and Operations
##### Questions
* [ ] Which metrics should we monitor once our application is deployed to the real world ?
[Datadog's Kafka dashboard overview](https://www.datadoghq.com/dashboards/kafka-dashboard/)

Have a look to the Kafka UI metrics : http://localhost:8080/ui/clusters/local/brokers/1/metrics

### Useful links
* https://sparkbyexamples.com/kafka/apache-kafka-consumer-producer-in-scala/
* https://www.confluent.io/fr-fr/blog/kafka-scala-tutorial-for-beginners/
* https://developer.confluent.io/learn-kafka/kafka-streams/get-started/
* [Hands-on Kafka Streams in Scala](https://softwaremill.com/hands-on-kafka-streams-in-scala/)
* [Scala, Avro Serde et Schema registry](https://univalence.io/blog/drafts/scala-avro-serde-et-schema-registry/)
* [Usage as a Kafka Serde (kafka lib for avro)](https://github.com/sksamuel/avro4s#usage-as-a-kafka-serde)
* [Kafka Streams example](https://blog.rockthejvm.com/kafka-streams/)
