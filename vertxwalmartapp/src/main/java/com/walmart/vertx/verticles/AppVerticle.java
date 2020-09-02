package com.walmart.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;

class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter Verticle");
    System.out.println(Thread.currentThread().getName());
  }
}

class WelcomeVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Welcome Verticle");
    System.out.println(Thread.currentThread().getName());

  }
}

public class AppVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(AppVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //we can deploy other verticles
    System.out.println("App Verticle begins");
    //deploy other verticles
    vertx.deployVerticle(new GreeterVerticle());
    vertx.deployVerticle(new WelcomeVerticle());
  }
}
