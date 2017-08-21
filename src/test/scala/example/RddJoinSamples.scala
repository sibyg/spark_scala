package example

import example.Tariff.{AG, DemiTariff, DemiTariffVisa}
import org.apache.spark.{SparkConf, SparkContext}

object Tariff extends Enumeration {
  type Tariff = Value
  val AG, DemiTariff, DemiTariffVisa = Value
}

object RddJoinSamples {
  def main(array: Array[String]) = {

    // Create a Scala Spark Context.
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc: SparkContext = new SparkContext(conf)

    val as = List(
      (101, ("Ruetli", AG)),
      (102, ("Brelaz", DemiTariff)),
      (103, ("Gress", DemiTariffVisa)),
      (104, ("Schatten", DemiTariff))
    )

    val abos = sc.parallelize(as)

    val ls = List(
      (101, "Bern"), (101, "Thun"),
      (102, "Lausanne"), (102, "Geneve"), (102, "Nyon"),
      (103, "Zurich"), (103, "St-Gallen"), (103, "Chur")
    )

    val locations = sc.parallelize(ls)

    // customers that have a subscription and where there is a location info
    val abosAndLocations = abos.join(locations)

    // location information of subscribers
    val abosWithOptionalLocations = abos.leftOuterJoin(locations)

    // customer using app and using
    val customersWithLocationDataAndOptionalAbos = abos.rightOuterJoin(locations)
  }
}
