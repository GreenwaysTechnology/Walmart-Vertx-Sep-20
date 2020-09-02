package com.walmart.vertx;

import io.vertx.core.Vertx;

public class CreateVertInstanceViaFactory {
  public static void main(String[] args) {
    //create vertx instance via factory
    //vertx container,engine,runtime...
    Vertx vertx = Vertx.vertx();
    System.out.println(vertx);
  }
}
