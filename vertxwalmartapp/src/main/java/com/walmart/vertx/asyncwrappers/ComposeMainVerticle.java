package com.walmart.vertx.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class ComposeVerticle extends AbstractVerticle {
  public Future<String> prepareDatabase() {
    System.out.println("Start DATABASE Server");
    Promise promise = Promise.promise();
    promise.complete("Redis Server");
    return promise.future();
  }

  public Future<String> startHttpServer() {
    System.out.println("Start HTTP Server");
    Promise promise = Promise.promise();
    //promise.fail("Http Server failed");
    promise.complete("Http Server ");
    return promise.future();
  }

  public Future<String> startWebContainer() {
    System.out.println("Start Web Container");

    Promise promise = Promise.promise();
    promise.complete("Netty Container");
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    //callback Hell code Hell
    prepareDatabase().onComplete(dbar -> {
      //if database success,start http server
      if (dbar.succeeded()) {
        startHttpServer().onComplete(httpar -> {
          //if http success, start web container
          if (httpar.succeeded()) {
            startWebContainer().onComplete(webctar -> {
              if (webctar.succeeded()) {
                System.out.println("System is Up!!");
              } else {
                System.out.println("System is down!!!");
              }
            });
          }
        });
      } else {
        System.out.println(dbar.cause().getMessage());
      }
    });
    //soultion to callback hell using compose method.
    prepareDatabase()
      .compose(httpserver -> {
        return startHttpServer(); //Future
      })
      .compose(wbcontainer -> {
        return startWebContainer();
      }).onComplete(webctar -> {
      if (webctar.succeeded()) {
        System.out.println("System is Up!!");
      } else {
        System.out.println("System is down!!!");
      }
    });
    prepareDatabase()
      .compose(httpserver -> startHttpServer())
      .compose(wbcontainer -> startWebContainer()
      ).onComplete(webctar -> {
      if (webctar.succeeded()) {
        System.out.println("System is Up!!");
      } else {
        System.out.println("System is down!!!");
      }
    });

  }
}


public class ComposeMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ComposeMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ComposeVerticle());
  }
}
