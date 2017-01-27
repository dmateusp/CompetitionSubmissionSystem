package helper

import java.io.File

import scala.io.Source

/**
  * Created by DanielMateusPires on 21/01/2017.
  */

object Comparer {
  private def bool2int(b:Boolean): Int = if (b) 1 else 0

  private def compareLineByLine(toCompare: List[(String,String)]): Long ={
    toCompare match {
      case Nil => 0
      case tocompare::tail => bool2int(tocompare._1 == tocompare._2) + compareLineByLine(tail)
    }
  }

  private def compareFiles(fileModel: File, fileToCompare: File): Option[Double] = {
    val source1 = Source.fromFile(fileModel)
    val linesFile1 = source1.getLines().toList
    source1.close()
    if(linesFile1.isEmpty) None
    else {
      val source2 = Source.fromFile(fileToCompare)
      val linesFile2 = source2.getLines().toList
      source2.close()

      val toCompare: List[(String,String)] = linesFile1 zip linesFile2
      val lengthOfModelFile: Double = linesFile1.length

      Some(compareLineByLine(toCompare) / lengthOfModelFile)
    }

  }
  def similarity(fileModel: File, fileToCompare: File): String = {

      compareFiles(fileModel, fileToCompare) match {
        case Some(similarity) => BigDecimal(similarity*100).setScale(3, BigDecimal.RoundingMode.HALF_UP).toString + '%'
        case None => "Got empty submission"
      }

  }

}
