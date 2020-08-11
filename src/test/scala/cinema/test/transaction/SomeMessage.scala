package cinema.test.transaction

object SomeMessage {
  sealed trait SomeMessage
  case class One(int: Int) extends SomeMessage
  case class Two(int: Int) extends SomeMessage
}