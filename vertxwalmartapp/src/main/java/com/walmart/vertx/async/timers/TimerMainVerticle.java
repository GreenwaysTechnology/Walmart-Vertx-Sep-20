package com.walmart.vertx.async.timers;

import io.vertx.core.*;
import io.vertx.example.util.Runner;

import java.util.Date;

class TimerVerticle extends AbstractVerticle {

  //timer
  public void delay() {
    vertx.setTimer(1000, ar -> {
      System.out.println("Timeout");
    });
  }

  public Future<String> delayAndSend() {
    Promise promise = Promise.promise();
    vertx.setTimer(2000, ar -> {
      //send data after 2sc later
      promise.complete("I am delyead Result");
    });
    return promise.future();
  }

  public void heartBeat(Handler<AsyncResult<String>> asyncResultHandler) {
    //  the unique ID of the timer : for cancelling timer
    long timerId = vertx.setPeriodic(1000, ar -> {
      asyncResultHandler.handle(Future.succeededFuture(new Date().toInstant().toString()));
    });
    //i want to cancel it after .5 min
    vertx.setTimer(5000, ar -> {
      System.out.println("Going to Stop Timer");
      vertx.cancelTimer(timerId);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Blocking start");
    delay();
    delayAndSend().onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause());
      }
    });
    //function as parameter: callback pattern
    //callback function is called for every event emision from the kernal
    heartBeat(ar -> {
      System.out.println(ar.result());
    });
    System.out.println("Blocking end");
  }
}

public class TimerMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimerMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new TimerVerticle());
  }
}
