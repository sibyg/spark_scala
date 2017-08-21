case class Taco(kind: String, price: Double)

val tacoOrder = List(
  Taco("Carnitas", 2.25),
  Taco("Corn", 1.75),
  Taco("Barbacoa", 2.50),
  Taco("Chicken", 2.00)
)

val cost = tacoOrder.foldLeft(0.0)((sum, taco) => sum + taco.price)

/**
  * Advantages:
  * a. Converts from one type to another type
  * Disadvantages:
  * a. Sequential
  *
  * @param z
  * @param f
  * @tparam B
  * @tparam A
  * @return
  */
def foldLeft[B, A](z: B)(f: (B, A) => B): B

/**
  * Advantages:
  * a. Parallelizable
  * Disadvantages:
  * a. Same type conversion
  *
  * @param z
  * @param f
  * @tparam A
  * @return
  */
def fold[A](z: A)(f: (A, A) => A): A

/**
  * Advantages:
  * a. Supports both one type to another or same type conversion
  * b. Parallelizable
  *
  * @param z
  * @param seqop
  * @param combop
  * @tparam B
  * @tparam A
  * @return
  */
def aggregate[B, A](z: => B)(seqop: (B, A) => B, combop: (B, B) => B): B