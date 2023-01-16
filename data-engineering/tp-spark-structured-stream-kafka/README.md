# Practices - Data engineering

## Getting Started

Start Kafka with Docker Compose and init it with news data thanks to :
```bash
cat init.sh

chmod 755 init.sh

./init.sh
```

**Important** : As we have a streaming application, we need to send data again, do it by running `init.sh` again.

## Apache Spark Structured Streaming with Apache Kafka
To process a large amount of data partitioned on a data lake, you can use data processing frameworks such as Apache Spark.

Spark allows us to perform batch or streaming query :
1. Discover Spark Structured Streaming : https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html

Some questions :
* What is the Spark Dataset API ?
* What is the micro-batch processing model ?
* What are the 3 different output modes of Spark Structured Streaming ?
* Which input sources or data sinks can Spark work with ? 

## How to read from / write to Kafka ?
Spark can read and write with Kafka : https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html
 
Some questions :
* How to configure the Spark consumer and producer ? [Help](https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html#kafka-specific-configurations)
* Can we use the Schema Registry with Spark ? [Help 1](https://learn.microsoft.com/en-us/azure/databricks/_static/notebooks/schema-registry-integration/avro-data-and-schema-registry.html)

## Code
The goal is to process JSON data coming from the Kafka topics "news".

To run the app `sbt run`

Once the Streaming app is running you can access to the monitoring console here : http://localhost:4040/

### Write as a parquet file locally
For data durability, you want to save your kafka's topic data to a data lake using the columnar format Parquet.

You'll look at this [chapter of the Spark documentation](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html#output-sinks) to know how to perform this operation and code it inside `KafkaService`

### Transformation
Inside `Main`, count the number of news by media, and display it using the ConsoleSink