package com.ashok

import com.ning.http.client.AsyncHttpClientConfig
import play.api.libs.ws.{DefaultWSClientConfig, WSClient, WSResponse}
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient}
import play.libs.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by ashok on 8/2/16.
  */
object HttpConnector {
  def getWithPlay(url:String) = {
    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build()
    val builder = new AsyncHttpClientConfig.Builder(config)
    val wsClient:WSClient = new NingWSClient(builder.build())
    /*
    val complexRequest: WSRequest =
      request.withHeaders("Accept" -> "application/json")
        .withRequestTimeout(10000.millis)
        .withQueryString("search" -> "play")
     */
    val futureResponse: Future[WSResponse] = wsClient.url(url).get()
    val result = Await.result(futureResponse, Duration.Inf)

    Json.parse(result.body)
    //Json.toJson(result.body)
  }
}
