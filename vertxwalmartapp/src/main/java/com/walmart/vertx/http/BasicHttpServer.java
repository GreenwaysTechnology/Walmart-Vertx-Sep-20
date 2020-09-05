package com.walmart.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class BasicHttpServer extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicHttpServer.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //HttpServer Configuration.
    //HttpServerOptions options=new HttpServerOptions().setPort(8081);

    //create HTTPServer instance
    //HttpServer server = vertx.createHttpServer(options);
    HttpServer server = vertx.createHttpServer(new HttpServerOptions().setPort(8081));
    //have requestListener - non blocking request handler
    server.requestHandler(request -> {
      //request.uri().equals("/api/message") && request.method().equals("GET")
      HttpServerResponse response = request.response();
      response.end("Hello Vertx Server");
    });

    //bind port with http channel
    server.listen(handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is Running  on  " + handler.result().actualPort());
      } else {
        System.out.println("Server is Down " + handler.cause().getMessage());
      }
    });


  }
}
