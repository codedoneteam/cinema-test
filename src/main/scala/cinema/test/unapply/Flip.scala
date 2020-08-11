package cinema.test.unapply

import cinema.hvector.HVector.HNil
import cinema.saga.context.SagaContext
import cinema.transaction.stateful.StatefulTransaction
import cinema.transaction.stateless.Transaction
import cinema.transaction.suspend.SuspendTransaction


trait Flip {

  def flipContext[B](sc: SagaContext[B]): SagaContext[B] = sc.copy(reverse = HNil)

  def flip[A, B](transaction: Transaction[A, B]): Transaction[B, A] = {

    object Flipped extends Transaction[B, A] {
      override def apply(implicit sc: SagaContext[B]): Flipped.Apply = transaction.unapply(flipContext(sc))

      override def unapply(implicit sc: SagaContext[A]): Flipped.UnApply = transaction.apply(flipContext(sc))
    }

    Flipped
  }

  def flip[A, B](transaction: SuspendTransaction[A, B]): SuspendTransaction[B, A] = {

    object Flipped extends SuspendTransaction[B, A] {
      override def apply(implicit sc: SagaContext[B]): Flipped.Apply = transaction.unapply(flipContext(sc))

      override def unapply(implicit sc: SagaContext[A]): Flipped.UnApply = transaction.apply(flipContext(sc))
    }

    Flipped
  }

  def flip[A, State, B](transaction: StatefulTransaction[A, State, B]): StatefulTransaction[B, State, A] = {

    object Flipped extends StatefulTransaction[B, State, A] {
      override def apply(state: Option[State])(implicit sc: SagaContext[B]): Flipped.Apply = {
        transaction.unapply(state)(flipContext(sc))
      }

      override def unapply(state: Option[State])(implicit sc: SagaContext[A]): Flipped.UnApply = {
        transaction.apply(state)(flipContext(sc))
      }
    }

    Flipped
  }
}