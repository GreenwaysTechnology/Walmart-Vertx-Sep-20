package com.walmart.fp;

interface Greeter {
    void sayGreet();
}

//implementations
class GreeterImpl implements Greeter {
    @Override
    public void sayGreet() {
        System.out.println("Hello");
    }
}


public class InterfacesInnerClasses {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = new GreeterImpl();
        greeter.sayGreet();

        //implementation via inner class : annonmous inner class
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Hello ");
            }
        };
        greeter.sayGreet();
        //functional implementation : lambda expression
        greeter = () -> {
            System.out.println("Hello ");
        };
        greeter.sayGreet();


    }
}
