package com.github.polomarcus.utils

import com.github.polomarcus.model.News
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.{Dataset, SaveMode}

object PostgresService {
  val logger = Logger(PostgresService.getClass)
  // Save it to Postgres into the table called "news"
  val tableName = "news"
  // These information help you to connect to postgres
  val user = "user"
  val password = "password"
  val dbHost = sys.env.getOrElse("postgres", "localhost")
  logger.warn(s"Connecting to jdbc:postgresql://$dbHost:5432/metabase")
  val dbServer = s"$dbHost:5432/metabase"

  def save(dataset: Dataset[News]) = {
    logger.info(
      s"""
         |Saving news json inside Postgres database with this config
         |server: $dbServer
         |user : $user
         |password : $password
         |""".stripMargin)

    dataset.write
      ???

    logger.info("Saved news inside PG database")

  }
}
