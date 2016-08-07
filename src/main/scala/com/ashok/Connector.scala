package com.ashok

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.HttpConnectionParams
import java.io._

import com.fasterxml.jackson.databind.JsonNode
import com.ning.http.client.AsyncHttpClientConfig
import org.mongodb.scala._

import scala.collection.JavaConverters._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

//import org.apache.hadoop.hdfs.web.JsonUtil
import play.api.libs.json.JsValue
import play.libs.Json
import play.api.libs.ws.ning._
import play.api.libs.ws._


object Connector extends App{
  println("In Connector")

  def initMongoConnector = {
    MongoConnector.connect(MONGO_HOST)
  }

  def getDataAndPersist(list_posts_url:String) = {
    val content:JsonNode = HttpConnector.getWithPlay(list_posts_url)
    println(content.asText())
    var codeResult = -1
    codeResult = content.get("code").asInt(-2)
    val cursorNode:JsonNode = content.get("cursor")
    val hasNext:Boolean = cursorNode.get("hasNext").asBoolean(false)
    val hasPrev:Boolean = cursorNode.get("hasPrev").asBoolean(false)
    val next:String = cursorNode.get("next").asText("")
    val cursorId:String = cursorNode.get("id").asText("")
    val prev:String = cursorNode.get("prev").asText("")
    val more:Boolean = cursorNode.get("more").asBoolean(false)
    val dc:DisqusCursor = new DisqusCursor(prev,hasNext,next,hasPrev,cursorId,more)
    println("***** This Cursor : **** " + dc.toString)
    //val cursorIter:Iterator[String] = cursorNode.fieldNames().asScala
    /*while(cursorIter.hasNext) {
      val thisNode: String = cursorIter.next()
      val thisVal:String = cursorNode.get(thisNode).asText()
      println(s"This NODE:: $thisNode, $thisVal")
    }*/
    val responseNode:JsonNode = content.get("response")
    if(responseNode.isArray()){
      for(i:Int <- 0 until responseNode.size()){
        val thisNode:JsonNode = responseNode.get(i)
        try {
          val forum: String = thisNode.get("forum").asText("Unknown")
          println(s"[$i] Forum " + forum)
          val isDeleted: Boolean = thisNode.get("isDeleted").asBoolean(false)
          val isFlagged: Boolean = thisNode.get("isFlagged").asBoolean(false)
          val dislikes:Int = thisNode.get("dislikes").asInt(0)
          val message:String = thisNode.get("raw_message").asText("Unknown")
          val createdAt:String = thisNode.get("createdAt").asText("Unknown")
          val id:String = thisNode.get("id").asText("0")
          val authorNode:JsonNode = thisNode.get("author")
          var userName:String = ""
          var profileURL:String =  ""
          var authorId:String = ""
          var aboutAuthor:String = ""
          var authorName:String = ""
          var reputation:String = ""
          var location:String = ""
          if(authorNode != null){
            userName = authorNode.get("username").asText("Unknown")
            profileURL = authorNode.get("profileUrl").asText("Unknown")
            authorId = authorNode.get("id").asText("0")
            aboutAuthor= authorNode.get("about").asText("")
            authorName = authorNode.get("name").asText("")
            reputation = authorNode.get("reputation").asText("")
            location=authorNode.get("location").asText("")
          }
          val df:DisqusPost = DisqusPost(forum,isDeleted, isFlagged,dislikes,message,createdAt,
            userName,profileURL,authorId,aboutAuthor,authorName,reputation,location)

          println("*********DISQUS POST**********" + df.toString)
          val mongoDoc = df.getMongoDoc
          MongoConnector.insertDoc(mongoDoc)

        }catch{
          case e:Exception => {println(e.toString); e.printStackTrace()}

        }
      }
    }
  }



}




