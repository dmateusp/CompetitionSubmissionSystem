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

  private def compareFiles(fileModel: File, fileToCompare: File): Double = {
    val linesFile1 = Source.fromFile(fileModel).getLines().toList
    val linesFile2 = Source.fromFile(fileToCompare).getLines().toList

    val toCompare: List[(String,String)] = linesFile1 zip linesFile2
    val lengthOfModelFile: Double = linesFile1.length

    compareLineByLine(toCompare) / lengthOfModelFile
  }
  def similarity(fileModel: File, fileToCompare: File): String = {
    BigDecimal(compareFiles(fileModel, fileToCompare)*100).setScale(3, BigDecimal.RoundingMode.HALF_UP).toString + '%'
  }

}
