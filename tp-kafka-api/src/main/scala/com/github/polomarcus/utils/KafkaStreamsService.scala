package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

import java.util.Properties

/**
 * From https://kafka.apache.org/20/documentation/streams/developer-guide/dsl-api.html#scala-dsl
 */
object KafkaStreamsService {
  val logger = Logger(KafkaStreamsService.getClass)


  def startStream() = {
    import Serdes._
    val props: Properties = {
      val p = new Properties()
      p.put(StreamsConfig.APPLICATION_ID_CONFIG, ConfService.APPLICATION_NAME)
      p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)
      p
    }

    val builder: StreamsBuilder = new StreamsBuilder
    val textLinesStream: KStream[String, String] = builder.stream[String, String](ConfService.TOPIC_OUT)

    //@TODO apply a filter here before counting words

    val wordCounts: KTable[String, Long] = textLinesStream
      .flatMapValues(textLine => { // Stateless
        val transformed = textLine.toLowerCase.split("\\W+")

        logger.info(s"We received $textLine and we transformed it to $transformed")
        transformed
      }) //transform every message
      .groupBy( (_, word) => word) //stateful operation --> have a look at https://www.oreilly.com/library/view/apache-spark-2x/9781787126497/assets/71a44cfb-249a-478f-8e4e-e5b35e2c2a36.png
      .count()

    // Sink 1 to terminal console
    wordCounts.toStream.foreach( (key, value) => {
      logger.info(
        s"""
           |KTable wordCounts :
           |key $key - value $value
           |""".stripMargin)
    })
    // Sink 2 to another topic
    wordCounts.toStream.to(ConfService.TOPIC_KAFKA_STREAMS)


    //@TODO join a stream (The join operation is on the keys of the messages) from ConfService.TOPIC_KAFKA_STREAMS_WORD

    //@TODO display the joined stream using a foreach
    //logger.info(s"Key $key - value after joined $value")


    val topology = builder.build()
    val streams: KafkaStreams = new KafkaStreams(topology, props)

    logger.info(
      s"""
         |Stream started with this topology :
         |${topology.describe().toString}
         |----------------------------------------------------------------------------------------------------------------
         |Waiting for messages (send them with Conduktor or run sbt "runMain com.github.polomarcus.main.MainKafkaProducer")
         |----------------------------------------------------------------------------------------------------------------
         |""".stripMargin
    )
    streams.start()

    sys.ShutdownHookThread {
      logger.info("Stream closed")
      streams.close()
    }
  }
}