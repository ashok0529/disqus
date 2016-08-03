package com.ashok

import com.mongodb.casbah.{MongoCollection, MongoConnection}

/**
  * Created by ashok on 8/3/2016.
  */
class MongoConnector {
  var mongoConnection:MongoConnection = null
  var mongoCollection:MongoCollection = null
   def connect(host:String) = {
     var connected:Boolean = true
      println(s"Attempting connection to $host")
      try{
        mongoConnection = MongoConnection(host)
        val dbs = mongoConnection.dbNames
        for(s:String <- dbs){
          println("DB : "  + s)
        }
        //mongoCollection = mongoConnection.  //("disqusdb")("post")
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

}
