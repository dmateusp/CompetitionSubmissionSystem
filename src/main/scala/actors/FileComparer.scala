package actors

import java.io.{BufferedWriter, File, FileWriter}

import akka.actor.{Actor, Props}
import helper.Comparer

/**
  * Created by DanielMateusPires on 21/01/2017.
  */
class FileComparer extends Actor {

  val scoreRecorderRef = context.actorOf(Props[ScoreRecorder], name = "scoreRecorderChild")

  def receive = {
    case MatchingFileMessage(file, fileToCompareAgainst) =>
      // creates a file with the same name of the submission with "/result_" before
      val resultFileName = file.getParent() + "/result_" + file.getName()

      // FileWriter
      val fileResult = new File(resultFileName)
      val bw = new BufferedWriter(new FileWriter(fileResult))
      bw.write(Comparer.similarity(file, fileToCompareAgainst) match {
        case Some(similarity) => {
          scoreRecorderRef ! ScoreMessage(file.getParentFile().getName(), similarity)
          similarity.toString() + "%"
        }
        case None => "Got empty submission!"
      })
      bw.close()

      // deletes the file
      file.delete()
  }
}
