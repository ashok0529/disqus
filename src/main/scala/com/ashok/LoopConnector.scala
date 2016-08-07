package com.ashok

/**
  * Created by ashok on 8/3/16.
  */
object LoopConnector extends App {

  def init = {MongoConnector.connect(MONGO_HOST)}

  def getDataAndPersist() = {
    //Loop over apps
    //For Each App call method in Connector. This method will procure data and write to persistence store (Mongo)
    //N times for each app. Then sleep for one hour.
    //at end of each loop remember to write PREV to some file (in this case just write to mongo)
    val apps: Vector[DisqusApp] = Util.createApps()
    var t1: Long = System.currentTimeMillis()
    val PER_RND = 25
    val MAX_PER_HR = 1000
    while (true) {
      for (app: DisqusApp <- apps) {
        //each call gets 25 posts; each app is allowed 1,000 in  one hour
        //so loop 40 times; after all apps are done, sleep for remaining time
        val loop = (MAX_PER_HR / PER_RND)
        for (counter <- 1 to loop) {
          val nextCursor: String = MongoConnector.fetchNextCursor()
          var url = ""
          url += LIST_POSTS_URL
          url += app.API_KEY
          if (!nextCursor.isEmpty) {
            url += "?cursor"
            url += nextCursor
          }
          Connector.getDataAndPersist(url)
        }

      }
      //val diff:Long = (3600*1000) - (System.currentTimeMillis()-t1)
      //println(s"Sleeping for $diff ms")
      Util.sleep(120*1000)
      t1 = System.currentTimeMillis()
    }

  }
  def test ={
    println("Begin Testing")
    val apps = Util.createApps
    for(app:DisqusApp <- apps){
      println(app)
    }
  }

  init
  getDataAndPersist


}

case class DisqusApp(val API_KEY:String, val API_SECRET:String)
