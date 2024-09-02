package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.models.News
import com.sksamuel.avro4s.{Record, RecordFormat}
import com.typesafe.scalalogging.Logger
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer._

import java.time.Duration
import java.util.Properties
import scala.collection.JavaConverters._

object KafkaAvroConsumerService {
  val logger = Logger(KafkaAvroConsumerService.getClass)

  private val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)

  val stringDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
  val kafkaAvroDeserializer = "io.confluent.kafka.serializers.KafkaAvroDeserializer"
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,stringDeserializer)

  // We want to serialize the value of a News object here : i.e. do a custom serialization (@see readme)
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaAvroDeserializer)
  props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, ConfService.SCHEMA_REGISTRY)
  props.put(ConsumerConfig.GROUP_ID_CONFIG, ConfService.GROUP_ID)
  val autoReset = ??? //@TODO @see https://kafka.apache.org/documentation/#consumerconfigs_auto.offset.reset
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoReset)

  private val consumer = new KafkaConsumer[String, Record](props)

  val topicToRead = List(ConfService.TOPIC_OUT).asJava
  consumer.subscribe(topicToRead)

  logger.info(
    s"""Our Consumer configuration :
       |Topics list : $topicToRead
       |SCHEMA_REGISTRY_URL_CONFIG: ${ConfService.SCHEMA_REGISTRY}
       |GROUP_ID: ${ConfService.GROUP_ID}
       |AUTO_OFFSET_RESET_CONFIG: $autoReset
       |KEY_DESERIALIZER_CLASS_CONFIG: $stringDeserializer
       |VALUE_DESERIALIZER_CLASS_CONFIG: $kafkaAvroDeserializer
       |""".stripMargin)


  def consume(): Unit = {
    try {
      for (i <- 0 to 20) { // to avoid a while(true) loop
        val messages = consumer.poll(Duration.ofMillis(1000))
        val numberOfMessages = messages.count()
        if (numberOfMessages > 0) {
          logger.info(s"Reading $numberOfMessages messages...")
          messages.forEach(record => {
            //@TODO how can we parse the raw data to a News object? @see producer for hints about RecordFormat
            val deserializedValue = ???
            // Deserialized Value [News]: title ${deserializedValue.title } media ${deserializedValue.media }
            logger.info(
              s"""Consumed :
                 |Offset : ${record.offset()} from partition ${record.partition()}
                 |Unserialized value (raw) : ${record.value()}
                 |Parsed value title : ${deserializedValue.title}
                 |Parsed value media : ${deserializedValue.media}
                 |Key : ${record.key()}
                 |""".stripMargin)
          })
        } else {
          logger.info("No new messages, waiting 3 seconds")
          Thread.sleep(3000)
        }
      }
    }
    catch {
      case e: Exception =>
        logger.error(e.toString)
    } finally {
      logger.warn("Closing our consumer, saving the consumer group offsets")
      consumer.close()
    }
  }

  def close() = {
    consumer.close()
  }
}
