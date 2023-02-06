package com.github.polomarcus.utils

import com.typesafe.scalalogging.Logger
import com.github.polomarcus.model.CO2Record

import scala.util.matching.Regex

object ClimateService {
  val logger = Logger(ClimateService.getClass)

  /**
   * detect if a sentence is climate related by looking for these words in sentence :
   * global warming
   * IPCC
   * climate change
   * @param description "my awesome sentence contains a key word like climate change"
   * @return Boolean True
   */
  def isClimateRelated(description: String): Boolean = ???

  /**
   * parse a list of raw data and transport it with type into a list of CO2Record
   * if the ppm value is valid (some ppm values are negative (CO2Record's "isValidPpmValue" function))
   * --> Some(value)
   * otherwise : None
   * you can access to Tuple with myTuple._1, myTuple._2, myTuple._3
   */
  def parseRawData(list: List[(Int, Int, Double)]) : List[Option[CO2Record]] = {
    list.map { record => ??? }
    ???
  }

  /**
   * remove all values from december (12) of every year
   *
   * @param list
   * @return a list
   */
  def filterDecemberData(list: List[Option[CO2Record]]) : List[CO2Record] = ???


  /**
   * **Tips**: look at the read me to find some tips for this function
   */
  def getMinMax(list: List[CO2Record]) : (Double, Double) = ???

  def getMinMaxByYear(list: List[CO2Record], year: Int) : (Double, Double) = ???

  /**
   * use this function side src/main/scala/com/polomarcus/main/Main (with sbt run)
   * display every item on the list using the CO2Record's "show" function
   *
   * Bonus: for quality check : count how many None values we have
   *
   * @param list
   */
  def showCO2Data(list: List[Option[CO2Record]]): Unit = {
    logger.info("Call ClimateService.filterDecemberData here")

    logger.info("Call record.show function here inside a map function")
  }

  /**
   * CO2 record from 1958 to 2022
   * Recorded at Mauna Loa Observatory, Hawaii
   * unit : ppm (part per million)
   * from: https://scrippsco2.ucsd.edu/data/atmospheric_co2/primary_mlo_co2_record.html
   * @return List of CO2 record
   */
  def getCO2RawDataFromHawaii() : List[(Int, Int, Double)] = {
    List(
      (1958, 3, 316.19),
      (1958, 4, 317.29),
      (1958, 5, 317.87),
      (1958, 6, -99.99),
      (1958, 7, 315.85),
      (1958, 8, 313.97),
      (1958, 9, 312.44),
      (1958, 10, -99.99),
      (1958, 11, 313.6),
      (1958, 12, 314.76),
      (1959, 1, 315.64),
      (1959, 2, 316.28),
      (1959, 3, 316.98),
      (1959, 4, 318.08),
      (1959, 5, 318.66),
      (1959, 6, 318.05),
      (1959, 7, 316.66),
      (1959, 8, 314.8),
      (1959, 9, 313.3),
      (1959, 10, 313.32),
      (1959, 11, 314.53),
      (1959, 12, 315.72),
      (1960, 1, 316.62),
      (1960, 2, 317.29),
      (1960, 3, 318.03),
      (1960, 4, 319.14),
      (1960, 5, 319.68),
      (1960, 6, 319.02),
      (1960, 7, 317.59),
      (1960, 8, 315.67),
      (1960, 9, 314.11),
      (1960, 10, 314.08),
      (1960, 11, -99.99),
      (1960, 12, 316.39),
      (1961, 1, 317.27),
      (1961, 2, 317.92),
      (1961, 3, 318.63),
      (1961, 4, 319.74),
      (1961, 5, 320.33),
      (1961, 6, 319.72),
      (1961, 7, 318.32),
      (1961, 8, 316.44),
      (1961, 9, 314.91),
      (1961, 10, 314.91),
      (1961, 11, -99.99),
      (1961, 12, 317.3),
      (1962, 1, 318.19),
      (1962, 2, 318.85),
      (1962, 3, 319.55),
      (1962, 4, 320.66),
      (1962, 5, 321.23),
      (1962, 6, 320.58),
      (1962, 7, 319.15),
      (1962, 8, 317.22),
      (1962, 9, 315.65),
      (1962, 10, 315.62),
      (1962, 11, 316.8),
      (1962, 12, 317.95),
      (1963, 1, 318.83),
      (1963, 2, 319.47),
      (1963, 3, 320.16),
      (1963, 4, 321.25),
      (1963, 5, 321.81),
      (1963, 6, 321.15),
      (1963, 7, 319.7),
      (1963, 8, 317.76),
      (1963, 9, 316.18),
      (1963, 10, 316.15),
      (1963, 11, 317.34),
      (1963, 12, 318.51),
      (1964, 1, 319.39),
      (1964, 2, 320.03),
    (1964, 3, 320.74),
    (1964, 4, 321.83),
    (1964, 5, 322.35)
    ,
    (1964, 6, 321.65)
    ,
    (1964, 7, 320.18)
    ,
    (1964, 8, 318.22)
    ,
    (1964, 9, 316.62)
    ,
    (1964, 10, 316.58)
    ,
    (1964, 11, 317.75)
    ,
    (1964, 12, 318.89)
    ,
    (1965, 1, 319.75)
    ,
    (1965, 2, 320.39)
    ,
    (1965, 3, 321.08)
    ,
    (1965, 4, 322.19)
    ,
    (1965, 5, 322.78)
    ,
    (1965, 6, 322.16)
    ,
    (1965, 7, 320.75)
    ,
    (1965, 8, 318.86)
    ,
    (1965, 9, 317.34)
    ,
    (1965, 10, 317.38)
    ,
    (1965, 11, 318.65)
    ,
    (1965, 12, 319.89)
    ,
    (1966, 1, 320.85)
    ,
    (1966, 2, 321.57)
    ,
    (1966, 3, 322.33)
    ,
    (1966, 4, 323.49)
    ,
    (1966, 5, 324.1)
    ,
    (1966, 6, 323.48)
    ,
    (1966, 7, 322.06)
    ,
    (1966, 8, 320.13)
    ,
    (1966, 9, 318.56)
    ,
    (1966, 10, 318.56)
    ,
    (1966, 11, 319.77)
    ,
    (1966, 12, 320.97)
    ,
    (1967, 1, 321.87)
    ,
    (1967, 2, 322.53)
    ,
    (1967, 3, 323.23)
    ,
    (1967, 4, 324.34)
    ,
    (1967, 5, -99.99)
    ,
    (1967, 6, 324.24)
    ,
    (1967, 7, 322.79)
    ,
    (1967, 8, 320.85)
    ,
    (1967, 9, 319.27)
    ,
    (1967, 10, 319.28)
    ,
    (1967, 11, 320.51)
    ,
    (1967, 12, 321.72)
    ,
    (1968, 1, 322.64)
    ,
    (1968, 2, 323.32)
    ,
    (1968, 3, 324.08)
    ,
    (1968, 4, 325.23)
    ,
    (1968, 5, 325.82)
    ,
    (1968, 6, 325.19)
    ,
    (1968, 7, 323.77)
    ,
    (1968, 8, 321.87)
    ,
    (1968, 9, 320.35)
    ,
    (1968, 10, 320.41)
    ,
    (1968, 11, 321.7)
    ,
    (1968, 12, 322.97)
    ,
    (1969, 1, -99.99)
    ,
    (1969, 2, 324.7)
    ,
    (1969, 3, 325.49)
    ,
    (1969, 4, 326.69)
    ,
    (1969, 5, 327.35)
    ,
    (1969, 6, 326.75)
    ,
    (1969, 7, 325.35)
    ,
    (1969, 8, 323.44)
    ,
    (1969, 9, 321.88)
    ,
    (1969, 10, 321.9)
    ,
    (1969, 11, 323.15)
    ,
    (1969, 12, 324.38)
    ,
    (1970, 1, -99.99)
    ,
    (1970, 2, 326.0)
    ,
    (1970, 3, 326.73)
    ,
    (1970, 4, 327.88)
    ,
    (1970, 5, 328.47)
    ,
    (1970, 6, 327.82)
    ,
    (1970, 7, 326.36)
    ,
    (1970, 8, 324.4)
    ,
    (1970, 9, 322.8)
    ,
    (1970, 10, 322.78)
    ,
    (1970, 11, 324.2),
    (1970, 12,325.18),
    (1971, 1, 326.07)
    ,
    (1971, 2, 326.72)
    ,
    (1971, 3, 327.42)
    ,
    (1971, 4, 328.54)
    ,
    (1971, 5, -99.99)
    ,
    (1971, 6, 328.46)
    ,
    (1971, 7, 326.99)
    ,
    (1971, 8, 325.03)
    ,
    (1971, 9, 323.43)
    ,
    (1971, 10, 323.43)
    ,
    (1971, 11, 324.68)
    ,
    (1971, 12, 325.91)
    ,
    (1972, 1, 326.85)
    ,
    (1972, 2, 327.56)
    ,
    (1972, 3, 328.34)
    ,
    (1972, 4, 329.53)
    ,
    (1972, 5, 330.16)
    ,
    (1972, 6, 329.56)
    ,
    (1972, 7, 328.18)
    ,
    (1972, 8, 326.32)
    ,
    (1972, 9, 324.85)
    ,
    (1972, 10, 324.99)
    ,
    (1972, 11, 326.37)
    ,
    (1972, 12, 327.72)
    ,
    (1973, 1, 328.78)
    ,
    (1973, 2, 329.61)
    ,
    (1973, 3, 330.46)
    ,
    (1973, 4, 331.72)
    ,
    (1973, 5, 332.42)
    ,
    (1973, 6, 331.85)
    ,
    (1973, 7, 330.44)
    ,
    (1973, 8, 328.5)
    ,
    (1973, 9, 326.9)
    ,
    (1973, 10, 326.88)
    ,
    (1973, 11, 328.1)
    ,
    (1973, 12, 329.28)
    ,
    (1974, 1, 330.16)
    ,
    (1974, 2, 330.81)
    ,
    (1974, 3, 331.5)
    ,
    (1974, 4, 332.62)
    ,
    (1974, 5, 333.18)
    ,
    (1974, 6, 332.49)
    ,
    (1974, 7, 330.99)
    ,
    (1974, 8, 328.98)
    ,
    (1974, 9, 327.35)
    ,
    (1974, 10, 327.33)
    ,
    (1974, 11, 328.57)
    ,
    (1974, 12, 329.8)
    ,
    (1975, 1, 330.73)
    ,
    (1975, 2, 331.43)
    ,
    (1975, 3, 332.18)
    ,
    (1975, 4, 333.36)
    ,
    (1975, 5, 333.98)
    ,
    (1975, 6, 333.34)
    ,
    (1975, 7, 331.88)
    ,
    (1975, 8, 329.91)
    ,
    (1975, 9, 328.32)
    ,
    (1975, 10, 328.33)
    ,
    (1975, 11, 329.6)
    ,
    (1975, 12, 330.85)
    ,
    (1976, 1, 331.79)
    ,
    (1976, 2, 332.49)
    ,
    (1976, 3, 333.27)
    ,
    (1976, 4, 334.43)
    ,
    (1976, 5, 335.01)
    ,
    (1976, 6, 334.33)
    ,
    (1976, 7, 332.85)
    ,
    (1976, 8, 330.88)
    ,
    (1976, 9, 329.3)
    ,
    (1976, 10, 329.36)
    ,
    (1976, 11, 330.68)
    ,
    (1976, 12, 331.98)
    ,
    (1977, 1, 333.01)
    ,
    (1977, 2, 333.81)
    ,
    (1977, 3, 334.64)
    ,
    (1977, 4, 335.92)
    ,
    (1977, 5, 336.63)
    ,
    (1977, 6, 336.07)
    ,
    (1977, 7, 334.67)
    ,
    (1977, 8, 332.76)
    ,
    (1977, 9, 331.22)
    ,
    (1977, 10, 331.29)
    ,
    (1977, 11, 332.63)
    ,
    (1977, 12, 333.94)
    ,
    (1978, 1, 334.95)
    ,
    (1978, 2, 335.71)
    ,
    (1978, 3, 336.51)
    ,
    (1978, 4, 337.73)
    ,
    (1978, 5, 338.37)
    ,
    (1978, 6, 337.75)
    ,
    (1978, 7, 336.28)
    ,
    (1978, 8, 334.3)
    ,
    (1978, 9, 332.7)
    ,
    (1978, 10, 332.72)
    ,
    (1978, 11, 334.01)
    ,
    (1978, 12, 335.29)
    ,
    (1979, 1, 336.27)
    ,
    (1979, 2, 337.01)
    ,
    (1979, 3, 337.8)
    ,
    (1979, 4, 339.02)
    ,
    (1979, 5, 339.68)
    ,
    (1979, 6, 339.07)
    ,
    (1979, 7, 337.63)
    ,
    (1979, 8, 335.68)
    ,
    (1979, 9, 334.12)
    ,
    (1979, 10, 334.19)
    ,
    (1979, 11, 335.55)
    ,
    (1979, 12, 336.89)
    ,
    (1980, 1, 337.93)
    ,
    (1980, 2, 338.72)
    ,
    (1980, 3, 339.59)
    ,
    (1980, 4, 340.85)
    ,
    (1980, 5, 341.51)
    ,
    (1980, 6, 340.88)
    ,
    (1980, 7, 339.43)
    ,
    (1980, 8, 337.47)
    ,
    (1980, 9, 335.9)
    ,
    (1980, 10, 335.95)
    ,
    (1980, 11, 337.26)
    ,
    (1980, 12, 338.55)
    ,
    (1981, 1, 339.53)
    ,
    (1981, 2, 340.26)
    ,
    (1981, 3, 341.03)
    ,
    (1981, 4, 342.23)
    ,
    (1981, 5, 342.85)
    ,
    (1981, 6, 342.18)
    ,
    (1981, 7, 340.68)
    ,
    (1981, 8, 338.67)
    ,
    (1981, 9, 337.04)
    ,
    (1981, 10, 337.07)
    ,
    (1981, 11, 338.39)
    ,
    (1981, 12, 339.69)
    ,
    (1982, 1, 340.68)
    ,
    (1982, 2, 341.42)
    ,
    (1982, 3, 342.19)
    ,
    (1982, 4, 343.39)
    ,
    (1982, 5, 344.01)
    ,
    (1982, 6, 343.34)
    ,
    (1982, 7, 341.82)
    ,
    (1982, 8, 339.79)
    ,
    (1982, 9, -99.99)
    ,
    (1982, 10, 338.17)
    ,
    (1982, 11, 339.5)
    ,
    (1982, 12, 340.83)
    ,
    (1983, 1, 341.86)
    ,
    (1983, 2, 342.67)
    ,
    (1983, 3, 343.52)
    ,
    (1983, 4, 344.83)
    ,
    (1983, 5, 345.56)
    ,
    (1983, 6, 344.99)
    ,
    (1983, 7, -99.99)
    ,
    (1983, 8, 341.63)
    ,
    (1983, 9, 340.07)
    ,
    (1983, 10, 340.14)
    ,
    (1983, 11, 341.51)
    ,
    (1983, 12, 342.85)
    ,
    (1984, 1, 343.88)
    ,
    (1984, 2, 344.66)
    ,
    (1984, 3, 345.5)
    ,
    (1984, 4, 346.74)
    ,
    (1984, 5, 347.38)
    ,
    (1984, 6, 346.72)
    ,
    (1984, 7, 345.23)
    ,
    (1984, 8, 343.22)
    ,
    (1984, 9, 341.62)
    ,
    (1984, 10, 341.67)
    ,
    (1984, 11, 343.02)
    ,
    (1984, 12, 344.33)
    ,
    (1985, 1, 345.35)
    ,
    (1985, 2, 346.11)
    ,
    (1985, 3, 346.91)
    ,
    (1985, 4, 348.14)
    ,
    (1985, 5, 348.79)
    ,
    (1985, 6, 348.12)
    ,
    (1985, 7, 346.6)
    ,
    (1985, 8, 344.56)
    ,
    (1985, 9, 342.91)
    ,
    (1985, 10, 342.91)
    ,
    (1985, 11, 344.23)
    ,
    (1985, 12, 345.52)
    ,
    (1986, 1, 346.52)
    ,
    (1986, 2, 347.27)
    ,
    (1986, 3, 348.07)
    ,
    (1986, 4, 349.31)
    ,
    (1986, 5, 349.98)
    ,
    (1986, 6, 349.35)
    ,
    (1986, 7, 347.86)
    ,
    (1986, 8, 345.86)
    ,
    (1986, 9, .24)
    ,
    (1986, 10, 344.29)
    ,
    (1986, 11, 345.65)
    ,
    (1986, 12, 346.99)
    ,
    (1987, 1, -99.99)
    ,
    (1987, 2, 348.83)
    ,
    (1987, 3, 349.67)
    ,
    (1987, 4, 350.97)
    ,
    (1987, 5, 351.69)
    ,
    (1987, 6, 351.11)
    ,
    (1987, 7, 349.68)
    ,
    (1987, 8, 347.73)
    ,
    (1987, 9, 346.17)
    ,
    (1987, 10, 346.29)
    ,
    (1987, 11, 347.72)
    ,
    (1987, 12, 349.14)
    ,
    (1988, 1, 350.24)
    ,
    (1988, 2, 351.1)
    ,
    (1988, 3, 352.01)
    ,
    (1988, 4, 353.34)
    ,
    (1988, 5, 354.05)
    ,
    (1988, 6, 353.45)
    ,
    (1988, 7, 352)
    ,
    (1988, 8, 350.02)
    ,
    (1988, 9, 348.43)
    ,
    (1988, 10, 348.51)
    ,
    (1988, 11, 349.89)
    ,
    (1988, 12, 351.22)
    ,
    (1989, 1, 352.24)
    ,
    (1989, 2, 353)
    ,
    (1989, 3, 353.8)
    ,
    (1989, 4, 355.03)
    ,
    (1989, 5, 355.68)
    ,
    (1989, 6, 355.01)
    ,
    (1989, 7, 353.49)
    ,
    (1989, 8, 351.43)
    ,
    (1989, 9, 349.76)
    ,
    (1989, 10, 349.78)
    ,
    (1989, 11, 351.13)
    ,
    (1989, 12, 352.44)
    ,
    (1990, 1, 353.45)
    ,
    (1990, 2, 354.2)
    ,
    (1990, 3, 355)
    ,
    (1990, 4, -99.99)
    ,
    (1990, 5, 356.89)
    ,
    (1990, 6, 356.23)
    ,
    (1990, 7, 354.72)
    ,
    (1990, 8, 352.69)
    ,
    (1990, 9, 351.06)
    ,
    (1990, 10, 351.13)
    ,
    (1990, 11, 352.52)
    ,
    (1990, 12, 353.89)
    ,
    (1991, 1, 354.95)
    ,
    (1991, 2, 355.74)
    ,
    (1991, 3, 356.56)
    ,
    (1991, 4, 357.81)
    ,
    (1991, 5, 358.46)
    ,
    (1991, 6, 357.76)
    ,
    (1991, 7, 356.19)
    ,
    (1991, 8, 354.08)
    ,
    (1991, 9, 352.36)
    ,
    (1991, 10, 352.35)
    ,
    (1991, 11, 353.66)
    ,
    (1991, 12, 354.95)
    ,
    (1992, 1, 355.93)
    ,
    (1992, 2, 356.65)
    ,
    (1992, 3, 357.45)
    ,
    (1992, 4, 358.65)
    ,
    (1992, 5, 359.24)
    ,
    (1992, 6, 358.48)
    ,
    (1992, 7, 356.87)
    ,
    (1992, 8, 354.73)
    ,
    (1992, 9, 353)
    ,
    (1992, 10, 352.97)
    ,
    (1992, 11, 354.26)
    ,
    (1992, 12, 355.53)
    ,
    (1993, 1, 356.49)
    ,
    (1993, 2, 357.21)
    ,
    (1993, 3, 357.98)
    ,
    (1993, 4, 359.2)
    ,
    (1993, 5, 359.84)
    ,
    (1993, 6, 359.15)
    ,
    (1993, 7, 357.61)
    ,
    (1993, 8, 355.54)
    ,
    (1993, 9, 353.88)
    ,
    (1993, 10, 353.94)
    ,
    (1993, 11, 355.35)
    ,
    (1993, 12, 356.74)
    ,
    (1994, 1, 357.83)
    ,
    (1994, 2, 358.66)
    ,
    (1994, 3, 359.52)
    ,
    (1994, 4, 360.84)
    ,
    (1994, 5, 361.56)
    ,
    (1994, 6, 360.95)
    ,
    (1994, 7, 359.46)
    ,
    (1994, 8, 357.45)
    ,
    (1994, 9, 355.84)
    ,
    (1994, 10, 355.94)
    ,
    (1994, 11, 357.38)
    ,
    (1994, 12, 358.8)
    ,
    (1995, 1, 359.9)
    ,
    (1995, 2, 360.74)
    ,
    (1995, 3, 361.62)
    ,
    (1995, 4, 362.94)
    ,
    (1995, 5, 363.67)
    ,
    (1995, 6, 363.05)
    ,
    (1995, 7, -99.99)
    ,
    (1995, 8, 359.52)
    ,
    (1995, 9, 357.89)
    ,
    (1995, 10, 357.97)
    ,
    (1995, 11, 359.4)
    ,
    (1995, 12, 360.79)
    ,
    (1996, 1, 361.87)
    ,
    (1996, 2, 362.67)
    ,
    (1996, 3, 363.55)
    ,
    (1996, 4, 364.83)
    ,
    (1996, 5, 365.48)
    ,
    (1996, 6, 364.78)
    ,
    (1996, 7, 363.21)
    ,
    (1996, 8, 361.11)
    ,
    (1996, 9, 359.41)
    ,
    (1996, 10, 359.43)
    ,
    (1996, 11, 360.77)
    ,
    (1996, 12, 362.09)
    ,
    (1997, 1, -99.99)
    ,
    (1997, 2, 363.85)
    ,
    (1997, 3, -99.99)
    ,
    (1997, 4, 365.92)
    ,
    (1997, 5, 366.6)
    ,
    (1997, 6, 365.94)
    ,
    (1997, 7, 364.43)
    ,
    (1997, 8, 362.4)
    ,
    (1997, 9, 360.79)
    ,
    (1997, 10, 360.93)
    ,
    (1997, 11, 362.45)
    ,
    (1997, 12, 363.95)
    ,
    (1998, 1, 365.14)
    ,
    (1998, 2, 366.08)
    ,
    (1998, 3, 367.05)
    ,
    (1998, 4, 368.49)
    ,
    (1998, 5, -99.32)
    ,
    (1998, 6, 368.79)
    ,
    (1998, 7, 367.36)
    ,
    (1998, 8, 365.38)
    ,
    (1998, 9, 363.79)
    ,
    (1998, 10, 363.9)
    ,
    (1998, 11, 365.35)
    ,
    (1998, 12, 366.76)
    ,
    (1999, 1, 367.82)
    ,
    (1999, 2, 368.6)
    ,
    (1999, 3, 369.41)
    ,
    (1999, 4, 370.66)
    ,
    (1999, 5, 371.29)
    ,
    (1999, 6, 370.57)
    ,
    (1999, 7, 368.96)
    ,
    (1999, 8, 366.81)
    ,
    (1999, 9, 365.07)
    ,
    (1999, 10, 365.07)
    ,
    (1999, 11, 366.43)
    ,
    (1999, 12, 367.78)
    ,
    (2000, 1, 368.8)
    ,
    (2000, 2, 369.56)
    ,
    (2000, 3, 370.41)
    ,
    (2000, 4, 371.69)
    ,
    (2000, 5, 372.34)
    ,
    (2000, 6, 371.66)
    ,
    (2000, 7, 370.1)
    ,
    (2000, 8, 368.02)
    ,
    (2000, 9, 366.37)
    ,
    (2000, 10, 366.44)
    ,
    (2000, 11, 367.87)
    ,
    (2000, 12, 369.25)
    ,
    (2001, 1, 370.31)
    ,
    (2001, 2, 371.11)
    ,
    (2001, 3, 371.94)
    ,
    (2001, 4, 373.23)
    ,
    (2001, 5, 373.92)
    ,
    (2001, 6, 373.25)
    ,
    (2001, 7, 371.7)
    ,
    (2001, 8, 369.61)
    ,
    (2001, 9, 367.94)
    ,
    (2001, 10, 368.01)
    ,
    (2001, 11, 369.46)
    ,
    (2001, 12, 370.89)
    ,
    (2002, 1, 372)
    ,
    (2002, 2, 372.4)
    ,
    (2002, 3, 373.73)
    ,
    (2002, 4, 375.1)
    ,
    (2002, 5, 375.86)
    ,
    (2002, 6, 375.27)
    ,
    (2002, 7, 373.79)
    ,
    (2002, 8, 371.78)
    ,
    (2002, 9, 370.18)
    ,
    (2002, 10, 370.33)
    ,
    (2002, 11, 371.85)
    ,
    (2002, 12, 373.34)
    ,
    (2003, 1, 374.51)
    ,
    (2003, 2, 375.4)
    ,
    (2003, 3, 376.33)
    ,
    (2003, 4, 377.72)
    ,
    (2003, 5, 378.49)
    ,
    (2003, 6, 377.9)
    ,
    (2003, 7, 376.39)
    ,
    (2003, 8, 374.34)
    ,
    (2003, 9, 372.68)
    ,
    (2003, 10, 372.77)
    ,
    (2003, 11, 374.22)
    ,
    (2003, 12, 375.64)
    ,
    (2004, 1, 376.72)
    ,
    (2004, 2, 377.53)
    ,
    (2004, 3, 378.41)
    ,
    (2004, 4, 379.71)
    ,
    (2004, 5, 380.37)
    ,
    (2004, 6, 379.66)
    ,
    (2004, 7, 378.08)
    ,
    (2004, 8, 375.96)
    ,
    (2004, 9, 374.29)
    ,
    (2004, 10, 374.39)
    ,
    (2004, 11, 375.87)
    ,
    (2004, 12, 377.33)
    ,
    (2005, 1, 378.48)
    ,
    (2005, 2, 379.37)
    ,
    (2005, 3, 380.3)
    ,
    (2005, 4, 381.71)
    ,
    (2005, 5, 382.5)
    ,
    (2005, 6, 381.91)
    ,
    (2005, 7, 380.42)
    ,
    (2005, 8, 378.38)
    ,
    (2005, 9, 376.74)
    ,
    (2005, 10, 376.85)
    ,
    (2005, 11, 378.36)
    ,
    (2005, 12, 379.82)
    ,
    (2006, 1, 380.95)
    ,
    (2006, 2, 381.81)
    ,
    (2006, 3, 382.69)
    ,
    (2006, 4, 384.04)
    ,
    (2006, 5, 384.76)
    ,
    (2006, 6, 384.1)
    ,
    (2006, 7, 382.53)
    ,
    (2006, 8, 380.41)
    ,
    (2006, 9, 378.7)
    ,
    (2006, 10, 378.77)
    ,
    (2006, 11, 380.22)
    ,
    (2006, 12, 381.65)
    ,
    (2007, 1, 382.76)
    ,
    (2007, 2, 383.59)
    ,
    (2007, 3, 384.47)
    ,
    (2007, 4, 385.83)
    ,
    (2007, 5, 386.56)
    ,
    (2007, 6, 385.91)
    ,
    (2007, 7, 384.35)
    ,
    (2007, 8, 382.24)
    ,
    (2007, 9, 380.55)
    ,
    (2007, 10, 380.62)
    ,
    (2007, 11, 382.09)
    ,
    (2007, 12, 383.52)
    ,
    (2008, 1, 384.62)
    ,
    (2008, 2, 385.45)
    ,
    (2008, 3, 386.36)
    ,
    (2008, 4, 387.71)
    ,
    (2008, 5, 388.42)
    ,
    (2008, 6, 387.75)
    ,
    (2008, 7, 386.19)
    ,
    (2008, 8, 384.08)
    ,
    (2008, 9, 382.4)
    ,
    (2008, 10, 382.49)
    ,
    (2008, 11, 383.96)
    ,
    (2008, 12, 385.4)
    ,
    (2009, 1, 386.51)
    ,
    (2009, 2, 387.36)
    ,
    (2009, 3, 388.25)
    ,
    (2009, 4, 389.61)
    ,
    (2009, 5, 390.37)
    ,
    (2009, 6, 389.73)
    ,
    (2009, 7, 388.2)
    ,
    (2009, 8, 386.11)
    ,
    (2009, 9, 384.45)
    ,
    (2009, 10, 384.59)
    ,
    (2009, 11, 386.13)
    ,
    (2009, 12, 387.64)
    ,
    (2010, 1, 388.83)
    ,
    (2010, 2, 389.75)
    ,
    (2010, 3, 390.69)
    ,
    (2010, 4, 392.11)
    ,
    (2010, 5, 392.88)
    ,
    (2010, 6, 392.25)
    ,
    (2010, 7, 390.7)
    ,
    (2010, 8, 388.58)
    ,
    (2010, 9, 386.87)
    ,
    (2010, 10, 386.94)
    ,
    (2010, 11, 388.41)
    ,
    (2010, 12, 389.85)
    ,
    (2011, 1, 390.95)
    ,
    (2011, 2, 391.77)
    ,
    (2011, 3, 392.64)
    ,
    (2011, 4, 393.98)
    ,
    (2011, 5, 394.71)
    ,
    (2011, 6, 394.05)
    ,
    (2011, 7, 392.48)
    ,
    (2011, 8, 390.36)
    ,
    (2011, 9, 388.66)
    ,
    (2011, 10, 388.76)
    ,
    (2011, 11, 390.27)
    ,
    (2011, 12, 391.75)
    ,
    (2012, 1, 392.9)
    ,
    (2012, 2, 393.79)
    ,
    (2012, 3, 394.75)
    ,
    (2012, 4, 396.16)
    ,
    (2012, 5, 396.93)
    ,
    (2012, 6, 396.3)
    ,
    (2012, 7, 394.77)
    ,
    (2012, 8, 392.71)
    ,
    (2012, 9, 391.08)
    ,
    (2012, 10, 391.25)
    ,
    (2012, 11, 392.82)
    ,
    (2012, 12, 394.35)
    ,
    (2013, 1, 395.56)
    ,
    (2013, 2, 396.48)
    ,
    (2013, 3, 397.43)
    ,
    (2013, 4, 398.85)
    ,
    (2013, 5, 399.65)
    ,
    (2013, 6, 399.02)
    ,
    (2013, 7, 397.47)
    ,
    (2013, 8, 395.35)
    ,
    (2013, 9, 393.64)
    ,
    (2013, 10, 393.74)
    ,
    (2013, 11, 395.25)
    ,
    (2013, 12, 396.73)
    ,
    (2014, 1, 397.88)
    ,
    (2014, 2, 398.75)
    ,
    (2014, 3, 399.65)
    ,
    (2014, 4, 401.04)
    ,
    (2014, 5, 401.79)
    ,
    (2014, 6, 401.12)
    ,
    (2014, 7, 399.52)
    ,
    (2014, 8, 397.36)
    ,
    (2014, 9, 395.63)
    ,
    (2014, 10, 395.71)
    ,
    (2014, 11, 397.21)
    ,
    (2014, 12, 398.69)
    ,
    (2015, 1, 399.83)
    ,
    (2015, 2, 400.71)
    ,
    (2015, 3, 401.63)
    ,
    (2015, 4, 403.05)
    ,
    (2015, 5, 403.84)
    ,
    (2015, 6, 403.22)
    ,
    (2015, 7, 401.7)
    ,
    (2015, 8, 399.64)
    ,
    (2015, 9, 398.02)
    ,
    (2015, 10, 398.23)
    ,
    (2015, 11, 399.89)
    ,
    (2015, 12, 401.51)
    ,
    (2016, 1, 402.8)
    ,
    (2016, 2, 403.81)
    ,
    (2016, 3, 404.88)
    ,
    (2016, 4, 406.38)
    ,
    (2016, 5, 407.2)
    ,
    (2016, 6, 406.58)
    ,
    (2016, 7, 405.03)
    ,
    (2016, 8, 402.93)
    ,
    (2016, 9, 401.26)
    ,
    (2016, 10, -99.4)
    ,
    (2016, 11, 402.95)
    ,
    (2016, 12, 404.46)
    ,
    (2017, 1, -99.63)
    ,
    (2017, 2, 406.52)
    ,
    (2017, 3, 407.43)
    ,
    (2017, 4, 408.83)
    ,
    (2017, 5, 409.59)
    ,
    (2017, 6, 408.91)
    ,
    (2017, 7, 407.3)
    ,
    (2017, 8, 405.12)
    ,
    (2017, 9, 403.36)
    ,
    (2017, 10, 403.43)
    ,
    (2017, 11, 404.93)
    ,
    (2017, 12, 406.41)
    ,
    (2018, 1, 407.54)
    ,
    (2018, 2, 408.4)
    ,
    (2018, 3, 409.32)
    ,
    (2018, 4, 410.73)
    ,
    (2018, 5, 411.52)
    ,
    (2018, 6, 410.9)
    ,
    (2018, 7, 409.36)
    ,
    (2018, 8, 407.26)
    ,
    (2018, 9, 405.61)
    ,
    (2018, 10, 405.8)
    ,
    (2018, 11, 407.42)
    ,
    (2018, 12, 409.01)
    ,
    (2019, 1, 410.25)
    ,
    (2019, 2, 411.2)
    ,
    (2019, 3, 412.17)
    ,
    (2019, 4, 413.64)
    ,
    (2019, 5, 414.45)
    ,
    (2019, 6, 413.83)
    ,
    (2019, 7, 412.27)
    ,
    (2019, 8, 410.14)
    ,
    (2019, 9, 408.44)
    ,
    (2019, 10, -99.99)
    ,
    (2019, 11, 410.15)
    ,
    (2019, 12, 411.69)
    ,
    (2020, 1, 412.9)
    ,
    (2020, 2, 413.81)
    ,
    (2020, 3, 414.8)
    ,
    (2020, 4, 416.24)
    ,
    (2020, 5, 417.01)
    ,
    (2020, 6, 416.34)
    ,
    (2020, 7, 414.75)
    ,
    (2020, 8, 412.6)
    ,
    (2020, 9, 410.88)
    ,
    (2020, 10, 411.01)
    ,
    (2020, 11, 412.55)
    ,
    (2020, 12, 414.06)
    ,
    (2021, 1, 415.23)
    ,
    (2021, 2, 416.12)
    ,
    (2021, 3, 417.04)
    ,
    (2021, 4, 418.45)
    ,
    (2021, 5, 419.23)
    ,
    (2021, 6, 418.56)
    ,
    (2021, 7, 416.96)
    ,
    (2021, 8, 414.78)
    ,
    (2021, 9, 413.04)
    ,
    (2021, 10, 413.15)
    ,
    (2021, 11, 414.7)
    ,
    (2021, 12, 416.21)
    ,
    (2022, 1, 417.37)
    ,
    (2022, 2, 418.23)
    ,
    (2022, 3, 419.12)
    ,
    (2022, 4, -99.99)
    )
  }
}