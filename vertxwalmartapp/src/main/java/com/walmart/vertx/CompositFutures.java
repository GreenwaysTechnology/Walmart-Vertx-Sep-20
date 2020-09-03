package com.walmart.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.example.util.Runner;

import java.util.Arrays;
import java.util.List;

public class CompositFutures extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CompositFutures.class);
  }

  public void all() {
    //While the all composition waits until all futures are successful (or one fails)
    List<Future> futures = Arrays.asList(
      Future.succeededFuture("Ok"),
      Future.failedFuture("Bam"),
      Future.succeededFuture("Great")
    );
    CompositeFuture.all(futures).map(v -> {
      return v;
    }).onSuccess(value -> {
      System.out.println(value.size());
      List<String> list = value.list();
      list.forEach(System.out::println);
    }).onFailure(System.out::println);
  }

  public void any() {
    //the any composition waits for the first succeeded future. CompositeFuture.any
    // takes several futures arguments (up to 6) and returns a future that is succeeded
    // when one of the futures is, and failed when all the futures are failed:
    List<Future> futures = Arrays.asList(
      Future.succeededFuture("Ok"),
      Future.failedFuture("Bam"),
      Future.succeededFuture("Great")
    );
    CompositeFuture.any(futures).map(v -> {
      return v;
    }).onSuccess(value -> {
      System.out.println(value.size());
      List<String> list = value.list();
      list.forEach(System.out::println);
    }).onFailure(System.out::println);
  }

  public void join() {
    List<Future> futures = Arrays.asList(
      Future.succeededFuture("Ok"),
      Future.failedFuture("Bam"),
      Future.succeededFuture("Great")
    );
    CompositeFuture.join(futures).map(v -> {
      return v;
    }).onSuccess(value -> {
      System.out.println(value.size());
      List<String> list = value.list();
      list.forEach(System.out::println);
    }).onFailure(System.out::println);
  }

  @Override
  public void start() throws Exception {
    super.start();
    // all();
    //any();
    join();

  }
}
