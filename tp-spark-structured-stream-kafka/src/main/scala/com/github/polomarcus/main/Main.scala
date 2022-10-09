package com.github.polomarcus.main

import com.github.polomarcus.model.NewsKafka
import com.github.polomarcus.utils.{KafkaService, SparkService}
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.Dataset

object Main {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    // This is our Spark starting point
    // Open file "src/main/scala/utils/SparkService.scala"
    // Read more about it here : https://spark.apache.org/docs/latest/sql-getting-started.html#starting-point-sparksession
    val spark = SparkService.getAndConfigureSparkSession()
    import spark.implicits._

    // To type our dataframe as News, we can use the Dataset API : https://spark.apache.org/docs/latest/sql-getting-started.html#creating-datasets
    val newsDatasets: Dataset[NewsKafka] = KafkaService.read()
    newsDatasets.printSchema()

    //Some examples of what we can do using the untyped or type APIs
    //// Select the devices which have signal more than 10
    //df.select("news.title").where("media == 'France 2'")      // using untyped APIs
    //ds.filter(_.media == "France 2").map(_.title)    // using typed APIs

    KafkaService.debugStream(newsDatasets, false)

    //@TODO call here your function from KafkaService
    ///KafkaService.writeToParquet(newsDatasets)

    //@TODO Count the number news we have from different media (Tips: use the typed API, groupBy $"news.media" and count() )
    //val counted = newsDatasets
    //???
    //KafkaService.debugStream(counted)

    //Wait for all streams to finish
    spark.streams.awaitAnyTermination()
  }
}

