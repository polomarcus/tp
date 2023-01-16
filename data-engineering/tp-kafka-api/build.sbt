name := "TP Kafka Api"
organization := "com.github.polomarcus"

version := "1.0"
scalaVersion := "2.12.17"
mainClass / run := Some("com.github.polomarcus.main.Main")

val scalaTest = "3.2.12"
val kafkaVersion = "3.2.1"
val logback = "1.2.10"
val scalaLogging = "3.9.4"

resolvers           += "confluent" at "https://packages.confluent.io/maven/"

// Log
libraryDependencies += "ch.qos.logback" % "logback-classic" % logback
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.32"
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "7.0.1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging

// Test
libraryDependencies +=  "org.scalatest" %% "scalatest" % scalaTest % "test"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % kafkaVersion
libraryDependencies += "org.apache.kafka" % "kafka-streams" % kafkaVersion
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % kafkaVersion
libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "4.1.0"
libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-kafka" % "4.1.0"
libraryDependencies += "io.confluent" % "kafka-streams-avro-serde" % "7.2.1"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.6.1"
//Fix the weirdest bug ever, without this line we cannot get kafka-streams-scala
libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts( Artifact("javax.ws.rs-api", "jar", "jar"))
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"