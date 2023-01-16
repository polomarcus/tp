package com.github.polomarcus.main

import com.github.polomarcus.model.News
import com.typesafe.scalalogging.Logger
import com.github.polomarcus.utils.{ClimateService, NewsService, SparkService}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Main {
  def main(args: Array[String]) {
    val logger = Logger(this.getClass)
    logger.info("Used `sbt run` to start the app")

    // This is our Spark starting point
    // Open file "src/main/scala/utils/SparkService.scala"
    // Read more about it here : https://spark.apache.org/docs/latest/sql-getting-started.html#starting-point-sparksession
    val spark = SparkService.getAndConfigureSparkSession()
    import spark.implicits._

    // Read a JSON data source with the path "./data-news-json"
    // Tips : https://spark.apache.org/docs/latest/sql-data-sources-json.html
    val pathToJsonData = "./data-news-json/"
    val newsDataframe: DataFrame = ???

    // To type our dataframe as News, we can use the Dataset API : https://spark.apache.org/docs/latest/sql-getting-started.html#creating-datasets
    val newsDatasets: Dataset[News] = NewsService.read(pathToJsonData)

    // print the dataset schema - tips : https://spark.apache.org/docs/latest/sql-getting-started.html#untyped-dataset-operations-aka-dataframe-operations
    //@TODO newsDatasets.???

    // Show the first 10 elements - tips : https://spark.apache.org/docs/latest/sql-getting-started.html#creating-dataframes
    //@TODO newsDatasets.???

    // Enrich the dataset by apply the ClimateService.isClimateRelated function to the title and the description of a news
    // a assign this value to the "containsWordGlobalWarming" attribute
    val enrichedDataset = NewsService.enrichNewsWithClimateMetadata(newsDatasets)

    // From now, we'll use only the Dataset API as it's more convenient
    val filteredNewsAboutClimate = NewsService.filterNews(enrichedDataset)
    // Count how many tv news we have in our data source
    val count = NewsService.getNumberOfNews(newsDatasets)
    logger.info(s"We have ${count} news in our dataset")


    // Show how many news we have talking about climate change compare to others news (not related climate)
    // Tips: use a groupBy


    // Use SQL to query a "news" table - look at : https://spark.apache.org/docs/latest/sql-getting-started.html#running-sql-queries-programmatically


    // Use strongly typed dataset to be sure to not introduce a typo to your SQL Query
    // Tips : https://stackoverflow.com/a/46514327/3535853


    // Save it as a columnar format with Parquet with a partition by date and media
    // Learn about Parquet : https://spark.apache.org/docs/3.2.1/sql-data-sources-parquet.html
    // Learn about partition : https://spark.apache.org/docs/3.2.1/sql-data-sources-load-save-functions.html#bucketing-sorting-and-partitioning

    logger.info("Stopping the app")
    System.exit(0)
  }
}

