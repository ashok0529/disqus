package com

import org.mongodb.scala._

/**
  * Created by ashok on 8/4/2016.
  */
package object ashok {
  val API_KEY="2XH0ECZ3XiObj724Ita6yTydSsF0jbJyCeP8yHYfwj7c6jWcD0jBaaDhKI19FX3p"
  val API_SECRET="nvf7HHACyAdaDKw5Pza5RPtZMZtoM1SffOvKXP49Ckme6re0bxDvoGPCDfmjAwAy"
  val auth_url = "https://disqus.com/api/oauth/2.0/authorize"
  val scope = "read,write"
  val redirect_url = "http://www.yahoo.com"
  val list_threads_url = s"https://disqus.com/api/3.0/trends/listThreads.json?api_key=$API_KEY"
  val LIST_POSTS_URL = "https://disqus.com/api/3.0/posts/list.json?api_key="
  val auth_url_final = s"$auth_url?client_id=$API_KEY&scope=read,write&response_type=code&redirect_uri=$redirect_url/oauth_redict"


  val MONGO_HOST:String = "mongomaster"
}
