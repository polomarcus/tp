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

  // Initialisation des propriétés Kafka pour le producteur
  private val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)

  // Utilisation d'un sérialiseur Avro pour les clés
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  // TODO: Utiliser le sérialiseur Avro pour les valeurs (objet News)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")

  // Connexion au Schema Registry
  props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, ConfService.SCHEMA_REGISTRY)

  // Activer l'idempotence pour éviter les doublons de messages
  props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")

  private val producer = new KafkaProducer[String, Record](props)

  // Fonction d'envoi d'un message en utilisant Avro et le Schema Registry
  def produce(topic: String, key: String, value: News): Unit = {
    // Sérialiser l'objet News en Avro
    val genericAvroRecord = RecordFormat[News].to(value)

    val record = new ProducerRecord(topic, key, genericAvroRecord)
    try {
      val metadata: Future[RecordMetadata] = producer.send(record)

      // Log des informations après l'envoi du message
      logger.info(s"""
        Envoi du message avec clé "$key" et valeur "${value.toString}"
        Offset : ${metadata.get().offset()}
        Partition : ${metadata.get().partition()}
      """)
    } catch {
      case e: Exception => logger.error(e.toString)
    }
  }

  // Fermer le producteur proprement
  def close() = {
    producer.flush()
    producer.close()
  }
}
