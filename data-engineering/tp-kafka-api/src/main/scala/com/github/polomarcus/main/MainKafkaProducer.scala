package com.github.polomarcus.main

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.utils.{KafkaProducerService}
import com.typesafe.scalalogging.Logger

object MainKafkaProducer {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    //@TODO go to ConfService to modify the TOPIC_OUT value by yours (Control + Click on the ConfService)
    for (i <- 0 to 20) {
      KafkaProducerService.produce(ConfService.TOPIC_OUT, s"key$i", s"value$i")
      KafkaProducerService.produce(ConfService.TOPIC_OUT, s"key$i", s"filter$i")
    }

    logger.warn(s"Stopping the app ${this.getClass}")
    KafkaProducerService.close()
    System.exit(0)
  }
}

