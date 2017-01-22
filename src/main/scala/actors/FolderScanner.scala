package actors
import java.io.File
import java.nio.file.Files

import akka.actor
import akka.actor._

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
class FolderScanner extends Actor {

  // refs to actors
  val folderScannerChildRef = context.actorOf(Props[FolderScanner], name = "folderScannerChild")
  val fileComparerChildRef = context.actorOf(Props[FileComparer], name = "fileComparerChild")


  def matchAnyRegex(input: File, regexes: List[Regex]): Boolean = {
    regexes match {
      case Nil => false
      case regex::tail => {
        regex.findFirstIn(input.getName()).isDefined || matchAnyRegex(input, tail)
      }
    }
  }

  def sendSubFolders(path: String, regexes: List[Regex], fileToCompareAgainst: File): Unit ={
    val folder = new File(path)
    if (folder.exists && folder.isDirectory) {
      folder.listFiles.filter(_.isDirectory).map(directory => {
        folderScannerChildRef ! ScanForFilesMessage(directory.getAbsolutePath(), regexes, fileToCompareAgainst)
      })
    }
  }
  def sendMatchingFiles(path: String, regexes: List[Regex], fileToCompareAgainst: File): List[File] ={
    val folder = new File(path)
    if (folder.exists && folder.isDirectory) {
      folder.listFiles.filter(_.isFile).filter(file => matchAnyRegex(file, regexes)).toList
    } else {
      List[File]()
    }
  }

  def receive = {
    case ScanThisFolderMessage(path, regexes, fileToCompareAgainst) =>
      sendSubFolders(path, regexes, fileToCompareAgainst)

    case ScanForFilesMessage(path, regexes, fileToCompareAgainst) =>
      sendMatchingFiles(path, regexes, fileToCompareAgainst) match {
        case files@(file::tail) => fileComparerChildRef ! MatchingFilesMessage(files, fileToCompareAgainst)
      }

    case DoneComparingFilesMessage() =>
      if(context.children.isEmpty) {
        sender ! DoneScanningFolderMessage()
        //context.stop(self)
      }
    case DoneScanningFolderMessage() =>
      if(context.children.isEmpty) {
        //context.stop(self)
        sender ! DoneScanningFolderMessage()
      }
  }
}
