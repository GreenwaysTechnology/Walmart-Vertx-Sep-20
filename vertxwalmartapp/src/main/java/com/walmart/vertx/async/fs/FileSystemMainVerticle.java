package com.walmart.vertx.async.fs;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;

class FileSystemVerticle extends AbstractVerticle {
  String path = "assets/info.txt";

  public Future<String> readFileAsync() {
    //read file async
    FileSystem fileSystem = vertx.fileSystem();
    Promise promise = Promise.promise();
    String path = "assets/info.txt";
    fileSystem.readFile(path, fileHandler -> {
      if (fileHandler.succeeded()) {
        //System.out.println(fileHandler.result().toString());
        promise.complete(fileHandler.result().toString());
      } else {
        //System.out.println(fileHandler.cause().getMessage());
        promise.fail(fileHandler.cause().getMessage());
      }
    });
    return promise.future();

  }

  public void readFileAsyncCallback(Handler<AsyncResult<String>> asyncResultHandler) {
    //read file async
    FileSystem fileSystem = vertx.fileSystem();
    fileSystem.readFile(path, fileHandler -> {
      if (fileHandler.succeeded()) {
        //System.out.println(fileHandler.result().toString());
        asyncResultHandler.handle(Future.succeededFuture(fileHandler.result().toString()));
      } else {
        //System.out.println(fileHandler.cause().getMessage());
        asyncResultHandler.handle(Future.failedFuture(fileHandler.cause().getMessage()));
      }
    });

  }

  public void readFileSync() {
    FileSystem fileSystem = vertx.fileSystem();
    //blocking
    System.out.println("Read File Sync");
    Buffer buffer = fileSystem.readFileBlocking(path);
    System.out.println(buffer);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    readFileSync();
    readFileAsync().onSuccess(System.out::println);
    readFileAsyncCallback(fileHandler -> {
      System.out.println(fileHandler.result());
    });
    System.out.println("end");
  }
}


public class FileSystemMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FileSystemMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FileSystemVerticle());
  }
}
