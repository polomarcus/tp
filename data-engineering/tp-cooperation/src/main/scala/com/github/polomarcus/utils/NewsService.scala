package com.github.polomarcus.utils

import com.github.polomarcus.model.News
import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.{col, lit, to_timestamp}

object NewsService {
  val logger = Logger(NewsService.getClass)

  val spark = SparkService.getAndConfigureSparkSession()
  import spark.implicits._

  /**
   * @see https://spark.apache.org/docs/latest/sql-data-sources-csv.html
   * @param path
   * @return
   */
  def read(path: String) = {
    spark.read
      .option("header", true)
      .csv(path)
      .withColumn("date", to_timestamp(col("date")))
      .withColumn("containsWordGlobalWarming", lit(false) ) //default value to false
      .as[News]
  }

  /**
   * Apply ClimateService.isClimateRelated function to see if a news is climate related
   * @param newsDataset
   * @return
   */
  def enrichNewsWithClimateMetadata(newsDataset: Dataset[News]) : Dataset[News] = {
    newsDataset.map { news =>
      val enrichedNews = News(
        news.title,
        news.date,
        news.url,
        news.media,
        containsWordGlobalWarming = false //@TODO you can change the logic here
      )

      enrichedNews
    }
  }

  /**
   * Only keep news about climate
   *
   * Tips --> https://alvinalexander.com/scala/how-to-use-filter-method-scala-collections-cookbook/
   *
   * @param newsDataset
   * @return newsDataset but with containsWordGlobalWarming to true
   */
  def filterNews(newsDataset: Dataset[News]) : Dataset[News] = {
    newsDataset.filter { news =>
      false //@TODO complete here
    }
  }

  /**
   * detect if a sentence is climate related by looking for these words in sentence :
   * global warming
   * IPCC
   * climate change
   * @param description "my awesome sentence contains a key word like climate change"
   * @return Boolean True
   */
  def getNumberOfNews(dataset: Dataset[News]): Long = {
    //@TODO look a the Spark API to know how to count
    return 1 // code here
  }
}
