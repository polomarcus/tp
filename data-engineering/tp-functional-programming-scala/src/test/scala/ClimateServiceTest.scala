import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }
  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("CO2"))
    assert(ClimateService.isClimateRelated("IPCC"))
    assert(ClimateService.isClimateRelated("Atmosphere"))
  }
  //@TODO
  test("parseRawData") {
    val firstRecord = (2003, 1, 355.2)
    //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val thirdRecord = (2004,12, -5.0)

    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val co2RecordWithType3 = None

    val list1 = List(firstRecord, secondRecord, thirdRecord)
    val list2 = List(firstRecord,thirdRecord)

    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2), None)
    val output2 = List(Some(co2RecordWithType), co2RecordWithType3)

    assert(ClimateService.parseRawData(list1) == output)
    assert(ClimateService.parseRawData(list2) == output2)
  }

  //@TODO
  test("filterDecemberData") {
    val list = List(
      Some(CO2Record(2003, 1, 355.2)),
      Some(CO2Record(2005, 12, 30.2)),
      Some(CO2Record(2004, 10, 35.2)),
      Some(CO2Record(1998, 12, 355.2)),
      None,
      Some(CO2Record(2008, 11, 355.2)),
    )
    val output = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2004, 10, 35.2),
      CO2Record(2008, 11, 355.2)
    )
    assert(ClimateService.filterDecemberData(list) == output)
  }

  test("getMinMax") {
    val list = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2005, 12, 30.2),
      CO2Record(2004, 10, 35.2),
    )
    val output = (30.2,355.2)
    assert(ClimateService.getMinMax(list) == output)
  }

  test("getMinMaxByYear") {
    val list = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2003, 12, 30.2),
      CO2Record(2009, 12, 390.2),
      CO2Record(2003, 10, 75.2),
      CO2Record(2007, 10, 205.2)
    )
    val output = (30.2,355.2)
    assert(ClimateService.getMinMaxByYear(list, 2003) == output)

  }
}

