package actors

import java.io.File

import akka.actor.ActorSystem

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

// Sends a message containing a file matching the regex (this is a response to ScanForFilesMessage)
case class MatchingFileMessage(file: File, fileToCompareAgainst: File) extends FileCheckerMessage

// Sends a message to start the routine
case class StartRoutineMessage() extends FileCheckerMessage