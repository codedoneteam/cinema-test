package cinema.test.transaction

import cinema.saga.context.SagaContext
import cinema.test.transaction.SomeMessage.{One, Two}
import cinema.transaction.suspend.SuspendTransaction

object SomeSuspendTransaction extends SuspendTransaction[One, Two] {
  override def apply(implicit sc: SagaContext[One]): Apply = execute { _ => _ => {
      case One(i) => commit(Two(i+1))
    }
  }

  override def unapply(implicit sc: SagaContext[Two]): UnApply = compensate { _ => _ => {
      case Two(i) => commit(One(i - 1))
    }
  }
}