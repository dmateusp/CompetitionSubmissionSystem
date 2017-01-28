package models

import slick.driver.SQLiteDriver.api._

/**
  * Created by DanielMateusPires on 27/01/2017.
  */
class Submission(tag: Tag) extends Table[(String, Int)](tag, "SUBMISSIONS") {
  def team = column[String]("TEAM")
  def score = column[Int]("SCORE")
  def * = (team, score)
}