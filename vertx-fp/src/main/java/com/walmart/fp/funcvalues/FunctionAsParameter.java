package com.walmart.fp.funcvalues;

//how to pass funciton as parameter

@FunctionalInterface
interface Handler {
    void handle();
}

@FunctionalInterface
interface HttpHandler {
    boolean write(Object data);
}

class SocketHandler {
    public void handleRequest(Handler handler) {
        //call handler implementation
        handler.handle();
    }
}

class HttpServer {
    public void requestHandler(HttpHandler handler) {
        boolean status = handler.write("Hello World");
        if (status) {
            System.out.println("HTTP response committed");
        } else {
            System.out.println("HTTP ERROR");
        }
    }
}

//passing handler implementtion to handleRequest method of Socket Handler
class SocketImpl implements Handler {
    @Override
    public void handle() {
        System.out.println("Socket Handler");
    }
}

public class FunctionAsParameter {
    public static void main(String[] args) {
        SocketHandler socketHandler = null;
        socketHandler = new SocketHandler();
        socketHandler.handleRequest(new SocketImpl());

        //inner classes
        socketHandler.handleRequest(new Handler() {
            @Override
            public void handle() {
                System.out.println("Socket Handler via inner classes");
            }
        });
        //lambda : funciton as parameter
        Handler handler = () -> System.out.println("Handler via lambda ");
        socketHandler.handleRequest(handler);
        //lambda inline
        socketHandler.handleRequest(() -> System.out.println("Handler via inline lambda"));
        ////////////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = null;
        httpServer = new HttpServer();

        HttpHandler httpHandler = data -> {
            System.out.println(data);
            return true;
        };
        httpServer.requestHandler(httpHandler);
        //
        httpServer.requestHandler(data -> {
            System.out.println(data);
            return true;
        });


    }
}
