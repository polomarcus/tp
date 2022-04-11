# Practices - Data engineering

Have a stackoverflow account : https://stackoverflow.com/

Have a github account : https://github.com/

And a github repo to push your code.

## Docker and Compose 
Take time to read and install

https://docs.docker.com/get-started/overview/
```
docker --version
Docker version 20.10.14
```

https://docs.docker.com/compose/
```
docker-compose --version
docker-compose version 1.29.2
```

## [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)


### Why Kafka ?
https://kafka.apache.org/documentation/#introduction

- What problems does Kafka solve ?
- Image which use cases 
- What is a producer ?
- What is a consumer ?
- What are consumer groups ?
- What is a offset ?
- Why using partitions ? 
- Why using replication ?
- What are  In-Sync Replicas (ISR) ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Try to install Kafka without docker
https://kafka.apache.org/documentation/#gettingStarted

### Try to install kafka with docker

Start multiples kakfa servers (called brokers) using a docker compose recipe : 
* https://github.com/conduktor/kafka-stack-docker-compose
* https://github.com/conduktor/kafka-stack-docker-compose#single-zookeeper--multiple-kafka

Check on the docker hub the image used : 
* https://hub.docker.com/r/confluentinc/cp-kafka


### Verify
```
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.0.1       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```

### Getting started with Kafka
1. Connect to your kafka cluster with 2 command-line-interface (CLI)

Using [Docker exec](https://docs.docker.com/engine/reference/commandline/exec/#description)

```
docker exec -ti my_kafka_container_name bash
> pwd
```

```
> kafka-topics 
> kafka-topics --describe --bootstrap-server localhost:9092 
```
Read this blog article to fix `Broker may not be available.` error : https://rmoff.net/2018/08/02/kafka-listeners-explained/

Pay attention to the `KAFKA_ADVERTISED_LISTENERS` config.

2. Create a "mailbox" - a topic with the default config
3. Check on which Kafka broker the topic is located
5. Send events to a topic on one terminal
4. Keep reading events from a topic from one terminal
* try the default config
* what does the `--from-beginning` config do ?
* what about using the `--group` option ?
6. stop reading
7. Keep sending some messages to the topic

#### Partition 
1. Check consumer group with `kafka-console-consumer`
* notice if there is [lag](https://univalence.io/blog/articles/kafka-et-les-groupes-de-consommateurs/) for your group
2. read from a new group, what happened ?
3. read from a already existing group, what happened ?
4. Recheck consumer group

#### Replication - High Availability
1. Increase replication in case one of your broker goes down
2. Stop one of your brokers with docker
3. Describe your topic, check the ISR (in-sync replica) config
4. Restart your stopped broker
5. Check again your topic

#### Using a Scala application with Kafka Streams to read and write to Kafka
* Scala https://docs.scala-lang.org/getting-started/index.html
* Scala build tool : https://www.scala-sbt.org/download.html
* https://kafka.apache.org/documentation/streams/
* https://github.com/polomarcus/Spark-Structured-Streaming-Examples

### Continuous Integration (CI)
If it works on your machine, congrats. Test it on a remote servers now thanks to a Continuous Integration (CI) system such as [GitHub Actions](https://github.com/features/actions) : 
* How to use containers inside a CI : https://docs.github.com/en/github-ae@latest/actions/using-containerized-services/about-service-containers
* A Github Action example : https://github.com/conduktor/kafka-stack-docker-compose/blob/master/.github/workflows/main.yml

####
# Tools
* Scala IDE : https://www.jetbrains.com/idea/
* Kafka User Interface (UI) : https://www.conduktor.io/download/