package cinema.test.selection

import java.util.UUID

import akka.actor.typed.DispatcherSelector.default
import akka.actor.typed.{ActorRef, Behavior, DispatcherSelector}
import cinema.app.CinemaAware

import scala.concurrent.{Future, Promise}
import scala.reflect.runtime.universe.TypeTag
import scala.util.Try

trait TestSelectionAware {
  this: CinemaAware =>
    def actorRef[A](behavior: Behavior[A], name: String = UUID.randomUUID().toString, ds: DispatcherSelector = default())
                   (implicit typeTag: TypeTag[A]): Future[ActorRef[A]] = {
      val promise = Promise[ActorRef[A]]()
      actorOf(behavior, name, ds) { ref => promise.complete(Try(ref))}
      promise.future
    }
}