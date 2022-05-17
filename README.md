# Practices - Data engineering

Have a stackoverflow account : https://stackoverflow.com/

Have a github account : https://github.com/

And a github repo to push your code.

## Fork the repo on your own Github account
* https://github.com/polomarcus/tp/fork

## TP2 - Functional programming for data engineering
You're the new data engineer of a scientific team in charge of monitoring CO2 levels in atmosphere, [which are at their highest in 800,000 years.](https://www.weforum.org/agenda/2018/05/earth-just-hit-a-terrifying-milestone-for-the-first-time-in-more-than-800-000-years).

You have to give your best estimate of CO2 levels for 2050. 

Your engineering team is famous for taking a great care of **the developer experience: using Type, small functions (using `.map`, `.filter`, `.reduce`), tests and logs.**

Your goal is to map, parse, filter CO2 concentration levels in the atmosphere coming from an observatory in Hawaii from 1950 to 2022.

CO2 concentration level has been inserted inside `utils/ClimateService`.

![](https://assets.weforum.org/editor/large_EEYnarb17Mwon7wYfBZ_V6gUQ3hwp6_tpzpPzAMVLRw.png)

### How to write a Scala application ?
* Install a Scala compatible IDE : Visual Studio with a Scala Plugin or Idea: https://www.jetbrains.com/idea/
* Scala https://docs.scala-lang.org/getting-started/index.html
* Another link if the first one does not work : https://www.scala-sbt.org/download.html

#### Could you install SBT on your machine ? If yes
```bash
sbt run
```

Should give you `an implementation is missing` error :
```bash
(...)
2022-05-16 18:33:48 [run-main-0] INFO  com.github.polomarcus.main.Main$ - Starting the app
[error] (run-main-0) scala.NotImplementedError: an implementation is missing
```

Same for `sbt test`

#### You couldn't install SBT on your machine

**Tips:** having trouble to install Idea, SBT or scala? You can use Docker and Docker Compose to run this code and use your default IDE to code or a web IDE https://scastie.scala-lang.org/:

```bash
docker-compose build my-scala-app
docker-compose run my-scala-app bash # connect to your container to acces to SBT
> sbt test
# or 
> sbt run
```

### Continuous build and test
**Pro Tips** : https://www.scala-sbt.org/1.x/docs/Running.html#Continuous+build+and+test

Make a command run when one or more source files change by prefixing the command with ~. For example, in sbt shell try:
```bash
sbt
> ~ testQuick
```

### Test Driven Development (TDD) - Write a function and its tests that detect climate related sentence
1. Look at and update "isClimateRelated" to add one more test `test/scala/ClimateService`
2. Look at and update "isClimateRelated" function inside `main/scala/com/github/polomarcus/utils/ClimateService`

### Write a function that use `Option[T]` to handle CO2 Record
With data coming from Hawaii about CO2 concentration in the atmosphere, iterate over it and find the difference between the max and the min value.

1. Look at and update "parseRawData" to add one more test `test/scala/ClimateService`
2. Look at and update "parseRawData" function inside `main/scala/com/github/polomarcus/utils/ClimateService`
3. Create your own function to find the min, max value. Write unit tests and run `sbt test`
Tips:
* Use scala API to get max and min from a list : https://www.w3resource.com/scala-exercises/list/scala-list-exercise-6.php
* You can also use "reduce functions" such as `foldLeft` : https://alvinalexander.com/scala/how-to-walk-scala-collections-reduceleft-foldright-cookbook/
4. Create your own function to find the min, max value for a specific year. Write unit tests
Tips: 
* Re use `getMinMax` to create this function :
5. Create your own function to difference between the max and the min. Write unit tests

Tips: 
* https://www.tutorialspoint.com/scala/scala_options.htm
* https://blog.engineering.publicissapient.fr/2012/03/19/les-types-monadiques-de-scala-le-type-option/

### Iteration - filter
1. Remove all data from december (12), *winter makes data unreliable there*, values with `filterDecemberData` inside `main/scala/com/github/polomarcus/utils/ClimateService`

### Iteration - map
1. implement `showCO2Data`  inside `main/scala/com/github/polomarcus/utils/ClimateService`
2. Make your Main program works using `sbt run`

### Bonus
Estimate CO2 levels for 2050 based on past data.

### How would you do if a continuous stream of data come ?
Tips: Batch processing / Stream processing ? 

### Continuous Integration (CI)
If it works on your machine, congrats !

Test it on a remote servers now thanks to a Continuous Integration (CI) system such as [GitHub Actions](https://github.com/features/actions) :

1. Have a look to the `.github/workflows` folder and files
2. Something weird ? Have a look to their documentation : https://github.com/features/actions
3. Ready to run a CI job ? Go on your Github's Fork/Clone of this and find the "Action" tab 
4. Find your CI job running
5. Create a CI workflows using Docker to run the `sbt test` command (inspiration : https://github.com/polomarcus/television-news-analyser/blob/main/.github/workflows/docker-compose.yml#L7-L17)

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

## TP1 - [Apache Kafka](https://kafka.apache.org/)
### Communication problems
![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)


### Why Kafka ?
https://kafka.apache.org/documentation/#introduction

Answer these questions with what you can find on the documentation :

- What problems does Kafka solve ?

- Which use cases ?

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

### Use kafka with docker
Start multiples kakfa servers (called brokers) by downloading a docker compose recipe : 
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
# will give you help to use this command
> kafka-topics --describe --bootstrap-server localhost:9092 
# will give you an error
```
Read this blog article to fix `Broker may not be available.` error : https://rmoff.net/2018/08/02/kafka-listeners-explained/

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
1. Increase replication in case one of your broker goes down : https://kafka.apache.org/documentation/#topicconfigs
2. Stop one of your brokers with docker
3. Describe your topic, check the ISR (in-sync replica) config : https://kafka.apache.org/documentation/#design_ha
4. Restart your stopped broker
5. Check again your topic


### TP 3 - Kafka Streams to read and write to Kafka
* https://kafka.apache.org/documentation/streams/
* https://github.com/polomarcus/Spark-Structured-Streaming-Examples
* Kafka User Interface (UI) : https://www.conduktor.io/download/

### Continuous Integration (CI)
If it works on your machine, congrats. Test it on a remote servers now thanks to a Continuous Integration (CI) system such as [GitHub Actions](https://github.com/features/actions) :
* How to use containers inside a CI : https://docs.github.com/en/github-ae@latest/actions/using-containerized-services/about-service-containers
* A Github Action example : https://github.com/conduktor/kafka-stack-docker-compose/blob/master/.github/workflows/main.yml