package com.github.polomarcus.conf

object ConfService {
  val APPLICATION_NAME = "my-app"
  val BOOTSTRAP_SERVERS_CONFIG = sys.env.getOrElse("BOOTSTRAP_SERVERS", "localhost:9092")
  val SCHEMA_REGISTRY = sys.env.getOrElse("SCHEMA_REGISTRY", "http://localhost:8081")
  val GROUP_ID = "my-group"
  val TOPIC_OUT = "news"
}
