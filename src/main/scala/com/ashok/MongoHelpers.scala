package com.ashok

import java.util.concurrent.{CountDownLatch, TimeUnit}

import org.bson.BSONObject
import org.mongodb.scala.bson.{BsonString, BsonValue}
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
    latch.await()
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

  def findStringValSync(coll:MongoCollection[Document], doc:Document):String = {
    var found:Option[BsonValue] = null
    var foundString:String = ""
    val latch = new CountDownLatch(1)
    val nextVal:FindObservable[Document] = coll.find(doc)
    nextVal.subscribe(new Observer[Document]{
      override def onNext(result: Document): Unit = { found = result.get("nextcursor"); latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
    latch.await(1000,TimeUnit.MILLISECONDS)
    println(s"Found $found")
    found match{
      case Some(x:BsonValue) => {foundString = x.asString().getValue}
      case _ => foundString = ""
    }
    foundString

  }

  def fetchCountSync(coll:MongoCollection[Document]):Long = {
    val latch = new CountDownLatch(1)
    var numDocs:Long = 0
    val observablecount:Observable[Long] = coll.count()
    observablecount.subscribe(new Observer[Long]{
      override def onNext(result: Long): Unit = { numDocs = result ; latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
    latch.await()
    numDocs
  }
}
