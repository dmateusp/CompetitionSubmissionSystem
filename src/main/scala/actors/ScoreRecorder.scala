package actors

import akka.actor.Actor

/**
  * Created by DanielMateusPires on 27/01/2017.
  */
class ScoreRecorder extends Actor {

  // the query interface for the Submissions table
  val coffees: TableQuery[Coffees] = TableQuery[Coffees]

  def receive = {
    case ScoreMessage(teamName, score) =>
      // Write to database

  }
}