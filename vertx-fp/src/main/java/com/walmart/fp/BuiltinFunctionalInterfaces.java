package com.walmart.fp;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltinFunctionalInterfaces {
    public static void main(String[] args) {
        Consumer<String> consumer = null;
        consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("Test");
        consumer = s -> System.out.println(s);
        consumer.accept("Consumer");
        consumer = System.out::println;
        consumer.accept("Consumer");
        //consumer inside for...each
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(i -> System.out.println(i));
        list.forEach(System.out::println);

        //supplier: returns
        Supplier<String> supplier = null;
        supplier = () -> "Hello";
        System.out.println(supplier.get());

        Predicate<Integer> predicate = null;
        predicate = input -> input % 2 == 0;
        System.out.println(predicate.test(3) ? "Even" : "Odd");

        Function<String, String> function = value -> value;
        System.out.println(function.apply("Hello function"));

    }
}
