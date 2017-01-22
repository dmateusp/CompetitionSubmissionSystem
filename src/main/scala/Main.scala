import actors.{Master, ScanThisFolderMessage, StartRoutineMessage}
import akka.actor.{ActorSystem, Props}

import scala.util.matching.Regex
/**
  * Created by DanielMateusPires on 20/01/2017.
  */
// Scanning a folder or multiple folders for a file or multiple files
object Main extends App {
  val system = ActorSystem("FileCheckerSystem")

  val masterRef = system.actorOf(Props[Master], name = "master")

  // Sends the message to start the routine
  masterRef ! StartRoutineMessage()

}
