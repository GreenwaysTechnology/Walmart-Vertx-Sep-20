package com.walmart.vertx.blocking;

public class ThreadDemo {
  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getName());
    Thread thread = new Thread(() -> {
      System.out.println(Thread.currentThread().getName());
    });
    thread.setName("Event-loop Thread-0");
    thread.start();
  }
}
