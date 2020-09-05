package com.walmart.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;


class ProductsVerticle extends AbstractVerticle {

  public Router getProductsConfig() {
    Router router = Router.router(vertx);
    router.get("/list").handler(routingContext -> {
      routingContext.response().end("Products");
    });
    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();

  }
}

class UsersVerticle extends AbstractVerticle {

  public Router getUsersConfig() {
    Router router = Router.router(vertx);
    router.get("/list").handler(routingContext -> {
      routingContext.response().end("Users");
    });
    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();

  }
}

class OrdersVerticle extends AbstractVerticle {
  public Router getOrdersConfig() {
    Router router = Router.router(vertx);

    router.get("/list").handler(routingContext -> {
      routingContext.response().end("Orders");
    });
    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();

  }
}


class GreeterHandlers {
  public static void helloHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    response.end("Hello");
  }

  public static void haiHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    response.end("Hai");
  }

  public static void greeterHandler(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    response.end("Greet");
  }
}

public class BasicRouters extends AbstractVerticle {
  //1.server creation
  HttpServer httpServer;
  //create Router, inject router with requ
  Router router;
  //Users
  UsersVerticle usersVerticle = new UsersVerticle();

  //Orders
  OrdersVerticle ordersVerticle = new OrdersVerticle();

  //Products
  ProductsVerticle productsVerticle = new ProductsVerticle();

  public static void main(String[] args) {
    Runner.runExample(BasicRouters.class);
  }


  public void buildGreeterEndPoint() {
    //end points
    // router.get("/api/hello").handler((routingContext) -> GreeterHandlers.helloHandler(routingContext));
    router.get("/api/hello").handler(GreeterHandlers::helloHandler);
    router.get("/api/hai").handler(GreeterHandlers::haiHandler);
    router.get("/api/greet").handler(GreeterHandlers::greeterHandler);
    router.get("/api/saysomething/:name").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      HttpServerRequest request = routingContext.request();
      String name = request.getParam("name");
      response.end("Hello " + name);
    });

    router.mountSubRouter("/api/users", usersVerticle.getUsersConfig());
    router.mountSubRouter("/api/orders", ordersVerticle.getOrdersConfig());
    router.mountSubRouter("/api/products", productsVerticle.getProductsConfig());


    //2.request handling
    //inject router with request Handler
    httpServer.requestHandler(router);


    //3.server starting
    httpServer.listen(handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is Running  on  " + handler.result().actualPort());
      } else {
        System.out.println("Server is Down " + handler.cause().getMessage());
      }
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(ordersVerticle);
    vertx.deployVerticle(productsVerticle);
    vertx.deployVerticle(usersVerticle);
    httpServer = vertx.createHttpServer(new HttpServerOptions().setPort(3000));
    router = Router.router(vertx);
    buildGreeterEndPoint();
  }
}
