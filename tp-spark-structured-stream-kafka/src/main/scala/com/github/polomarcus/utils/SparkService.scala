package com.github.polomarcus.utils

import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.SparkSession

object SparkService {
  val logger = Logger(this.getClass)

  def getAndConfigureSparkSession() = {
    SparkSession
      .builder()
      .appName("TP Spark")
      .master("local[*]") // https://spark.apache.org/docs/3.2.1/submitting-applications.html#master-urls
      .getOrCreate()
  }
}
