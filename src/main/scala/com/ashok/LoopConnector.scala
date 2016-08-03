package com.ashok

/**
  * Created by ashok on 8/3/16.
  */
object LoopConnector {
  def createApps():Seq[App] = {
    var apps:Seq[App] = Vector.empty[App]
    var app1:DisqusApp = DisqusApp("2XH0ECZ3XiObj724Ita6yTydSsF0jbJyCeP8yHYfwj7c6jWcD0jBaaDhKI19FX3p","nvf7HHACyAdaDKw5Pza5RPtZMZtoM1SffOvKXP49Ckme6re0bxDvoGPCDfmjAwAy")
    var app2:DisqusApp = DisqusApp("LFNaOOwiYUXj7Qa3RLYGQeUc42TQChKx0Qgg8ZfA75TrT5H128MrMJuRO33owk0m","vujOjGCed7kxxxf8Zv7c5LaAXHrTKrBgEph9mZigVxipZPyN8BwiIU9cQ8rSXtfC")

  }

}

case class DisqusApp(val API_KEY:String, val API_SECRET:String)
