package com.github.polomarcus.utils

import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.SparkSession

object SparkService {
  val logger = Logger(NewsService.getClass)

  def getAndConfigureSparkSession() = {
    SparkSession
      .builder()
      .appName("EPF - TelevisionNewsAnalyser")
      .master("local[*]") // https://spark.apache.org/docs/3.2.1/submitting-applications.html#master-urls
      .getOrCreate()
  }
}
