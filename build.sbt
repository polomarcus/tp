name := "TP EPF"
organization := "com.github.polomarcus"

version := "1.0"
scalaVersion := "2.12.15"

mainClass / run := Some("com.github.polomarcus.main.Main")

val scalaTest = "3.2.12"
val logback = "1.2.10"
val scalaLogging = "3.9.4"

// Log
libraryDependencies += "ch.qos.logback" % "logback-classic" % logback
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.32"
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "7.0.1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging

// Test
libraryDependencies +=  "org.scalatest" %% "scalatest" % scalaTest % "test"