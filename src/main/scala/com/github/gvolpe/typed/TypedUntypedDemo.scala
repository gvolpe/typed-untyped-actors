package com.github.gvolpe.typed

import akka.actor.ActorSystem
import com.github.gvolpe.typed.actors.WorkerActor._
import com.github.gvolpe.typed.actors.TypedActor._
import com.github.gvolpe.typed.actors.{TypedActorRef, WorkerActor}

object TypedUntypedDemo extends App {

  implicit val system = ActorSystem("typed-untyped-demo")

  val worker: TypedActorRef[WorkerMessage] =
    system.actorOf(WorkerActor.props, "worker").typed[WorkerMessage]

  // This works fine
  worker ! Start(1L)

}
