package com.ashok


import java.util.concurrent.CountDownLatch

import com.mongodb.{BasicDBObject, DBObject}
import org.bson.{BSON, BSONObject}
import org.mongodb.scala.bson.BsonRegularExpression
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
    val doc:Document = Document("nextcursor" -> BsonRegularExpression(".*"))
    MongoHelpers.findStringValSync(collection_meta,doc)
  }

  def writeNextCursor(next:String) = {
    //first remove
    var doc:Document = Document("nextcursor" -> BsonRegularExpression(".*"))
    MongoHelpers.deleteSync(collection_meta,doc)
    doc = Document("nextcursor" -> next)
    MongoHelpers.insertSync(collection_meta,doc)
  }
  def fetchCount() = {
    MongoHelpers.fetchCountSync(collection)

  }
  def insertTest={
    val doc: Document = Document("name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 100))
    MongoHelpers.insertSync(collection,doc)
  }

  def insert(disqusPost: DisqusPost = null): Unit ={
    if(disqusPost == null){
      insertTest
    }
  }

  def main(args:Array[String]):Unit = {
    connect("mongomaster")
    insertTest
    val numDocs = fetchCount()
    println(s"Num Docs : $numDocs")
    val s = fetchNextCursor()
    println("Next cursor " + s)
    writeNextCursor("9013")
  }
}
