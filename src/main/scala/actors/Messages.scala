package actors

import java.io.File

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */
sealed trait FileCheckerMessage
case class ScanThisFolderMessage(path: String, regexes: List[Regex], fileToCompareAgainst: File) extends FileCheckerMessage
case class ScanForFilesMessage(path: String, regexes: List[Regex], fileToCompareAgainst: File) extends FileCheckerMessage
case class MatchingFilesMessage(files: List[File], fileToCompareAgainst: File) extends FileCheckerMessage
case class DoneComparingFilesMessage() extends FileCheckerMessage
case class DoneScanningFolderMessage() extends FileCheckerMessage
case class StartRoutineMessage() extends FileCheckerMessage