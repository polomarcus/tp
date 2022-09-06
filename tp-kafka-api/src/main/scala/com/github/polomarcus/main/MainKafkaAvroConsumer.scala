package com.github.polomarcus.main

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.utils.KafkaAvroConsumerService
import com.typesafe.scalalogging.Logger

object MainKafkaAvroConsumer {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    KafkaAvroConsumerService.consume()

    logger.warn(s"Stopping the app ${this.getClass}")
    KafkaAvroConsumerService.close()
    System.exit(0)
  }
}

