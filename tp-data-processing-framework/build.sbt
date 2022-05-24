name := "TP EPF"
organization := "com.github.polomarcus"

version := "1.0"
scalaVersion := "2.12.15"
mainClass / run := Some("com.github.polomarcus.main.Main")

val scalaTest = "3.2.12"
val sparkVersion = "3.2.1"
val logback = "1.2.10"
val scalaLogging = "3.9.4"

// Log
libraryDependencies += "ch.qos.logback" % "logback-classic" % logback
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.32"
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "7.0.1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging

// Test
libraryDependencies +=  "org.scalatest" %% "scalatest" % scalaTest % "test"


//Spark (csv writer)
libraryDependencies += ("org.apache.spark" %% "spark-core" % sparkVersion)
  .exclude("log4j", "log4j")
  .exclude("org.slf4j", "slf4j-log4j12")
  .exclude("org.slf4j", "log4j")

libraryDependencies += ("org.apache.spark" %% "spark-sql" % sparkVersion)
  .exclude("org.slf4j", "log4j")
  .exclude("org.slf4j", "slf4j-log4j12")
  .exclude("org.slf4j", "log4j")

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.6.1"

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"