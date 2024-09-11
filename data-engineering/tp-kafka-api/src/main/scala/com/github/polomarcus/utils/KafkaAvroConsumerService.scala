package com.github.polomarcus.utils

import com.github.polomarcus.conf.ConfService
import com.github.polomarcus.models.News
import com.sksamuel.avro4s.{Record, RecordFormat}
import com.typesafe.scalalogging.Logger
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import java.time.Duration
import java.util.Properties
import scala.collection.JavaConverters._

object KafkaAvroConsumerService {
  val logger = Logger(KafkaAvroConsumerService.getClass)

  // Initialisation des propriétés Kafka pour le consommateur
  private val props = new Properties()

  // Configuration du bootstrap serveur
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfService.BOOTSTRAP_SERVERS_CONFIG)

  // Désérialiseur pour les clés (String)
  val stringDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializer)

  // Désérialiseur Avro pour les valeurs
  val kafkaAvroDeserializer = "io.confluent.kafka.serializers.KafkaAvroDeserializer"
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaAvroDeserializer)

  // Connexion au Schema Registry
  props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, ConfService.SCHEMA_REGISTRY)
  props.put(ConsumerConfig.GROUP_ID_CONFIG, ConfService.GROUP_ID)

  // Lire à partir du dernier offset disponible
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

  // Création du consommateur Kafka
  private val consumer = new KafkaConsumer[String, Record](props)

  // Souscription au topic Kafka
  val topicToRead = List(ConfService.TOPIC_OUT).asJava
  consumer.subscribe(topicToRead)

  // Fonction de consommation des messages
  def consume(): Unit = {
    try {
      for (i <- 0 to 20) { // Limiter la boucle pour éviter une consommation infinie
        val messages = consumer.poll(Duration.ofMillis(1000))
        val numberOfMessages = messages.count()

        if (numberOfMessages > 0) {
          logger.info(s"Lecture de $numberOfMessages messages...")
          messages.forEach { record =>
            // Désérialiser l'objet Avro en News
            val deserializedValue = RecordFormat[News].from(record.value())
            logger.info(
              s"""Message consommé :
                 |Offset : ${record.offset()} de la partition ${record.partition()}
                 |Valeur désérialisée : titre ${deserializedValue.title}, media ${deserializedValue.media}
                 |Clé : ${record.key()}
                 |""".stripMargin)
          }
        } else {
          logger.info("Aucun nouveau message, en attente de 3 secondes")
          Thread.sleep(3000)
        }
      }
    } catch {
      case e: Exception =>
        logger.error(e.toString)
    } finally {
      logger.warn("Fermeture du consommateur, sauvegarde des offsets du groupe")
      consumer.close()
    }
  }

  // Méthode pour fermer le consommateur Kafka proprement
  def close(): Unit = {
    logger.info("Fermeture du consommateur Kafka")
    consumer.close()
  }
}
