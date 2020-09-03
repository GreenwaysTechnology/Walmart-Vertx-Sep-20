package com.walmart.vertx.asyncwrappers;

import io.vertx.core.*;
import io.vertx.example.util.Runner;

class FutureVerticle extends AbstractVerticle {

  public void doSomething(String result) {
    System.out.println("Do something" + result);
  }

  public void doSomethingError(Throwable error) {
    System.out.println("Do something Error " + error);
  }

  //empty response
  public Future<Void> emptyFuture() {
    //create Future Object
    Future<Void> future = Future.future();
    //response simulation : empty
    future.complete();//wraps response
    return future;
  }

  //How to return only simple success response
  public Future<String> getSuccesResponse() {
    //create Future Object
    Future<String> future = Future.future();
    //response simulation : empty
    future.complete("Hello World");//wraps response
    return future;
  }

  //error payload
  public Future<String> getErrorResponse() {
    //create Future Object
    Future<String> future = Future.future();
    //response simulation : empty
    future.fail("Something went wrong!!!");//wraps response
    return future;
  }

  //how to send and error based on biz logic
  public Future<String> login() {
    //create Future Object
    Future<String> future = Future.future();

    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      future.complete("Login success");
    } else {
      future.fail("Login Failed");
    }

    return future;
  }

  //future returns with static factory methods
  public Future<String> factoryFuture() {
    //Future.failedFuture("eee")
    return Future.succeededFuture("welcome to Vertx");
  }

  public Future<String> loginUsingFactory() {
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      return Future.succeededFuture("Login success");
    }
    return Future.failedFuture("Login Failed");

  }


  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Future object");
    //handle future - get result or error
    if (emptyFuture().succeeded()) {
      System.out.println("Future is success");
    }
    //handle success response.
    getSuccesResponse().setHandler(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> event) {
        if (event.succeeded()) {
          System.out.println(event.result());
        }
      }
    });
    getSuccesResponse().setHandler(handler -> {
      if (handler.succeeded()) {
        System.out.println(handler.result());
      }
    });
    //deprecation replacment of setHandler
    getSuccesResponse().onComplete(handler -> {
      if (handler.succeeded()) {
        //transfer result to any where
        System.out.println(handler.result());
        doSomething(handler.result());
      }
    });
    //simple handlers handle only results
    getSuccesResponse().onSuccess(result -> {
      System.out.println(result);
    });
    //
    getErrorResponse().onComplete(handler -> {
      if (handler.failed()) {
        System.out.println(handler.cause().getMessage());
      }
    });
    //login
    login().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });

    login()
      .onSuccess(res -> System.out.println(res))
      .onFailure(err -> System.out.println(err));

    login()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    login()
      .onSuccess(this::doSomething)
      .onFailure(this::doSomethingError);

    System.out.println("Login factory");
    loginUsingFactory()
      .onSuccess(this::doSomething)
      .onFailure(this::doSomethingError);
  }
}


public class FutureVerticleDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FutureVerticleDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FutureVerticle());
  }
}
