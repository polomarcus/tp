package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.PartitionInfo
import scala.collection.JavaConverters._
import java.time.Duration
import java.util
import java.util.Properties

object KafkaConsumerService {
  val logger = Logger(KafkaProducerService.getClass)



  val props: Properties = new Properties()
  props.put("group.id", ConfService.GROUP_ID)
  props.put("bootstrap.servers", ConfService.BOOTSTRAP_SERVERS_CONFIG)
  props.put("key.deserializer",
    "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer",
    "org.apache.kafka.common.serialization.StringDeserializer")

  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest") // on the first execution, read from the beginning

  //@see https://docs.confluent.io/platform/current/clients/consumer.html#offset-management
  //Can we change a configuration to not read again the same data always and always ?
  props.put("enable.auto.commit", "false") // @TODO what are the risks to use this config ?
  props.put("auto.commit.interval.ms", "1000")

  val consumer = new KafkaConsumer[String, String](props)
  val topic = ConfService.TOPIC_OUT
  val topicToRead = List(topic).asJava

  //@TODO we need to connect our consumer to our topic (topicToRead) by **subscribing** it,
  //@TODO solution is here : https://www.oreilly.com/library/view/kafka-the-definitive/9781491936153/ch04.html#idm45788273579960
  ???
  
  def consume() = {
    try {
      val numberOfLoop = 20
      for (i <- 0 to numberOfLoop)  { // to avoid a while(true) loop
        val messages = consumer.poll(Duration.ofMillis(1000))
        if( messages.count() > 0) {
          messages.forEach(record => {
            logger.info(
              s""" Reading :
                 |Offset : ${record.offset()} from partition ${record.partition()}
                 |Value : ${record.value()}
                 |Key : ${record.key()}
                 |""".stripMargin)
          })
        } else {
          logger.info(s"No new messages, waiting 3 seconds - will shutdown in ${i}/${numberOfLoop}")
          Thread.sleep(3000)
        }
      }
    }
    catch {
      case e: Exception => logger.error(e.toString)
    } finally {
      logger.warn("Closing our consumer, saving the consumer group offsets")
      consumer.close()
    }
  }
}
