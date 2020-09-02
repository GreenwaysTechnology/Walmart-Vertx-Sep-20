package com.walmart.fp.syntax;

@FunctionalInterface
interface Welcome {
    void sayHello();
}

//parameters and args : multi parameters and args
@FunctionalInterface
interface Greeter {
    void sayHello(String message, String toWhom);
}

//single parameter
@FunctionalInterface
interface Setter {
    void setValue(String value);
}

//return type
@FunctionalInterface
interface Adder {
    int add(int a, int b);
}

public class FunctionalSyntax {
    public static void main(String[] args) {
        Welcome welcome = null;
        //() args list, -> arrow {funciton body}
        welcome = () -> {
            //function body
            System.out.println("Hello");
        };
        welcome.sayHello();
        //if function body has only one line of code : remove {}
        welcome = () -> System.out.println("Hello");
        welcome.sayHello();

        ///////////////////////////////
        //params
        Greeter greeter = null;
        greeter = (String message, String toWhom) -> {
            System.out.println(message + " " + toWhom);
        };
        //pass parameters
        greeter.sayHello("Hello", "Subramanian");
        //one line body
        greeter = (String message, String toWhom) -> System.out.println(message + " " + toWhom);
        greeter.sayHello("Hello", "Subramanian");

        //Type inference : in lambda implementation, you dont need to tell the type, because type
        //is understood by default.
        //one line body
        greeter = (message, toWhom) -> System.out.println(message + " " + toWhom);
        greeter.sayHello("Hello", "Subramanian");
        //single args
        Setter setter = null;
        //single parameter and args : remove ()
        setter = value -> System.out.println(value);
        setter.setValue("Hello");
        //Adder
        Adder adder = null;
        adder = (a, b) -> {
            int c = a + b;
            return c;
        };
        System.out.println(adder.add(1, 2));
        //body has no multi line only return : remove {} and return
        adder = (a, b) -> a + b;

        System.out.println(adder.add(1, 2));


    }
}
