package cinema.test

import cinema.test.transaction.SomeMessage.{One, Two}
import cinema.test.transaction.{SomeStatefulTransaction, SomeSuspendTransaction}

import scala.language.postfixOps

class UnApplyTransactionTest extends CinemaTest {

  "UnApply transaction" should "complete" in {
    assert(compensate(SomeStatefulTransaction) ? Two(2)== One(1))
  }

  "UnApply suspend transaction" should "complete" in {
    assert(compensate(SomeSuspendTransaction) ? Two(2)== One(1))
  }

  "UnApply stateful transaction" should "complete" in {
    assert(compensate(SomeStatefulTransaction) ? Two(2)== One(1))
  }
}
