package cinema.test

import cinema.test.transaction.SomeMessage.{One, Two}
import cinema.test.transaction.SomeStatefulTransaction

import scala.language.postfixOps

class ApplyTransactionTest extends CinemaTest {

  "Some transaction" should "apply" in {
    assert(execute(SomeStatefulTransaction) ? One(1) == Two(2))
  }

}
