package com.ashok

import java.util.concurrent.CountDownLatch

import org.mongodb.scala.result._
import org.mongodb.scala.{Completed, Observable, Observer, _}

/**
  * Created by ashok on 8/4/2016.
  */
object MongoHelpers {
  def deleteSync(coll:MongoCollection[Document] ,doc:Document): Unit ={
    val latch:CountDownLatch = new CountDownLatch(1)
    val deletedObs:Observable[DeleteResult] =  coll.deleteOne(doc)
    deletedObs.subscribe(new Observer[DeleteResult]{
      override def onNext(result: DeleteResult): Unit = {  latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
  }

  def insertSync(coll:MongoCollection[Document], doc:Document) = {
    val latch:CountDownLatch = new CountDownLatch(1)
    val observable: Observable[Completed] = coll.insertOne(doc)
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = {println("Inserted");latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
    latch.await()
  }
}
