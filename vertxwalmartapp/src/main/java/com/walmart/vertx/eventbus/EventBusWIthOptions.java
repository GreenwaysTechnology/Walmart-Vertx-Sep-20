package com.walmart.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;

class Reciver extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
//    EventBus eb = vertx.eventBus();
//
//    eb.consumer("ping-address", message -> {
//
//      System.out.println("Received message: " + message.body());
//      // Now send back reply
//      message.reply("pong!");
//    });
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer("ping-address");
    //handle /process the message/news
    // messageConsumer.setMaxBufferedMessages(5000);
    System.out.println(messageConsumer.getMaxBufferedMessages());
    messageConsumer.handler(message -> {
      System.out.println(message.headers());
      System.out.println(message.replyAddress());
      System.out.println("Request -  : " + message.body());
      message.reply("pong!");
    });
    System.out.println("Receiver ready!");
  }
}

class Sender extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    // EventBus eb = vertx.eventBus();

    // Send a message every second

    DeliveryOptions deliveryOptions = new DeliveryOptions();
    deliveryOptions.setSendTimeout(100);
    deliveryOptions.addHeader("msg", "header");

    vertx.eventBus().request("ping-address", "ping!", deliveryOptions, reply -> {
      if (reply.succeeded()) {
        System.out.println("Received reply " + reply.result().body());
      } else {
        System.out.println("No reply");
      }
    });

  }
}

public class EventBusWIthOptions extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(EventBusWIthOptions.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Reciver());
  }
}
