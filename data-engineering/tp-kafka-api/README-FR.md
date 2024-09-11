## TP - [Apache Kafka](https://kafka.apache.org/)

### Problèmes de communication

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_complex.png)

### Pourquoi Kafka ?

![](https://content.linkedin.com/content/dam/engineering/en-us/blog/migrated/datapipeline_simple.png)

### Utilisation de Kafka avec Docker

Démarrez plusieurs serveurs Kafka (appelés brokers) en utilisant le fichier docker-compose.yml :

```bash
docker compose -f docker-compose.yml up --detach
```

Vérifiez sur Docker Hub l'image utilisée :

- https://hub.docker.com/r/confluentinc/cp-kafka

**Remarque** : Utilisez-vous un Mac M1 ? Vous devez changer le nom de votre image en `confluentinc/cp-kafka-connect:7.2.1.arm64` au lieu de `confluentinc/cp-kafka-connect:7.2.1` : https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui-arm64.yaml#L71

#### Vérifier les conteneurs Docker

```bash
docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS         PORTS                                                                                  NAMES
b015e1d06372   confluentinc/cp-kafka:7.1.3       "/etc/confluent/dock…"   10 seconds ago   Up 9 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9999->9999/tcp, :::9999->9999/tcp   kafka1
(...)
```

### Interface utilisateur de Kafka

Comme Kafka n'a pas d'interface, nous allons utiliser l'application web ["Kafka UI"](https://docs.kafka-ui.provectus.io/) grâce à Docker Compose.

### Kafka avec une interface utilisateur est mieux

Puisque Kafka n'a pas d'interface, nous allons utiliser l'application web ["Kafka UI"](https://docs.kafka-ui.provectus.io/) grâce à Docker Compose.

En utilisant Kafka UI sur http://localhost:8080/, connectez-vous à **votre cluster Docker Kafka existant** avec localhost:9092.

0. À l'aide de Kafka UI, créez un topic "mytopic" avec 5 partitions.
1. Trouvez le topic "mytopic" sur Kafka UI et ses différentes configurations (InSync Replica, Facteur de Réplication...).
2. Produisez 10 messages (sans clé) et lisez-les.
3. Observez sur quelles partitions du topic ils sont localisés.
4. Envoyez 10 autres messages mais avec une clé appelée "my key".
5. Regardez à nouveau sur quelles partitions du topic ils sont localisés.

Questions :

- [ ] Quand devrions-nous utiliser une clé lors de la production d'un message dans Kafka ? Quels sont les risques ? [Aide](https://stackoverflow.com/a/61912094/3535853)
- [ ] Comment fonctionne le partitionneur par défaut (sticky partition) avec Kafka ? [Aide1](https://www.confluent.io/fr-fr/blog/apache-kafka-producer-improvements-sticky-partitioner/) et [Aide2](<https://www.conduktor.io/kafka/producer-default-partitioner-and-sticky-partitioner#Sticky-Partitioner-(Kafka-%E2%89%A5-2.4)-3>)

### Coder notre propre client Kafka en utilisant Scala

Au lieu d'utiliser l'interface de ligne de commande (CLI) ou Kafka UI pour produire et consommer, nous allons coder notre première application comme des pros.

#### Producteur - le service en charge d'envoyer des messages

Nous allons remplacer tous les ??? et tous les //@TODO à l'intérieur des dossiers `src/scala/main/com.github.polomarcus/main` et `src/scala/main/com.github.polomarcus/utils`.

Tout d'abord, en utilisant `scala/com.github.polomarcus/utis/KafkaProducerService`, envoyez des messages à Kafka et **lisez-les avec Kafka UI** (Topics / Sélectionner le nom du topic / Messages).

Questions :

- Qu'est-ce que les sérialiseurs et désérialiseurs ? Lequel est utilisé ici ? Et pourquoi les utiliser ?

#### Pour exécuter votre programme avec SBT ou Docker

Il y a 3 façons d'exécuter votre programme :

```bash
sbt "runMain com.github.polomarcus.main.MainKafkaProducer"
# OU
sbt run
# et tapez "4" pour exécuter "com.github.polomarcus.main.MainKafkaProducer"

# OU
docker compose run my-scala-app bash
> sbt
> run
```

Après avoir exécuté avec votre shell sbt `runMain com.github.polomarcus.main.MainKafkaProducer`, vous devriez voir ce genre de log :

```declarative
INFO  c.g.p.utils.KafkaProducerService$ -
        Sending message with key "key20" and value "filter20"
```

##### Question 1

Votre équipe d'exploitation vous indique que votre application est lente et que le CPU n'est pas beaucoup utilisé, ils espéraient vous aider mais ils ne sont pas experts en Kafka.

- [ ] Regardez la méthode `producer.flush()` à l'intérieur de KafkaProducerService (Clic droit sur "tp-kafka-api" et "Find in files" pour la trouver), pouvez-vous améliorer la vitesse du programme ?
- [ ] Qu'en est-il du batching des messages ? [Aide](https://www.conduktor.io/kafka/kafka-producer-batching). Cela peut-il aider les performances de votre application ?

##### Question 2

Votre sympathique équipe d'exploitation vous avertit que les disques Kafka commencent à se remplir. Que pouvez-vous faire ?

Conseils :

- [ ] Qu'en est-il de la [compression des messages](https://kafka.apache.org/documentation/#producerconfigs_compression.type) ? Pouvez-vous la mettre en œuvre ? [Vous avez entendu dire que la compression snappy est excellente.](https://learn.conduktor.io/kafka/kafka-message-compression/)
- [ ] Quels sont les inconvénients de la compression ?
- [ ] Qu'en est-il de la [durée de vie des messages](https://kafka.apache.org/documentation/#topicconfigs_delete.retention.ms) sur vos brokers Kafka ? Pouvez-vous changer la configuration de votre topic ?
- [ ] Quels sont les inconvénients d'augmenter la durée de vie de vos messages ?

##### Question 3

Après un certain temps et de nombreux déploiements et autoscaling (ajout et suppression en raison des pics de trafic), sur votre tableau de bord de qualité des données, vous remarquez que certains messages sont dupliqués ou manquants. Que pouvez-vous faire ?

- [ ] Qu'est-ce que les ["acks"](https://kafka.apache.org/documentation/#producerconfigs_acks) ? Quand utiliser `acks=0` ? Quand utiliser `acks=all` ?
- [ ] L'[idempotence](https://kafka.apache.org/documentation/#producerconfigs_enable.idempotence) peut-elle nous aider ?
- [ ] Qu'est-ce que ["min.insync.replicas"](https://kafka.apache.org/documentation/#brokerconfigs_min.insync.replicas) ?

#### Consommateur - le service en charge de lire les messages

L'objectif est de lire les messages de notre producteur grâce à la [classe "KafkaConsumerService"](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/utils/KafkaConsumerService.scala#L34-L35).

Pour exécuter votre programme (si vous ne changez rien, il y aura une erreur d'implémentation manquante, **c'est normal**):

```bash
sbt "runMain com.github.polomarcus.main.MainKafkaConsumer"
> scala.NotImplementedError: an implementation is missing --> modifiez la classe `utils/KafkaConsumerService`
```

[Après avoir modifié le code ici](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/utils/KafkaConsumerService.scala#L34-L35) et lu le code de KafkaConsumerService, vous devriez voir des messages être lus sur votre terminal.

```declarative
INFO  c.g.p.utils.KafkaProducerService$ -  Lecture :
Offset : 20 de la partition 0
Valeur : value1
Clé : key1
(...)
INFO  c.g.p.utils.KafkaProducerService$ - Aucun nouveau message, en attente de 3 secondes
```

Ouvrez Kafka UI dans l'onglet "Consommateurs" et regardez quelles nouvelles informations vous pouvez obtenir (_indice : groupe de consommateurs_).

Maintenant, renvoyez des messages à Kafka grâce à `runMain com.github.polomarcus.main.MainKafkaProducer` et regardez le retard du groupe de consommateurs dans Kafka UI.

Que remarquons-nous ? Pouvons-nous changer une configuration pour ne pas lire à nouveau les mêmes données toujours et encore ? Modifiez-la dans notre KafkaConsumerService.

##### Question 1

- [ ] Que se passe-t-il si votre consommateur plante pendant le traitement des données ?
- Quelles sont les sémantiques "at most once" / "at least once" / "exactly once" ? [Aide](https://www.conduktor.io/kafka/complete-kafka-consumer-with-java#Automatic-Offset-Committing-Strategy-1)
- Que devrions-nous utiliser ?

##### Question

2
Nous avons introduit un bug dans notre programme et nous aimerions rejouer certaines données. Pouvons-nous utiliser Kafka UI pour aider notre groupe de consommateurs ? Devons-nous créer un nouveau groupe de consommateurs ?

- [ ] [Aide](https://kafka.apache.org/documentation.html#basic_ops_consumer_group)

#### Schema Registry - pour avoir plus de contrôle sur nos messages

##### Introduction

Regardez :

- votre docker-compose.yml, et le service schema-registry.
- Dans Kafka UI, vous pouvez vérifier l'onglet Schema registry, il devrait être vide : http://localhost:8080/ui/clusters/local/schemas

##### Questions

- [ ] Quels sont les avantages d'utiliser un registre de schémas pour les messages ? [Aide](https://docs.confluent.io/platform/current/schema-registry/index.html)
- [ ] Où sont stockées les informations des schémas ?
- [ ] Qu'est-ce que la sérialisation ? [Aide](https://developer.confluent.io/learn-kafka/kafka-streams/serialization/#serialization)
- [ ] Quels formats de sérialisation sont supportés ? [Aide](https://docs.confluent.io/platform/current/schema-registry/index.html#avro-json-and-protobuf-supported-formats-and-extensibility)
- [ ] Pourquoi le format Avro est-il si compact ? [Aide](https://docs.confluent.io/platform/current/schema-registry/index.html#ak-serializers-and-deserializers-background)
- [ ] Quelles sont les meilleures pratiques pour exécuter un registre de schémas en production ? [Aide1](https://docs.confluent.io/platform/current/schema-registry/index.html#sr-high-availability-single-primary) et [Aide2](https://docs.confluent.io/platform/current/schema-registry/installation/deployment.html#running-sr-in-production)

##### Code

[Comment créer un sérialiseur personnalisé ?](https://developer.confluent.io/learn-kafka/kafka-streams/serialization/#custom-serdes)

[Types de données Kafka Streams et sérialisation](https://docs.confluent.io/platform/current/streams/developer-guide/datatypes.html#avro)

1. Dans `KafkaAvroProducerService`, découvrez les "@TODO" et envoyez votre premier message en utilisant Avro et le Schema Registry avec `runMain com.github.polomarcus.main.MainKafkaAvroProducer`.
2. Dans `KafkaAvroConsumerService`, modifiez les 2 ??? et exécutez `runMain com.github.polomarcus.main.MainKafkaAvroConsumer`.

##### Évolution du schéma

3. Ajoutez une nouvelle propriété à la classe News dans le dossier "src/main/scala/.../models" appelée `test: String` : cela signifie une colonne "test" de type String.
4. Que se passe-t-il dans votre console lorsque vous envoyez à nouveau des messages avec `runMain com.github.polomarcus.main.MainKafkaAvroProducer` ? Pourquoi ?
5. Modifiez la classe News avec `test: Option[String] = None`.
6. Envoyez un autre message et dans l'onglet Schema Registry de Kafka UI, voyez ce qui se passe.

Nous avons vécu une [évolution de schéma](https://docs.confluent.io/platform/current/schema-registry/avro.html#schema-evolution).

#### Kafka Connect

> Kafka Connect est un composant open-source d'Apache Kafka® qui fonctionne comme un hub de données centralisé pour une intégration de données simple entre bases de données, magasins clé-valeur, index de recherche et systèmes de fichiers. [En savoir plus ici](https://docs.confluent.io/platform/current/connect/index.html)

![](https://images.ctfassets.net/gt6dp23g0g38/5vGOBwLiNaRedNyB0yaiIu/529a29a059d8971541309f7f57502dd2/ingest-data-upstream-systems.jpg)

Quelques vidéos qui peuvent être utiles :

- [Série de vidéos avec Confluent](https://developer.confluent.io/learn-kafka/kafka-connect/intro/)
- [Série de vidéos avec Conduktor](https://www.youtube.com/watch?v=4GSmIE9ji9c&list=PLYmXYyXCMsfMMhiKPw4k1FF7KWxOEajsA&index=25)

##### Où trouver des connecteurs ?

Des connecteurs déjà faits peuvent être trouvés sur [le Confluent Hub](https://www.confluent.io/hub/).

Un a déjà été installé via docker-compose.yml, pouvez-vous le repérer ?

Ce connecteur File Sink lit à partir d'un topic et l'écrit sous forme de fichier sur votre machine locale.

##### Comment démarrer un connecteur ?

À des fins de test, nous allons utiliser le [mode Standalone](https://docs.confluent.io/kafka-connectors/self-managed/userguide.html#standalone-mode) car nous n'avons pas de cluster.

Nous allons exécuter un [FileStream connector](https://docs.confluent.io/platform/current/connect/filestream_connector.html#kconnect-long-filestream-connectors) - un connecteur qui lit à partir d'un topic et l'écrit sous forme de fichier texte sur notre serveur.

Pour lire à partir d'un topic spécifique, nous devons configurer ce fichier [kafka-connect-configs/connect-file-sink-properties](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/kafka-connect-configs/connect-file-sink.properties).

Pour exécuter notre connecteur FileStream, nous pouvons procéder comme suit (regardez d'abord le fichier docker-compose.yml pour comprendre) :

```bash
docker compose run kafka-connect bash
> ls
> cat connect-file-sink.properties
> connect-standalone connect-standalone.properties connect-file-sink.properties
```

Félicitations, un fichier **devrait** avoir été produit sur votre **conteneur local** :

```bash
ls
cat test.sink.txt
```

**Comment pouvons-nous utiliser ce type de connecteur pour un usage en production ( = cas réels) ?**

- [ ] Pouvons-nous trouver un autre connecteur [sur le Confluent Hub](https://www.confluent.io/hub/) qui peut écrire dans **un data lake** au lieu d'un simple fichier texte sur l'un de nos serveurs ?

##### Comment fonctionnent les sérialiseurs pour Kafka Connect ?

Dans kafka-connect-config/connect-file-sink.properties, nous devons définir le sérialiseur que nous avons utilisé pour produire les données, pour notre cas, nous voulons utiliser le **String Serializer** dans notre configuration.

Conseils : [Kafka Connect Deep Dive – Converters and Serialization Explained](https://www.confluent.io/fr-fr/blog/kafka-connect-deep-dive-converters-serialization-explained/)

##### Comment fonctionnent les groupes de consommateurs pour Kafka Connect ?

Regardez sur Kafka UI pour voir si un connecteur utilise un groupe de consommateurs pour marquer les offsets des partitions.

#### Kafka Streams

[Introduction à Kafka Streams](https://kafka.apache.org/documentation/streams/)

[Guide du développeur Streams](https://docs.confluent.io/platform/current/streams/developer-guide/dsl-api.html#overview)

- [ ] Quelles sont les différences entre les APIs du consommateur, du producteur et Kafka Streams ? [Aide1](https://stackoverflow.com/a/44041420/3535853)
- [ ] Quand utiliser Kafka Streams au lieu de l'API consommateur ?
- [ ] Qu'est-ce qu'un SerDe ?
- [ ] Qu'est-ce qu'un KStream ?
- [ ] Qu'est-ce qu'une KTable ? Qu'est-ce qu'un topic compacté ?
- [ ] Qu'est-ce qu'une GlobalKTable ?
- [ ] Qu'est-ce qu'une [opération stateful](https://developer.confluent.io/learn-kafka/kafka-streams/stateful-operations/) ?

Quels sont les nouveaux [configs](https://kafka.apache.org/documentation/#streamsconfigs) que nous pouvons utiliser ?

##### Code

![MapReduce](http://coderscat.com/images/2020_09_10_understanding-map-reduce.org_20200918_164359.png)

Nous allons agréger (compter) combien de fois nous recevons le même mot dans le topic [ConfService.TOPIC_OUT](https://github.com/polomarcus/tp/blob/main/data-engineering/tp-kafka-api/src/main/scala/com/github/polomarcus/conf/ConfService.scala#L8). Pour cela, nous devons mapper chaque message pour séparer chaque mot.

Ensuite, nous allons filtrer les messages contenant "filter".

Ensuite, après avoir filtré ces messages, nous allons lire un autre topic pour le joindre à notre agrégat.

Dans `KafkaStreamsService`, écrivez le code pour effectuer :

- Écrivez une fonction de filtre qui supprime chaque message contenant le mot "filter".
- Écrivez une fonction qui joint une KTable (notre comptage de mots) avec un nouveau KStream qui lit un nouveau topic que vous devez créer.
  Cela devrait afficher dans la console la valeur de la KTable et du nouveau KStream basé sur la même clé. Si nous envoyons un message avec une clé "value10" et la valeur "active", il devrait retourner la clé : "value10" et la valeur (6, active), c'est-à-dire les valeurs jointes.

Pour exécuter le code, vous aurez besoin de cette commande :

```bash
sbt "runMain com.github.polomarcus.main.MainKafkaStream"
```

- [ ] Arrêtez votre application une fois que vous avez traité les

messages. Si vous redémarrez votre application et renvoyez des messages, faites attention à vos valeurs dans la KTable. [Comment l'état de la KTable est-il sauvegardé ?](https://docs.confluent.io/platform/current/streams/architecture.html#fault-tolerance)

Beaucoup d'exemples peuvent être trouvés [ici](https://blog.rockthejvm.com/kafka-streams/).

#### Monitoring et Opérations

##### Questions

- [ ] Quelles métriques devons-nous surveiller une fois notre application déployée dans le monde réel ? [Vue d'ensemble du tableau de bord Kafka de Datadog](https://www.datadoghq.com/dashboards/kafka-dashboard/)

Jetez un œil aux métriques Kafka UI : http://localhost:8080/ui/clusters/local/brokers/1/metrics

### Liens utiles

- https://sparkbyexamples.com/kafka/apache-kafka-consumer-producer-in-scala/
- https://www.confluent.io/fr-fr/blog/kafka-scala-tutorial-for-beginners/
- https://developer.confluent.io/learn-kafka/kafka-streams/get-started/
- [Hands-on Kafka Streams in Scala](https://softwaremill.com/hands-on-kafka-streams-in-scala/)
- [Scala, Avro Serde et Schema registry](https://univalence.io/blog/drafts/scala-avro-serde-et-schema-registry/)
- [Utilisation comme un Kafka Serde (lib kafka pour avro)](https://github.com/sksamuel/avro4s#usage-as-a-kafka-serde)
- [Exemple de Kafka Streams](https://blog.rockthejvm.com/kafka-streams/)
