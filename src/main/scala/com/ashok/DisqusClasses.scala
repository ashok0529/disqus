package com.ashok

import org.mongodb.scala._

/**
  * Created by ashok on 8/4/2016.
  */
case class DisqusCursor(val prev:String,val hasNext:Boolean,val next:String,val hasPrev:Boolean,val id:String,val more:Boolean){

}
case class DisqusPost(val forum:String,val isDeleted:Boolean, val isFlagged:Boolean,val dislikes:Int,val message:String, val createdAt:String,
                      val userName:String, profileURL:String, authorId:String, aboutAuthor:String, authorName:String){
  def getMongoDoc:Document = {
    val doc: Document = Document("forum" -> forum, "isDeleted" -> "isDeleted",
      "isFlagged" -> 1, "dislikes" -> dislikes, "message" -> message, "createdAt" -> createdAt,
      "userName" -> userName, "profileURL" -> profileURL, "authorId" -> authorId, "aboutAuthor"->aboutAuthor,"authorName"->authorName)
    doc
  }
}
