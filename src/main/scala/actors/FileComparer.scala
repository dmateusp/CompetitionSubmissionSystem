package actors

import java.io.{BufferedWriter, File, FileWriter}

import akka.actor.Actor
import helper.Comparer

/**
  * Created by DanielMateusPires on 21/01/2017.
  */
class FileComparer extends Actor {

  def receive = {
    case MatchingFileMessage(file, fileToCompareAgainst) =>
      // creates a file with the same name of the submission with "/result_" before
      val resultFileName = file.getParent() + "/result_" + file.getName()

      // FileWriter
      val fileResult = new File(resultFileName)
      val bw = new BufferedWriter(new FileWriter(fileResult))
      bw.write(Comparer.similarity(file, fileToCompareAgainst))
      bw.close()

      // deletes the file
      file.delete()
  }
}
