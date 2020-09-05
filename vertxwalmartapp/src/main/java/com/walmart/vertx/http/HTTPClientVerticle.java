package com.walmart.vertx.http;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class HTTPClientVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HTTPClientVerticle.class);
  }

  //Using HTTPClient
  private void fetchMessage() {

    HttpClient httpClient = vertx.createHttpClient();
    vertx.setTimer(1000, handler -> {
      httpClient.request(HttpMethod.GET, 8081, "localhost", "/", response -> {
        System.out.println(response.statusCode());
        response.bodyHandler(payload -> {
          System.out.println(payload.toString());
        });
      }).end();
    });
  }

  //communicate via webclient
  private void fetchMessageWithWebClient() {
    WebClient webClient = WebClient.create(vertx);
    vertx.setTimer(3000, handler -> {
      webClient
        .get(8081, "localhost", "/")
        .send(ar -> {
          if (ar.succeeded()) {
            //Obtain Response
            HttpResponse<Buffer> response = ar.result();
            System.out.println(response.bodyAsString());
          } else {
            System.out.println(ar.cause());
          }
        });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    fetchMessage();
    fetchMessageWithWebClient();
  }
}
