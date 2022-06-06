package com.github.polomarcus.utils

import com.typesafe.scalalogging.Logger

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
  def isClimateRelated(description: String): Boolean = {
    val pattern = new Regex(
      "(réchauffement|dérèglement|changement|crise|enjeux|volet)(|s) climatique(|s)")
    val pattern2 = new Regex("((réchauffement|dérèglement|changement) du climat)|(giec)|(climat :)")
      pattern.findFirstIn(description.toLowerCase).isDefined || pattern2
        .findFirstIn(description.toLowerCase)
        .isDefined
    }
}
