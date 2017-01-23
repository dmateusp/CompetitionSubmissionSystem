package actors
import java.io.File
import akka.actor._

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
class FilesScanner extends Actor {

  // refs to actors
  val fileComparerChildRef = context.actorOf(Props[FileComparer], name = "fileComparerChild")

  // Checks if the name of a file matches any of the regexes given as parameter
  def matchAnyRegex(input: File, regexes: List[Regex]): Boolean = {
    regexes match {
      case Nil => false
      case regex::tail => {
        regex.findFirstIn(input.getName()).isDefined || matchAnyRegex(input, tail)
      }
    }
  }

  // Creates a list of files with names matching the given regex
  def sendMatchingFiles(path: String, regexes: List[Regex], fileToCompareAgainst: File): List[File] ={
    val folder = new File(path)
    if (folder.exists && folder.isDirectory) {
      folder.listFiles.filter(_.isFile).filter(file => matchAnyRegex(file, regexes)).toList
    } else {
      List[File]()
    }
  }

  def receive = {

    case ScanForFilesMessage(path, regexes, fileToCompareAgainst) =>
      sendMatchingFiles(path, regexes, fileToCompareAgainst).foreach(file =>
        fileComparerChildRef ! MatchingFileMessage(file, fileToCompareAgainst))
  }
}
