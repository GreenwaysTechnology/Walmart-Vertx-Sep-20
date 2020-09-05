package com.walmart.vertx.rx.eventbus;

import io.reactivex.disposables.Disposable;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;

class Receiver extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    EventBus eb = vertx.eventBus();

    eb.consumer("news.reactive")
      .toFlowable()
      .subscribe(message -> {
        System.out.println("Received " + message.body());
        message.reply("PONG");
      });
//    eb.consumer("news.reactive").toObservable().subscribe(message -> {
//      System.out.println("Received " + message.body());
//      message.reply("PONG");
//    });
    eb.consumer("news-feed")
      .toFlowable()
      .subscribe(message -> System.out.println("Sub 1 News: " + message.body()));

    Disposable disposable = eb.consumer("news-feed")
      .toFlowable()
      .doOnSubscribe(s -> System.out.println("joined"))
      .subscribe(message -> System.out.println("Sub 2 news: " + message.body()));

    vertx.setTimer(5000, ar -> {
      System.out.println("Sub 2 left");
      disposable.dispose();
    });
  }
}

class Sender extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    vertx.setPeriodic(1000, ar -> {
      EventBus eventBus = vertx.eventBus();
      eventBus.send("news.reactive", "Hello Reactive");
      eventBus.publish("news-feed", "Some news!");
    });

  }
}


public class ReactiveEventBus extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ReactiveEventBus.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }
}
