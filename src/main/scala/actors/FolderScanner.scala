package actors

import java.io.File

import akka.actor.{Actor, Props}

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 23/01/2017.
  */
class FolderScanner extends Actor {

  // ref to child actor
  val filesScannerChildRef = context.actorOf(Props[FilesScanner], name = "filesScannerChild")

  // Finds sub folders in a root folder and starts a new child actor for each sub folder
  def sendSubFolders(path: String, regexes: List[Regex], fileToCompareAgainst: File): Unit ={
    val folder = new File(path)
    if (folder.exists && folder.isDirectory) {
      folder.listFiles.filter(_.isDirectory).map(directory => {
        filesScannerChildRef ! ScanForFilesMessage(directory.getAbsolutePath(), regexes, fileToCompareAgainst)
      })
    }
  }

  def receive = {
    case ScanThisFolderMessage(path, regexes, fileToCompareAgainst) =>
      sendSubFolders(path, regexes, fileToCompareAgainst)

    case Done() =>
      if (context.children.isEmpty) {
        context.stop(self)
      }
  }
}
