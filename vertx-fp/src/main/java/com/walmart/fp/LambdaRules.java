package com.walmart.fp;

@FunctionalInterface
interface Welcome {
    public static void doSomething() {
        System.out.println("do something");
    }

    void sayHello();

   // void sayHai(); //error , not allowed more than one abs method
    public default void saySomething() {
        System.out.println("SOmething ....");
    }
}

//when error is dected that lambda rule viloation

@FunctionalInterface
interface Myinterface{
    void sayHai();
}


public class LambdaRules {
    public static void main(String[] args) {
        Welcome welcome = null;
        welcome = () -> {
            System.out.println("Hello");
        };
        welcome.sayHello();
        welcome.saySomething();
        Welcome.doSomething();

    }
}
