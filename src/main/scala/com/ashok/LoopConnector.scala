package com.ashok

/**
  * Created by ashok on 8/3/16.
  */
object LoopConnector extends App {
  def createApps():Vector[DisqusApp] = {

    var app1:DisqusApp = DisqusApp("2XH0ECZ3XiObj724Ita6yTydSsF0jbJyCeP8yHYfwj7c6jWcD0jBaaDhKI19FX3p","nvf7HHACyAdaDKw5Pza5RPtZMZtoM1SffOvKXP49Ckme6re0bxDvoGPCDfmjAwAy")
    var app2:DisqusApp = DisqusApp("LFNaOOwiYUXj7Qa3RLYGQeUc42TQChKx0Qgg8ZfA75TrT5H128MrMJuRO33owk0m","vujOjGCed7kxxxf8Zv7c5LaAXHrTKrBgEph9mZigVxipZPyN8BwiIU9cQ8rSXtfC")
    var apps:Vector[DisqusApp] =  Vector()
    apps = apps :+ app2 :+ app1
    apps
  }

  def test ={
    println("Begin Testing")
    val apps = createApps
    for(app:DisqusApp <- apps){
      println(app)
    }

  }

  test

}

case class DisqusApp(val API_KEY:String, val API_SECRET:String)
