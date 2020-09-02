package com.walmart.fp.methodreference;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Printer {
    void print(String value);
}

class MicroTask {
    public static void staticstartMicoTask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startMicoTask() {
        System.out.println(Thread.currentThread().getName());
    }
}


class Task {
    private void startMicoTask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void start() {
        //Thread
        Thread thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();
        Runnable runner = () -> System.out.println(Thread.currentThread().getName());
        thread = new Thread(runner);
        thread.start();
        thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();
        //isloate runnbale logic outside
        thread = new Thread(() -> this.startMicoTask());
        thread.start();
        //method reference : instance method,static method  ::
        thread = new Thread(this::startMicoTask);
        thread.start();
        //outside class
        MicroTask microTask = new MicroTask();
        thread = new Thread(() -> microTask.startMicoTask());
        thread.start();
        thread = new Thread(() -> new MicroTask().startMicoTask());
        thread.start();
        thread = new Thread(microTask::startMicoTask);
        thread.start();
        thread = new Thread(new MicroTask()::startMicoTask);
        thread.start();
        thread = new Thread(MicroTask::staticstartMicoTask);
        thread.start();
    }
}

public class MethodReference {
    public static void main(String[] args) {
        Task task = new Task();
        task.start();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(i -> System.out.println(i));
        list.forEach(System.out::println);

        Printer printer = null;
        printer = (value) -> {
            System.out.println(value);
        };
        printer.print("Hello");
        printer = value -> System.out.println(value);
        printer.print("Hello");

        printer = System.out::println;
        printer.print("Hello");



    }
}
