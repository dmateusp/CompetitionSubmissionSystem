package actors

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
class Master extends Actor {
  val foldersToScan: Array[String] = Array("C:\\Users\\DanielMateusPires\\Documents\\docs", "./docs2")
  val filesToMatch: List[Regex] = List("""^competition1_.*""".r, """^myfilename_.*""".r)
  val fileToCompareAgainst: File = new File("C:\\Users\\DanielMateusPires\\Documents\\docs\\compareAgainst.txt")

  val folderScannerRef = context.actorOf(Props[FolderScanner], name = "folderScanner")

  def receive = {
    case StartRoutineMessage() ⇒
      folderScannerRef ! ScanThisFolderMessage(foldersToScan.head, filesToMatch, fileToCompareAgainst)
  }
}