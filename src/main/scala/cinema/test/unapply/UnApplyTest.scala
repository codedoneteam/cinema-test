package cinema.test.unapply

import cinema.app.CinemaAware
import cinema.saga.builder.SagaBuilder
import cinema.test.exception.TestFailedException
import cinema.transaction.AbstractTransaction
import cinema.transaction.exception.SagaException
import cinema.transaction.stateful.StatefulTransaction
import cinema.transaction.stateless.Transaction
import cinema.transaction.suspend.SuspendTransaction

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, _}
import scala.language.postfixOps
import scala.reflect.runtime.universe.TypeTag
import scala.util.{Failure, Try}


trait UnApplyTest extends Flip {
  this: CinemaAware =>

    class UnderUpApplyTest[In, Out](val flipped: AbstractTransaction[Out, In], val duration: Duration) {
      def ?(out: Out)(implicit typeTag: TypeTag[In], typeTag2: TypeTag[Out]): In = test(flipped)(out, duration)
    }

    def test[In, Out](flipped: AbstractTransaction[Out, In])(out: Out, duration: Duration)(implicit typeTag: TypeTag[In], typeTag2: TypeTag[Out]): In = {
      val future = SagaBuilder()
        .transaction(flipped)
        .build()
        .duration(duration)
        .run(out)
      Try { Await.result(future, duration) } match {
        case Failure(e: SagaException[_]) => e.message.asInstanceOf[In]
        case _ => throw new TestFailedException
      }
    }

    def compensate[In, Out](transaction: Transaction[In, Out]): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), 100 seconds)
    }

    def compensate[In, Out](transaction: Transaction[In, Out], duration: Duration): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), duration)
    }

    def compensate[In, Out](transaction: SuspendTransaction[In, Out]): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), 100 seconds)
    }

    def compensate[In, Out](transaction: SuspendTransaction[In, Out], duration: Duration): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), duration)
    }

    def compensate[In, State, Out](transaction: StatefulTransaction[In, State, Out]): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), 100 seconds)
    }

    def compensate[In, State, Out](transaction: StatefulTransaction[In, State, Out], duration: Duration): UnderUpApplyTest[In, Out] = {
      new UnderUpApplyTest(flip(transaction), duration)
    }
}
