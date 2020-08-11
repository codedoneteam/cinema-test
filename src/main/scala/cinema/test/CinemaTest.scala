package cinema.test

import cinema.app.CinemaAware
import cinema.test.apply.ApplyTest
import cinema.test.selection.TestSelectionAware
import cinema.test.unapply.UnApplyTest
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

import scala.language.postfixOps

trait CinemaTest extends FlatSpec with CinemaAware with ApplyTest with UnApplyTest with TestSelectionAware with BeforeAndAfterAll
