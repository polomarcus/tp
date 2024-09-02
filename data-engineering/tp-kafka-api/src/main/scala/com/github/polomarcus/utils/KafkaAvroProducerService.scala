package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.models.News
import com.sksamuel.avro4s.{Record, RecordFormat}
import com.typesafe.scalalogging.Logger
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import org.apache.kafka.clients.producer._

import java.util.Properties
import java.util.concurrent.Future

object KafkaAvroProducerService {
  val logger = Logger(KafkaAvroProducerService.getClass)

  private val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)

  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  // @TODO We want to serialize the value of a News object here : i.e. do a custom serialization (@see readme)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")

  // @TODO this is how we connect to the Schema Registry
  props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, ConfService.SCHEMA_REGISTRY)

  //@see https://kafka.apache.org/documentation/#producerconfigs_enable.idempotence
  props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")

  private val producer = new KafkaProducer[String, Record](props)

  def produce(topic: String, key: String, value: News): Unit = {

    // @TODO pay attention here
    val genericAvroRecord = RecordFormat[News].to(value) // @see https://softwaremill.com/hands-on-kafka-streams-in-scala/

    val record = new ProducerRecord(topic, key, genericAvroRecord)
    try {
      val metadata: Future[RecordMetadata] = producer.send(record)

      logger.info(s"""
        Sending message with key "$key" and value "${value.toString}"
        Offset : ${metadata.get().offset()}
        Partition : ${metadata.get().partition()}
      """)
    } catch {
      case e:Exception => logger.error(e.toString)
    }
  }

  def close() = {
    producer.flush()
    producer.close()
  }
}
