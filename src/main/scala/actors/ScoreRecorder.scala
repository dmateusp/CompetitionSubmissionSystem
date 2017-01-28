package actors

import akka.actor.Actor
import models.Submission
import slick.lifted.TableQuery
import slick.driver.SQLiteDriver.api._

/**
  * Created by DanielMateusPires on 27/01/2017.
  */
class ScoreRecorder extends Actor {

  // the query interface for the Submissions table
  val submissions: TableQuery[Submission] = TableQuery[Submission]

  // db
  val db: Database = Database.forURL("jdbc:sqlite:" + Master.dbPath, driver = "org.sqlite.JDBC")


  def receive = {
    case ScoreMessage(teamName, score) =>
      val q = for { s <- submissions if s.score < score } yield s.score
      val updateAction = q.update(score)

      val insertOrUpdate = DBIO.seq(
        updateAction,
        submissions += (teamName, score)
      )

      db.run(insertOrUpdate)
  }
}