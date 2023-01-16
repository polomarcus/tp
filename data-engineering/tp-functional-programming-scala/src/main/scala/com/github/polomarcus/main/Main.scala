package com.github.polomarcus.main

import com.typesafe.scalalogging.Logger
import com.github.polomarcus.utils.ClimateService

object Main {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    val sentence = "run sbt test to execute unit test"
    ClimateService.isClimateRelated(sentence)

    val co2Records = ClimateService.getCO2RawDataFromHawaii()
    val parsedCo2Records = ClimateService.parseRawData(co2Records)
    ClimateService.showCO2Data(parsedCo2Records)

    logger.info("Stopping the app")
    System.exit(0)
  }
}

