package com.github.polomarcus.main

import com.github.polomarcus.utils.KafkaStreamsService
import com.typesafe.scalalogging.Logger

object MainKafkaStream {
  def main(args: Array[String])  {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    KafkaStreamsService.startStream()
  }
}

