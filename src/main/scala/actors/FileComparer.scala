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

      val resultFileName = file.getParent() + "\\_result_" + file.getName()

      // FileWriter
      val fileResult = new File(resultFileName)
      val bw = new BufferedWriter(new FileWriter(fileResult))
      bw.write(Comparer.similarity(file, fileToCompareAgainst))
      bw.close()

      sender ! Done()
  }
}
