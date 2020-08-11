package cinema.test.transaction

import cinema.saga.context.SagaContext
import cinema.test.transaction.SomeMessage.{One, Two}
import cinema.transaction.stateful.StatefulTransaction

object SomeStatefulTransaction extends StatefulTransaction[One, Nothing, Two] {
  override def apply(state: Option[Nothing])(implicit sc: SagaContext[One]): SomeStatefulTransaction.Apply = execute { _ => _ => _ => {
      case One(i) => commit(Two(i+1))
    }
  }

  override def unapply(state: Option[Nothing])(implicit sc: SagaContext[Two]): SomeStatefulTransaction.UnApply = compensate { _ => _ => _ => {
    case Two(i) => commit(One(i - 1))
    }
  }
}