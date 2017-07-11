typed-untyped-actors
====================

Add types to your old plain Akka actors with minimal code and get the best of both worlds!

At the moment the WorkerActor is handling both Start and Cancel messages. If you remove the handling of Cancel for example, you'll get a warning message like the following one when compiling:

```
[warn] /home/gvolpe/development/workspace/typed-untyped-actors/src/main/scala/com/github/gvolpe/typed/actors/WorkerActor.scala:16: match may not be exhaustive.
[warn] It would fail on the following input: Cancel(_)
[warn]   override def typedReceive = {
[warn]                               ^
[warn] one warning found
```