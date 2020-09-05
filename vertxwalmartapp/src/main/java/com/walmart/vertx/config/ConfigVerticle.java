package com.walmart.vertx.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class ConfigVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ConfigVerticle.class);
  }
  public void gitjsonStore() {
    ConfigStoreOptions git = new ConfigStoreOptions()
      .setType("git")
      .setConfig(new JsonObject()
        .put("url", "https://github.com/cescoffier/vertx-config-test.git")
        .put("path", "local")
        .put("filesets",
          new JsonArray().add(new JsonObject().put("pattern", "*.json"))));
    ConfigRetriever retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(git));

    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.encodePrettily());

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });
  }

  public void configViaConfigReteriver() {
    //Add Storage options: type, format,file path
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    //file path
    options.setConfig(new JsonObject().put("path", "conf/config.json"));

    ConfigRetriever retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(options));

    //read config
    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getString("message"));

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });

  }

  public void configViaAbstractVerticle() {
    JsonObject jsonConfig = config();
    String message = jsonConfig.getString("message", "defaultMessage");
    Integer port = jsonConfig.getInteger("port", 8081);
    System.out.println(message + " " + port);
  }


  @Override
  public void start() throws Exception {
    super.start();
    // configViaAbstractVerticle();
    //configViaConfigReteriver();
    gitjsonStore();
  }
}

