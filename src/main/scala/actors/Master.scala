package actors

import java.io.{File, FileInputStream}

import akka.actor.{Actor, Props}
import com.typesafe.config.ConfigFactory

import scala.util.matching.Regex
import scala.collection.JavaConversions._

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
class Master() extends Actor {
  // Creates a ref to the folder scanner actor
  val folderScannerRef = context.actorOf(Props[FolderScanner], name = "folderScanner")

  def receive = {
    case StartRoutineMessage() ⇒
      folderScannerRef ! ScanThisFolderMessage(Master.rootFolder, Master.filesToMatch, Master.fileToCompareAgainst)
  }
}
object Master{
  // Getting config
  val conf = ConfigFactory.load()
  // Gets the folder to scan (this is the root of the file system)
  val rootFolder: String = new File(conf.getString("config.rootFolder")).getAbsolutePath()

  // Gets DB path
  val dbPath: String = new File(conf.getString("config.dbPath")).getAbsolutePath()

  // Sends a list of regex to match submissions (a submission must satisfy ANY of the following regexes)
  val filesToMatch: List[Regex] = conf.getStringList("config.filesToMatch").map(regex => regex.r).toList

  // Sends the correct answer to the problem (the submissions will be compared against this file)
  val fileToCompareAgainst: File = new File(conf.getString("config.solution"))
}