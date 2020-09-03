package com.walmart.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;

//////////////////////////////////////////////////////////////////////////////////////////////////////
//request and reply ; with
class LabVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer("report.in.covid");
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
      //sending reply /ack
      news.reply("Patient is Crictal, Need More attention");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}


/////////////////////////////////////////////////////////////////////////////////////////////////

///point-point
class CenertalFinanceVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer("fund.in.covid.request");
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class CenertalFinanceVerticle1 extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer("fund.in.covid.request");
    messageConsumer.handler(news -> {
      System.out.println("Request 1 -  : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
//pub-sub

//Consumer Verticles
class NewsSevenVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer("news.in.covid");
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("News Seven  - Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class BBCVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer("news.in.covid");
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("BBC  - Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class NDTVVerticle extends AbstractVerticle {

  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer("news.in.covid");
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("NDTV  - Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

//publisher Verticle
class NewsPublisherVerticle extends AbstractVerticle {

  public void publishNews() {
    System.out.println("News started....");
    vertx.setTimer(5000, ar -> {
      System.out.println("Message is being published...");
      String message = "Last 24 Hrs,new 50000 covid patients in India";
      //broadcasting : publish method
      vertx.eventBus().publish("news.in.covid", message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    publishNews();

  }
}

////////////////////////////////////////////////////////////////////////////////////
//point to point publisher
//point to point publisher
class FinanceRequestVerticle extends AbstractVerticle {

  public void requestFinance() {
    //
    System.out.println("Finance Request started....");
    vertx.setTimer(5000, ar -> {
      //point to point : send method
      String message = "Dear Team, We request that we want 1 Billion Money for Covid";
      vertx.eventBus().send("fund.in.covid.request", message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestFinance();
  }
}
//request-reply

class ReportVerticle extends AbstractVerticle {

  public void sendReport() {
    vertx.setTimer(5000, ar -> {
      String message = "Report of Mr.x";
      //request- req-reply with ack
      vertx.eventBus().request("report.in.covid", message, asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result().body());
        } else {
          System.out.println(asyncResult.cause());
        }
      });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    sendReport();
  }
}


public class EventBusVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusVerticleMain.class);
  }

  private void pubsub() {
    vertx.deployVerticle(new NewsPublisherVerticle());
    vertx.deployVerticle(new NewsSevenVerticle());
    vertx.deployVerticle(new BBCVerticle());
    vertx.deployVerticle(new NDTVVerticle());
  }

  public void poinToPoint() {
    vertx.deployVerticle(new FinanceRequestVerticle());
    vertx.deployVerticle(new CenertalFinanceVerticle());
    vertx.deployVerticle(new CenertalFinanceVerticle1());
  }

  public void requestReply() {
    vertx.deployVerticle(new ReportVerticle());
    vertx.setTimer(8000, ar -> {
      System.out.println("lab verticle");
      vertx.deployVerticle(new LabVerticle());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //pubsub();
    ///poinToPoint();
    requestReply();

  }
}
