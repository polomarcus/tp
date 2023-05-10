#!/bin/bash
set -e
#### Description: Init kafka with messages

# Init values
container_name='tp-docker-kafka-kafka1-1'
destination='/home/appuser/'
topic='lyrics'
file_name='lyrics.txt'
kafka_server='localhost:9092'

echo -e "\nCopying some messages inside Kafka container"
echo -e "docker cp $file_name $container_name:$destination"
docker cp $file_name $container_name:$destination

echo -e "\nSending messages from $file_name to kafka"
echo -e "Command Used :"
echo -e "docker exec $container_name /bin/sh -c  'kafka-console-producer --broker-list localhost:9092 --topic $topic < $destination$file_name'"
docker exec $container_name /bin/sh -c 'kafka-console-producer --broker-list '$kafka_server' --topic '$topic' < '$destination$file_name

echo -e "Got an error ? Retry it :)"