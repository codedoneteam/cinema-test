# Test transaction execution

```
object SomeTransaction extends Transaction[One, Two] {
  override def apply(implicit sc: SagaContext[One]): Apply = execute { _ => {
      case One(i) => commit(Two(i+1))
    }
  }

  override def unapply(implicit sc: SagaContext[Two]): UnApply = compensate { _ => {
      case Two(i) => commit(One(i - 1))
    }
  }
}

class ApplyTransactionTest extends CinemaTest {

  "Some transaction" should "apply" in {
    assert(execute(SomeTransaction) ? One(1) == Two(2))
  }

}
```

# Test transaction compensation

```
class UnApplyTransactionTest extends CinemaTest {

  "UnApply transaction" should "complete" in {
    assert(compensate(SomeTransaction) ? Two(2)== One(1))
  }
}
```


#Build framework
sbt clean assembly release