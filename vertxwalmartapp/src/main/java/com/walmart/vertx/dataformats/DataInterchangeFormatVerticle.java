package com.walmart.vertx.dataformats;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class JSONVerticle extends AbstractVerticle {

  private void generateJson() {
    //Fluent Syntax :  builder pattern, command chain pattern
    JsonObject address = new JsonObject()
      .put("street", "10th street")
      .put("city", "Coimbatore")
      .put("state", "Tamil Nadu");

    JsonObject employeeJSON = new JsonObject();
    employeeJSON.put("id", 1);
    employeeJSON.put("name", "Subramanian");
    employeeJSON.put("status", true);
    employeeJSON.put("address", address);
    employeeJSON.put("contact", new JsonObject()
      .put("email", "demo@abc.com")
      .put("mobile", "9999999999")
      .put("linkedin", "somethingelse"));
    System.out.println(employeeJSON.getInteger("id"));
    System.out.println(employeeJSON.encodePrettily());
    //json array
    JsonArray list = new JsonArray();
    list.add(employeeJSON);
    list.add(employeeJSON);
    list.add(employeeJSON);
    System.out.println(list.encodePrettily());

  }

  @Override
  public void start() throws Exception {
    super.start();
    generateJson();
  }
}

class BufferVerticle extends AbstractVerticle {

  public void bufferDemo() {
    //Buffer
    Buffer buffer = Buffer.buffer();
    buffer.appendString("Hello");
    buffer.appendString("Hai");
    buffer.appendString("Welcome");
    buffer.appendString("How are you");
    buffer.appendString("this is binary data");
    System.out.println(buffer.length());
    System.out.println(buffer.toString());
  }

  @Override
  public void start() throws Exception {
    super.start();
    bufferDemo();
  }

}


public class DataInterchangeFormatVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(DataInterchangeFormatVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new BufferVerticle());
    vertx.deployVerticle(new JSONVerticle());
  }
}
