package com.github.gvolpe.typed.actors

import akka.actor.{Actor, ActorRef}

/**
  * Simple [[TypedActor]] that gives any class implementing it the power to have typed messages making
  * proper use of the compiler for type check exhaustiveness by just using a typed [[Function1]].
  *
  * For convenience use this trait instead of using directly [[Actor]] unless you have a good reason.
  * */
trait TypedActor[A] extends Actor {
  type TypedReceive = A => Unit
  def typedReceive: TypedReceive

  private def liftToPF[X <: Y, W, Y](f: Function[X, W]): PartialFunction[Y, W] =
    new PartialFunction[Y, W] {
      override def isDefinedAt(x: Y): Boolean = x.isInstanceOf[X]
      override def apply(v1: Y): W = f(v1.asInstanceOf[X])
    }

  override def receive: Receive = liftToPF(typedReceive)
}

class TypedActorRef[A](ref: ActorRef) {
  def !(msg: A): Unit = ref ! msg
}

object TypedActor {

  implicit class ActorRefOps(ref: ActorRef) {
    def typed[A]: TypedActorRef[A] = new TypedActorRef[A](ref)
  }

}