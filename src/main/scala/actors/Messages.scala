package actors

import java.io.File

import scala.util.matching.Regex

/**
  * Created by DanielMateusPires on 20/01/2017.
  */

// Declaring all possible messages
sealed trait FileCheckerMessage

// Sends a message to scan a root folder (and look for sub directories
case class ScanThisFolderMessage(path: String, regexes: List[Regex], fileToCompareAgainst: File) extends FileCheckerMessage

// Sends a message to scan for files (matching regex) in a given folder
case class ScanForFilesMessage(path: String, regexes: List[Regex], fileToCompareAgainst: File) extends FileCheckerMessage

// Sends a message containing the files matching the regex (this is a response to ScanForFilesMessage)
case class MatchingFilesMessage(files: List[File], fileToCompareAgainst: File) extends FileCheckerMessage

// Sends a message signaling that the job "compating files" is done (so the actor can be terminated)
case class DoneComparingFilesMessage() extends FileCheckerMessage

// Sends a message signaling that the job "scanning the folder" is done (so the actor can be terminated)
case class DoneScanningFolderMessage() extends FileCheckerMessage

// Sends a message to start the routine
case class StartRoutineMessage() extends FileCheckerMessage