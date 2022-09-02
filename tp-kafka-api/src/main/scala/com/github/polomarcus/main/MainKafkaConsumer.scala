package com.github.polomarcus.main

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.utils.{KafkaConsumerService, KafkaProducerService}
import com.typesafe.scalalogging.Logger

object MainKafkaConsumer {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run MainKafkaConsumer` to start the app")

    KafkaConsumerService.consume()

    logger.warn(s"Stopping the app ${this.getClass}")

    System.exit(0)
  }
}

