package example

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(arg: Array[String]) {
    // Create a Scala Spark Context.
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc: SparkContext = new SparkContext(conf)
    // Load our input data.
    val input = sc.textFile("src/main/resources/InputWordCount.txt") // from resources
    // Split it up into words.
    val words = input.flatMap(line => line.split(" "))
    // Transform into pairs and count.
    val counts = words.map(word => (word, 1)).reduceByKey { case (x, y) => x + y }
    // Save the word count back out to a text file, causing evaluation.
    counts.saveAsTextFile("src/main/resources/OutputWordCount.txt") // from resources
  }
}