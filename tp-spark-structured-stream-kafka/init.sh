#!/bin/bash
set -e
#### Description: Init kafka with messages

# Init values
container_name='tp-spark-structured-stream-kafka_kafka1_1'
destination='/home/appuser/'
topic='news'
file_name='news.txt'
number_messages="108"
kafka_server='kafka1:19092'

## Create stack
docker-compose -f docker-compose.yml up -d
sleep 3

echo -e "\nCopying some messages inside Kafka container"
docker cp $file_name $container_name:$destination
echo -e "\nSending messages to kafka"
echo 'kafka-console-producer --broker-list ' $kafka_server ' --topic '$topic
docker exec $container_name /bin/sh -c 'kafka-console-producer --broker-list '$kafka_server' --topic '$topic' < '$destination$file_name