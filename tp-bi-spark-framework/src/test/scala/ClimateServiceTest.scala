import com.github.polomarcus.utils.ClimateService
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("isClimateRelated") {
    assert(!ClimateService.isClimateRelated("pizza"))
  }

  test("isClimateRelated - does not count aléas climatiques") {
    assert(!ClimateService.isClimateRelated("aléas climatiques"))
  }

  test("isClimateRelated - does not count just 'climat'") {
    assert(!ClimateService.isClimateRelated("aléas climatiques"))
  }

  test("réchauffement") {
    assert(ClimateService.isClimateRelated("réchauffement climatique"))
  }

  test("dérèglement") {
    assert(ClimateService.isClimateRelated("dérèglement climatique"))
  }

  test("changement") {
    assert(ClimateService.isClimateRelated("changement climatique"))
  }

  test("changements") {
    assert(ClimateService.isClimateRelated("changements climatiques"))
  }

  test("changements with capital letters") {
    assert(ClimateService.isClimateRelated("Les Changements Climatiques"))
  }

  test("with 'du climat'") {
    assert(ClimateService.isClimateRelated("dérèglement du climat"))
    assert(ClimateService.isClimateRelated("réchauffement du climat"))
    assert(ClimateService.isClimateRelated("changement du climat"))
  }

  test("enjeux climatiques") {
    assert(ClimateService.isClimateRelated("enjeux climatiques"))
  }

  test("volet climatique") {
    assert(ClimateService.isClimateRelated("volet climatique"))
    assert(ClimateService.isClimateRelated("volets climatiques"))
  }

  test("crise climatique") {
    assert(ClimateService.isClimateRelated("crise climatique"))
    assert(ClimateService.isClimateRelated("crises climatiques"))
  }

  test("GIEC") {
    assert(ClimateService.isClimateRelated("GIEC"))
    assert(ClimateService.isClimateRelated("giec"))
    assert(
      ClimateService.isClimateRelated(
        "On tiendra nos objectifs par rapport au rapport du Giec."))
  }

  test("Climat :") {
    assert(
      ClimateService.isClimateRelated(
        "Climat : pourquoi la France connaît-elle une sécheresse précoce ?"))
    assert(
      ClimateService.isClimateRelated(
        "Climat : des alternatives pour conserver son gazon sans impacter l'environnement"))
    assert(!ClimateService.isClimateRelated("climat"))
  }
}
