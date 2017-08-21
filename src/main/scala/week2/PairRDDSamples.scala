package week2

import org.apache.spark.{SparkConf, SparkContext}

case class Event(organiser: String, name: String, budget: Int)

object PairRDDSamples {
  def main(array: Array[String]) = {

    // Create a Scala Spark Context.
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc: SparkContext = new SparkContext(conf)


    val events = List(
      Event("org1", "evt1", 10),
      Event("org2", "evt2", 20),
      Event("org3", "evt3", 30),
      Event("org1", "evt4", 40),
      Event("org2", "evt5", 50)
    )

    // total budget per organiser
    val eventsRdd = sc.parallelize(events).map(event => (event.organiser, event.budget))
    val reducedByKeys = eventsRdd.reduceByKey(_ + _)
    reducedByKeys.collect().foreach(println)

    val eventsRddWithEventCounts = eventsRdd.mapValues(b => (b, 1))
    eventsRddWithEventCounts.collect().foreach(println)

    // average budget per event organiser
    val intermediate = eventsRddWithEventCounts
      .reduceByKey((v1, v2) => (v1._1 + v2._1, v1._2 + v2._2))

    val avgBudgets = intermediate.mapValues {
      case (budget, numberOfEvents) => budget / numberOfEvents
    }
    avgBudgets.collect().foreach(println)

    // keys
    eventsRdd.keys.distinct.collect().foreach(println)
  }
}
