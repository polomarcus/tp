package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer._

import java.util.Properties

object KafkaProducerService {
  val logger = Logger(this.getClass)

  private val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)

  // Sérialisation des clés et valeurs
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  // Activer l'idempotence pour éviter les messages dupliqués (Question 3)
  props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")

  // Utiliser acks=all pour garantir que les messages sont bien répliqués (Question 3)
  props.put(ProducerConfig.ACKS_CONFIG, "all")

  // Configurer le batching
  props.put(ProducerConfig.LINGER_MS_CONFIG, "50")  // Ajouter un petit délai pour permettre le batching
  props.put(ProducerConfig.BATCH_SIZE_CONFIG, "32768")  // Définir la taille du batch à 32KB

  // Compression des messages en utilisant Snappy (Question 2)
  props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy")

  private val producer = new KafkaProducer[String, String](props)

  def produce(topic: String, key: String, value: String): Unit = {
    val record = new ProducerRecord(topic, key, value)

    try {
      producer.send(record)
      logger.info(s"""
        Sending message with key "$key" and value "$value"
      """)
    } catch {
      case e:Exception => logger.error(e.toString)
    }
  }

  def close() = {
    producer.close()
  }
}
