package com.github.gvolpe.typed.actors

import akka.actor.Props
import com.github.gvolpe.typed.actors.WorkerActor._

object WorkerActor {
  sealed trait WorkerMessage
  final case class Start(id: Long)  extends WorkerMessage
  final case class Cancel(id: Long) extends WorkerMessage

  def props: Props = Props[WorkerActor]
}

class WorkerActor extends TypedActor[WorkerMessage] {

  override def typedReceive = {
    case Start(id)  => println(s"Start >> $id")
    case Cancel(id) => println(s"Cancel >> $id")
  }

}
