package com.walmart.vertx.rx.http;

import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;

public class HttpServerReactive extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpServerReactive.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    HttpServer server = vertx.createHttpServer();
//    server.requestHandler(res -> {
//      res.response().end("Hello");
//    });
//    server.requestStream().toFlowable().subscribe(req -> {
//      HttpServerResponse resp = req.response();
//      resp.end("Hello Reactive");
//    });
    //converting http inputstream into backpressured inputStream, so that , data flow can be controlled
    //so dont loose data. perfect streaming.
    server.requestStream().toFlowable().subscribe(req -> {
      req.response().putHeader("content-type", "application/json").end(new JsonObject().put("time", System.currentTimeMillis()).toString());
    });
    server.rxListen(8080).subscribe(data -> {
      System.out.println("Server is ready : " + data.actualPort());
    }, err -> {
      System.out.println("Server is down" + err);
    });
  }
}
