package com.ashok

/**
  * Created by ashok on 8/4/2016.
  */
object Util {
  def createApps(): Vector[DisqusApp] = {

    var app1: DisqusApp = DisqusApp("2XH0ECZ3XiObj724Ita6yTydSsF0jbJyCeP8yHYfwj7c6jWcD0jBaaDhKI19FX3p", "nvf7HHACyAdaDKw5Pza5RPtZMZtoM1SffOvKXP49Ckme6re0bxDvoGPCDfmjAwAy")
    var app2: DisqusApp = DisqusApp("LFNaOOwiYUXj7Qa3RLYGQeUc42TQChKx0Qgg8ZfA75TrT5H128MrMJuRO33owk0m", "vujOjGCed7kxxxf8Zv7c5LaAXHrTKrBgEph9mZigVxipZPyN8BwiIU9cQ8rSXtfC")
    var app3: DisqusApp = DisqusApp("LVIDbbUpRsnjucXNEaRGzGLXdMIfnrr4fjSiSex4R7ddj5A4cXDIyytZayXBfXbh","CZsVqnnKUl6FsrxduChdsJvLHomyvxvgg1zRaiiqq9KB80K2xc5pHT9euAeXx7qy")
    var app4: DisqusApp = DisqusApp("i6dEORk6fAJ5pgOdENKynnPMdgicRod2XLGZsPiVAnCtyp99bed8ryzCOcmfYrFP","Ol1KUhOIIQnSAz0KNIerb8Dr4dfGvofDsca01ZefMK8e0tb8X2D2yehT19PImrAX")
    var app5: DisqusApp = DisqusApp("x0XgXIah41s47VyBC7XeUQkYAWtBuQeP9xxnIuc66ntHWzowCJ9kEmw7fJeXSnBe","7UHFWpVu5R2VO6efpgKW804NHHwrTtTZitmurZ8X1Q6gJb9X79wuvpiAnxfgA4zT")
    var app6: DisqusApp = DisqusApp("wEN3BgPxp9UDOfLriIizfmIESLa4SzhzsmXR0PAua0pwMaOL2b9ja1SUAbiQL9v0","QTjUptR4uTB8IXfVyEKKqWEqb0G7POvdjvHcxWbk49qSEq2woHXt3jsR8R91Mnc8")
    var app7: DisqusApp = DisqusApp("wEN3BgPxp9UDOfLriIizfmIESLa4SzhzsmXR0PAua0pwMaOL2b9ja1SUAbiQL9v0","QTjUptR4uTB8IXfVyEKKqWEqb0G7POvdjvHcxWbk49qSEq2woHXt3jsR8R91Mnc8")
    var app8: DisqusApp = DisqusApp("7PrGoIrC21mFdNMdf7TIBwYwbMJhReOnZ6UlOwtPCYvnByN8EqbCJJbn97ByudMc","sQGOnQsLF0YMRJORuZqdA2pXvIKECbiKMJHSYCVqXAFUcHbAT8Ra6wbO7rzg7p1j")
    var app9: DisqusApp = DisqusApp("uQTFgNipQCaiWSVIkbc0O1DZv7bW9rJ3JOFI9OUsEL8u7JDPnxBFNM178q3sZcVv","FsxMwom9NUYTmf51YDWX4JNSHHr6gUi8JYhzz6XAR8Gbp53OLEJJlBzcxN0Ka27X")
    var app10:DisqusApp = DisqusApp("WEhUJvx0aGzwY4Ju8E4FgZJ6ZCriI1faTuR1Wttum1PWEytkFG9vESy3vPHuk1GV","Y4jVezdMEwjxVpKAmD1fZitSDwKHLP4RpSbPdxLzuRzeuKTLnToV33svsKa59Oc6")

    var apps: Vector[DisqusApp] = Vector()
    apps = apps :+ app1 :+ app2 :+ app3  :+ app4 :+ app5 :+ app6 :+ app7 :+ app7 :+app8 :+ app9 :+ app10
    apps
  }

  /**
    * Created by ashok on 8/4/2016.
    */

  def sleep(_duration:Long) = {
    var duration:Long = _duration
    var t1:Long = System.currentTimeMillis()
    var done: Boolean = false
    while (!done){
      try {
        val t = System.currentTimeMillis()
        Thread.sleep(duration)
        val diff = System.currentTimeMillis() - t
        println(s"Actually slept for $diff ms")
        done = true
      } catch {
        case e: Exception => {
          println("exception in thread sleep")
          var t2:Long = System.currentTimeMillis()
          var elapsed:Long = t2-t1
          duration = duration - elapsed
        }
      }
    }
  }
}
