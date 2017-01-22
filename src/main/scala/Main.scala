import actors.{Master, StartRoutineMessage}
import akka.actor.{ActorSystem, Props}

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
object Main extends App {
  // Creates the main system
  val system = ActorSystem("FileCheckerSystem")

  val masterRef = system.actorOf(Props[Master], name = "master")

  // Sends the message to start the routine
  masterRef ! StartRoutineMessage()
}
