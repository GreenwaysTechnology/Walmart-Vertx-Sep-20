package com.walmart.vertx.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class PromiseVerticle extends AbstractVerticle {

  //empty response
  public Promise<Void> emptyPromise() {
    //create Future Object
    Promise<Void> promise = Promise.promise();
    //response simulation : empty
    promise.complete();//wraps response
    return promise;
  }
  //How to return only simple success response
  public Future<String> getSuccesResponse() {
    //create Future Object
    Promise<String> promise = Promise.promise();    //response simulation : empty
    promise.complete("Hello World");//wraps response
    return promise.future();
  }
  public Future<String> login() {
    //create Future Object
    Promise<String> promise = Promise.promise();
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
         promise.complete("Login success");
    } else {
      promise.fail("Login Failed");
    }

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    emptyPromise().future().onComplete(asy -> {
      if (asy.succeeded()) {
        System.out.println("Success");
      }
    });
    getSuccesResponse().onComplete(handler -> {
      if (handler.succeeded()) {
        //transfer result to any where
        System.out.println(handler.result());
      }
    });
    login()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }
}

public class PromiseMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(PromiseMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PromiseVerticle());
  }
}
