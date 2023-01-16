import com.github.polomarcus.model.News
import com.github.polomarcus.utils.{ClimateService, NewsService, SparkService}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.scalatest.funsuite.AnyFunSuite

import java.sql.Timestamp

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class NewsServiceTest extends AnyFunSuite {
  val spark = SparkService.getAndConfigureSparkSession()
  import spark.implicits._

  val news = News(
    "My Title",
    "My description",
    new Timestamp(System.currentTimeMillis()),
    1,
    "Laurent Delahousse",
    List("C.Verove", "O.Sauvayre"),
    "Elsa Pallot",
    List("Sébastien Renout", "Anne Poncinet", "Arnaud Comte"),
    "http://localhost:8000/monde/bresil/test.html",
    "http://localhost:8000/replay.html",
    containsWordGlobalWarming = false,
    "France 2")

  val news2 = news.copy(title = "Another news")
  val newsClimate = news.copy(title = "Climat : pourquoi la France connaît-elle une sécheresse précoce ?", containsWordGlobalWarming = true)
  val fakeListNews = List(news, news2, newsClimate).toDS().as[News]

  test("getNumberOfNews") {
    assert(NewsService.getNumberOfNews(fakeListNews) == 3)
  }

  test("filterNews") {
    val input = NewsService.filterNews(fakeListNews).collect()
    val output = Array(newsClimate)

    assert( input.sameElements(output) )
  }

  test("enrichNewsWithClimateMetadata") {
    val newstoEnrich = news.copy(title = "Climat : pourquoi la France connaît-elle une sécheresse précoce ?")
    val listNews = List(news, newstoEnrich).toDS().as[News]

    val input = NewsService.enrichNewsWithClimateMetadata(listNews).collect()
    val output = Array(news, newstoEnrich.copy(containsWordGlobalWarming = true))

    assert(input.sameElements(output))
  }
}
