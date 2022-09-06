package com.github.polomarcus.main

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.models.News
import com.github.polomarcus.utils.KafkaAvroProducerService
import com.typesafe.scalalogging.Logger

object MainKafkaAvroProducer {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    for (i <- 0 to 20) {
      val news =  News(s"key $i", s"value $i")
      logger.info(s"Sending ${news.toString}")
      KafkaAvroProducerService.produce(ConfService.TOPIC_OUT, s"key$i", news)
    }

    logger.warn(s"Stopping the app ${this.getClass}")
    KafkaAvroProducerService.close()
    System.exit(0)
  }
}

