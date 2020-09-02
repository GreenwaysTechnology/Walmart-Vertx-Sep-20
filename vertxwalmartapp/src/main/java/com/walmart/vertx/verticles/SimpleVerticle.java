package com.walmart.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;

public class SimpleVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    System.out.println("Verticle starts");
    //create vertx instance
//    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new SimpleVerticle());
    Runner.runExample(SimpleVerticle.class);
  }

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    System.out.println("init method");
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start method");
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("stop method");
  }
}
