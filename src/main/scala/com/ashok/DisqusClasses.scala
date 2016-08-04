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

  object Util {
    def createApps(): Vector[DisqusApp] = {

      var app1: DisqusApp = DisqusApp("2XH0ECZ3XiObj724Ita6yTydSsF0jbJyCeP8yHYfwj7c6jWcD0jBaaDhKI19FX3p", "nvf7HHACyAdaDKw5Pza5RPtZMZtoM1SffOvKXP49Ckme6re0bxDvoGPCDfmjAwAy")
      var app2: DisqusApp = DisqusApp("LFNaOOwiYUXj7Qa3RLYGQeUc42TQChKx0Qgg8ZfA75TrT5H128MrMJuRO33owk0m", "vujOjGCed7kxxxf8Zv7c5LaAXHrTKrBgEph9mZigVxipZPyN8BwiIU9cQ8rSXtfC")
      var apps: Vector[DisqusApp] = Vector()
      apps = apps :+ app2 :+ app1
      apps
    }
  }
