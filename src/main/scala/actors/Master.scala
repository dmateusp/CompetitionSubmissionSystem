package actors

import java.io.File

import akka.actor.{Actor, Props}

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
class Master() extends Actor {
  // Gets the folder to scan (this is the root of the file system)
  val folderToScan: String = "C:\\Users\\DanielMateusPires\\Documents\\docs"

  // Sends a list of regex to match submissions (a submission must satisfy ANY of the following regexes)
  val filesToMatch: List[Regex] = List("""^competition1_.*""".r, """^myfilename_.*""".r)

  // Sends the correct answer to the problem (the submissions will be compared against this file)
  val fileToCompareAgainst: File = new File("C:\\Users\\DanielMateusPires\\Documents\\docs\\compareAgainst.txt")

  // Creates a ref to the folder scanner actor
  val folderScannerRef = context.actorOf(Props[FolderScanner], name = "folderScanner")

  def receive = {
    case StartRoutineMessage() ⇒
      folderScannerRef ! ScanThisFolderMessage(folderToScan, filesToMatch, fileToCompareAgainst)

    case Done() =>
      if (context.children.isEmpty) {
        sender ! Done()
        context.stop(self)
      }
  }
}