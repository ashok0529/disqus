package com.ashok


import java.util.concurrent.CountDownLatch

import org.mongodb.scala.result.DeleteResult
import org.mongodb.scala.{Completed, Document, FindObservable, MongoClient, MongoCollection, MongoDatabase, Observable, Observer}

/**
  * Created by ashok on 8/3/2016.
  */
object MongoConnector {
  var collection:MongoCollection[Document] = null
  var collection_meta:MongoCollection[Document] = null
   def connect(host:String) = {
     var connected:Boolean = true
      println(s"Attempting connection to $host")
      val url:String = "mongodb://"+host
      try{
        // To directly connect to the default server localhost on port 27017
        val mongoClient: MongoClient = MongoClient(url)
        val database: MongoDatabase = mongoClient.getDatabase("disqusdb")
        collection = database.getCollection("post")
        collection_meta = database.getCollection("post_meta")
      }catch{
        case e:Exception  => {
          println(s"Error connecting to Host $host " + e.toString)
          connected = false
        }
        case e:Error => {
          println(s"Error connecting to Host $host " + e.toString)
          connected = false
        }
          System.exit(1)
      }
      if(connected) println("Connected")
  }

  def insertDoc(doc:Document): Unit ={
    MongoHelpers.insertSync(collection,doc)
  }

  def fetchNextCursor():String = {
    var nextCursor:String = ""
    val latch = new CountDownLatch(1)
    val nextVal:FindObservable[String] = collection_meta.find(nextDocFinder)
    nextVal.subscribe(new Observer[String]{
      override def onNext(result: String): Unit = { nextCursor = result; latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
    latch.await()
    nextCursor
  }

  def writeNextCursor(next:String) = {
    //first remove
    MongoHelpers.deleteSync(collection_meta,nextDocFinder)
    val doc: Document = Document("nextcursor" -> next)
    val observable: Observable[Completed] = collection_meta.insertOne(doc)
  }
  def fetchCount() = {
    val latch = new CountDownLatch(1)
    var numDocs:Long = 0
    val observablecount:Observable[Long] = collection.count()
    observablecount.subscribe(new Observer[Long]{
      override def onNext(result: Long): Unit = { numDocs = result ; latch.countDown()}
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
    latch.await()
    println("Number of docs = " + numDocs )
  }
  def insertTest={
    val doc: Document = Document("name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 100))
    val observable: Observable[Completed] = collection.insertOne(doc)
    observable.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = println(" \n\nFailed " + e + "\n\n")
      override def onComplete(): Unit = println("Completed")
    })
  }

  def insert(disqusPost: DisqusPost = null): Unit ={
    if(disqusPost == null){
      insertTest
    }
  }

  def main(args:Array[String]) = {
    connect("mongomaster")
    fetchCount()
  }




}
