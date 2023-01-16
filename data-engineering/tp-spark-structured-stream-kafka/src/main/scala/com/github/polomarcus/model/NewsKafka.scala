package com.github.polomarcus.model

import java.sql.Timestamp

case class NewsKafka(topic: String, partition: Int, offset: Long, timestamp: Timestamp, news: News)
