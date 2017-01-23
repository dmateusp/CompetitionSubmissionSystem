import actors.{Master, StartRoutineMessage}
import akka.actor.{ActorSystem, Props}
import scala.concurrent.duration._

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
object Main extends App {
  // Creates the main system
  val system = ActorSystem("FileCheckerSystem")
  import system.dispatcher

  val masterRef = system.actorOf(Props[Master], name = "master")

  // Sends the message to start the routine every 3 seconds
  system.scheduler.schedule(0 seconds, 3 seconds) {
    masterRef ! StartRoutineMessage()
  }

}