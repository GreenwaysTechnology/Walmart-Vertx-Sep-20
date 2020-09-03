package com.walmart.vertx.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

/**
 * getuser function returns user to login function.
 * getuser---->login
 * |
 * if data is available goto login
 * else throw error
 */

class UserVerticle extends AbstractVerticle {

  public Future<String> getUser() {
    Promise<String> promise = Promise.promise();
    //fake/mock user data
    String userName = "admin";
    if (userName != null) {
      promise.complete(userName);
    } else {
      promise.fail("User not Found");
    }

    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise<String> promise = Promise.promise();
    //fake/mock user data
    String status = "";
    if (userName.equals("admin")) {
      status = "Login Success";
      promise.complete(status);
    } else {
      status = "Login failed";
      promise.fail(status);
    }

    return promise.future();
  }

  public Future<String> showPage(String status) {
    Promise<String> promise = Promise.promise();
    //fake/mock user data
    String page = "";
    if (status.equals("Login Success")) {
      page = "Premium Page";
      promise.complete(page);
    } else {
      page = "Guest Page";
      promise.fail(page);
    }

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getUser().onComplete(handler -> {
      if (handler.succeeded()) {
        System.out.println("Get User called");
        //nesting callbacks
        login(handler.result()).onComplete(loginhandler -> {
          if (loginhandler.succeeded()) {
            System.out.println("Login is called");
            //call show Page
            showPage(loginhandler.result()).onComplete(pageHandler -> {
              if (pageHandler.succeeded()) {
                System.out.println(pageHandler.result());
              } else {
                System.out.println(pageHandler.cause());
              }
            });

          } else {
            System.out.println(loginhandler.cause().getMessage());
          }
        });
      } else {
        System.out.println(handler.cause());
      }
    });
    //
    getUser()
      .compose(userhr -> login(userhr))
      .compose(page -> showPage(page))
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    getUser()
      .compose(this::login)
      .compose(this::showPage)
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

  }
}

public class CallbackNestingVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackNestingVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserVerticle());
  }
}
