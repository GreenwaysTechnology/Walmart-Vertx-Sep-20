package com.walmart.vertx.ha;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

import java.lang.management.ManagementFactory;

public class Server extends AbstractVerticle {

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Launcher.main(new String[]{"run", Server.class.getName(), "-ha", "-instances=5"});
  }

  @Override
  public void start() throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      final String name = ManagementFactory.getRuntimeMXBean().getName();
      final String thread = Thread.currentThread().getName();
      System.out.println(name + thread);
      req.response().end("Happily served by " + name + thread + this.hashCode());
    }).listen(8080);
  }
}
