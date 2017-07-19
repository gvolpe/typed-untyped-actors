package com.github.gvolpe.typed.actors

import akka.actor.{Actor, ActorRef}

import scala.reflect.ClassTag

/**
  * Simple [[TypedActor]] that gives any class implementing it the power to have typed messages making
  * proper use of the compiler for type check exhaustiveness by just using a typed [[Function1]].
  *
  * For convenience use this abstract class instead of using directly [[Actor]].
  * */
abstract class TypedActor[A : ClassTag] extends Actor {
  type TypedReceive = A => Unit
  def typedReceive: TypedReceive

  private def liftToPF[X <: Y : ClassTag, W, Y](f: Function[X, W]): PartialFunction[Y, W] =
    new PartialFunction[Y, W] {
      override def isDefinedAt(x: Y): Boolean = implicitly[ClassTag[X]].runtimeClass.isAssignableFrom(x.getClass)
      override def apply(v1: Y): W = f(v1.asInstanceOf[X])
    }

  override def receive: Receive = liftToPF[A, Unit, Any](typedReceive)
}

class TypedActorRef[A](ref: ActorRef) {
  def !(msg: A): Unit = ref ! msg
}

object TypedActor {

  implicit class ActorRefOps(ref: ActorRef) {
    def typed[A]: TypedActorRef[A] = new TypedActorRef[A](ref)
  }

}