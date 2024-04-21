import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("isClimateRelated - non climate related words should return false") {
    assert(!ClimateService.isClimateRelated("pizza"))
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change"))
    assert(ClimateService.isClimateRelated("IPCC"))
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2) //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  test("get_max_min_value") {
    val list_test_max_min = List(
      CO2Record(1961, 1, 317.27),
      CO2Record(1961, 2, 317.92),
      CO2Record(1961, 3, 318.63),
      CO2Record(1961, 4, 319.74),
      CO2Record(1961, 5, 320.33),
      CO2Record(1961, 6, 319.72),
      CO2Record(1961, 7, 318.32),
      CO2Record(1961, 8, 316.44),
      CO2Record(1961, 9, 314.91),
      CO2Record(1961, 10, 314.91),
      CO2Record(1961, 11, -99.99)
    )
    val (min, max) = ClimateService.getMinMax(list_test_max_min)
    assert(min == 314.91)
    assert(max == 320.33)
  }

  test("get_max_min_value_per_year") {
    val list_test_max_min_per_year = List(
      CO2Record(1961, 1, 317.27),
      CO2Record(1961, 2, 317.92),
      CO2Record(1961, 3, 318.63),
      CO2Record(1961, 4, 319.74),
      CO2Record(1962, 5, 320.33),
      CO2Record(1962, 6, 319.72),
      CO2Record(1962, 7, 318.32),
      CO2Record(1962, 8, 316.44),
      CO2Record(1962, 9, 314.91),
      CO2Record(1963, 10, 314.91),
      CO2Record(1963, 11, -99.99)
    )
    assert(ClimateService.getMinMaxByYear(list_test_max_min_per_year, 1961) == (317.27, 319.74))
    assert(ClimateService.getMinMaxByYear(list_test_max_min_per_year, 1962) == (314.91, 320.33))
    assert(ClimateService.getMinMaxByYear(list_test_max_min_per_year, 1963) == (314.91, 314.91)) // Since there is only one record for 1963
  }


  //@TODO
  test("filterDecemberData") {
    val recordsWithDecember = List(
      Some(CO2Record(2023, 12, 300.0)),
      Some(CO2Record(2024, 11, 305.0)),
      Some(CO2Record(2024, 12, 310.0)),
      Some(CO2Record(2025, 1, 315.0))
    )

    val filteredRecords = ClimateService.filterDecemberData(recordsWithDecember)

    // Check if there are no records with month 12 in the filtered list
    assert(filteredRecords.forall(_.month != 12))
  }

}
