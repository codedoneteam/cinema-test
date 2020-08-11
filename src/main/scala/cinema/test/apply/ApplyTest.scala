package cinema.test.apply

import cinema.app.CinemaAware
import cinema.saga.builder.SagaBuilder
import cinema.transaction.AbstractTransaction

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.runtime.universe.TypeTag

trait ApplyTest {
  this: CinemaAware =>

    class ApplyTest[In, Out](val transaction: AbstractTransaction[In, Out], duration: Duration) {
      def ?(in: In)(implicit typeTag: TypeTag[In], typeTag2: TypeTag[Out]): Out = {
        val future = SagaBuilder()
          .transaction(transaction)
          .build()
          .duration(duration)
          .run(in)
        Await.result(future, duration)
      }
    }

    def execute[In, Out](transaction: AbstractTransaction[In, Out]): ApplyTest[In, Out] = {
      new ApplyTest(transaction, 100 seconds)
    }

    def execute[In, Out](transaction: AbstractTransaction[In, Out], duration: Duration): ApplyTest[In, Out] = {
      new ApplyTest(transaction, duration)
    }
}