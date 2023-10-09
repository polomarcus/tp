# Data pipeline with Kafka and Elastic Search

A media specialist company would like to modernize its data stack.

Based on your Kafka expertise you want to create a Proof of Concept (PoC) using :
* Docker compose
* A Kafka broker
* A Kafka Connect Sink to Elastic 
* Elastic Search to store data
* Kibana to visualize

## 0. Why Kafka ?
What we can argue against a solution without Kafka that deals their data directly inside the same service (monolith) ?

Some friends are Ippon talks about [event-drivent architure.](https://blog.ippon.fr/2021/06/29/comment-se-lancer-avec-kafka-partie-1/)
 
## 1. But First ! Let's discover Elastic and Kibana 
Try Elastic and Kibana, by importing data from TV news that you can find here : https://github.com/polomarcus/television-news-analyser/blob/main/data-news-csv/year%3D2023
```bash

```
1. Inside the block "Get started by adding integrations" you can "Upload a file"
2. Click on "Analytics" and ["Discover"](http://localhost:5601/app/discover) to explore your data
3. "Take the tour" to discover Kibana features
4. Using the [Kibana Query Language](https://www.elastic.co/guide/en/kibana/current/kuery-query.html) "KQR" search all climate related news.
5. Create a visualization and a dashboard that shows a histogram of how many news talk about climate by Source (media) :
* Choose "Lense" here : http://localhost:5601/app/visualize

## 2. Elastic : you can direct query it without kibana
Once you uploaded data inside Elastic, you don't have to use Kibana anymore, but the Elastic Search API : either for your frontend or your backend.

Inside Kibana, create your first Elastic query with DevTools : http://localhost:5601/app/dev_tools#/console
On the index "news", we can to have any records that match the attribute "Title" containing "climat".

Once you get your query OK, click on the icon next to the play icon to obtain your "cURL" command.

```
curl -XGET "http://localhost:9200/_search" -H "kbn-xsrf: reporting" -H "Content-Type: application/json" -d'
...
```

Run your own command inside your terminal and get your results.

## 3. Draw your architecture with Kafka
Be specific on what you would need.

## 4. Use docker-compose.yml
