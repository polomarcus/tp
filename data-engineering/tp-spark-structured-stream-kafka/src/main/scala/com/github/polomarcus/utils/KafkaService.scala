package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.model.NewsKafka
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types._


object KafkaService  {
  private val spark = SparkService.getAndConfigureSparkSession()
  val logger = Logger(this.getClass)
  import spark.implicits._

  val schemaNews = new StructType()
    .add("title", StringType)
    .add("description", StringType)
    .add("media", StringType)
    .add("timestamp", TimestampType)

  /**
   * will return, we keep some kafka metadata for our example, otherwise we would only focus on "news" structure
   *  |-- key: binary (nullable = true)
   *  |-- value: binary (nullable = true)
   *  |-- topic: string (nullable = true) :
   *  |-- partition: integer (nullable = true) :
   *  |-- offset: long (nullable = true) :
   *  |-- timestamp: timestamp (nullable = true) :
   *  |-- timestampType: integer (nullable = true)
   *  |-- news: struct (nullable = true)
   *  |    |-- title: string (nullable = true)
   *  |    |-- description: string (nullable = true)
   *
   */
  def read(startingOption: String = "startingOffsets", partitionsAndOffsets: String = "earliest"): Dataset[NewsKafka] = {
    logger.warn(
      s"""
         |Reading from Kafka with these configs :
         |BOOTSTRAP_SERVERS_CONFIG : ${ConfService.BOOTSTRAP_SERVERS_CONFIG}
         |TOPIC_IN : ${ConfService.TOPIC_IN}
         |GROUP_ID : ${ConfService.GROUP_ID}
         |""".stripMargin)

    // This will read from our Kafka topic
    // We'll received Kafka's metadata (topic, partition, offsets, key and value)
    val dataframeStreaming = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", ConfService.BOOTSTRAP_SERVERS_CONFIG)
      .option("subscribe", ConfService.TOPIC_IN)
      .option("group.id", ConfService.GROUP_ID)
      .option(startingOption, partitionsAndOffsets) //this only applies when a new query is started and that resuming will always pick up from where the query left off
      .load()

    // We need to read our binary value to our class object News
    val newsDataset: Dataset[NewsKafka] = dataframeStreaming.withColumn("news", // nested structure with our json
        from_json($"value".cast(StringType), schemaNews) //From binary to JSON object
      ).as[NewsKafka]

    newsDataset.filter(_.news != null) //fitler bad data
  }

  /**
   *  -------------------------------------------
   *  Batch: 3
   *  -------------------------------------------
   *  +----+--------------------+-----+---------+------+--------------------+-------------+-------------+
   *  | key|               value|topic|partition|offset|           timestamp|timestampType|         news|
   *  +----+--------------------+-----+---------+------+--------------------+-------------+-------------+
   *  |null|[7B 22 74 69 74 6...| news|        2|    12|2022-10-09 16:57:...|            0|{test, oh my}|
   *  |null|[7B 22 74 69 74 6...| news|        2|    13|2022-10-09 16:57:...|            0|{test, oh my}|
   *  |null|[7B 22 74 69 74 6...| news|        2|    14|2022-10-09 16:57:...|            0|{test, oh my}|
   *  |null|[7B 22 74 69 74 6...| news|        2|    15|2022-10-09 16:57:...|            0|{test, oh my}|
   *  +----+--------------------+-----+---------+------+--------------------+-------------+-------------+
   *
   */
  def debugStream[T](ds: Dataset[T], completeOutputMode : Boolean = true) = {
    val outputMode = if(completeOutputMode) { OutputMode.Complete() } else { OutputMode.Append() }
    ds
      .writeStream
      .queryName(s"Debug Stream Kafka ${outputMode} ")
      .format("console")
      .outputMode(outputMode)
      .option("truncate", true)
      .start()
  }

  //@TODO Read "README.md" file and write
  //def writeToParquet(ds: Dataset[NewsKafka]) = {
  //???
  // }
}