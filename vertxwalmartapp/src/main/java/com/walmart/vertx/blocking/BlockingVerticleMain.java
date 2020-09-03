package com.walmart.vertx.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class EventLoopThreadVerticle extends AbstractVerticle {

  private void sayHello(Promise<String> promise) {
    System.out.println("Say Hello : " + Thread.currentThread().getName());
    try {
      Thread.sleep(4000);
      System.out.println("Wake Up read to send data to Non blocking Service");
      promise.complete("Hey this is blocking Result");
    } catch (InterruptedException es) {
      promise.fail("Something went wrong in blocking service");
    }
  }
  private void resultHandler(AsyncResult<String> ar) {
    System.out.println("Result Handler" + Thread.currentThread().getName());
    if (ar.succeeded()) {
      System.out.println("Blocking api Result goes Ready Here");
      System.out.println(ar.result());
    } else {
      System.out.println(ar.cause().getMessage());
    }
  }

  //send some blocking result to non blocking
  public void runBlockingAndNonBlocking() {
    vertx.executeBlocking(this::sayHello, this::resultHandler);

  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Event Loop Verticle  : " + Thread.currentThread().getName() + " - " + vertx.getOrCreateContext());
    runBlockingAndNonBlocking();
  }
}

class BlockingVerticle extends AbstractVerticle {
  //blocking api
  public void delay() {
    try {
      System.out.println("Sleeping");
      Thread.sleep(10000);
      System.out.println("Wake Up");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(" Worker Verticle  : " + Thread.currentThread().getName() + " " + vertx.getOrCreateContext());
    delay();

  }
}


public class BlockingVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(vertx.getOrCreateContext());
    //Deploment options
    DeploymentOptions options = new DeploymentOptions();
    options.setWorker(true);
    vertx.deployVerticle(new BlockingVerticle(), options);
    vertx.deployVerticle(new EventLoopThreadVerticle());
  }
}
