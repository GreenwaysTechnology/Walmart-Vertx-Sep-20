package com.walmart.reactive.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ObservableDemo {
  public static void createStreamusingCreate() {
    //
    Observable<Integer> stream = Observable.create(observableEmitter -> {
      //send data
      observableEmitter.onNext(1);
      observableEmitter.onNext(2);
      observableEmitter.onNext(3);
      observableEmitter.onNext(4);
      observableEmitter.onComplete();
    });
    //process data
    stream.subscribe(data -> System.out.println(data), err -> System.out.println(err), () -> System.out.println("complete"));

  }

  public static void createStreamusingrange() {
    Observable<Integer> stream =
      Observable.range(1, 10)
        .map(i -> {
          System.out.println("Map method runs on " + Thread.currentThread().getName());
          return i * 2;
        })
        .subscribeOn(Schedulers.newThread())
        .filter(j -> {
          System.out.println("Filter method runs on " + Thread.currentThread().getName());
          return j % 2 == 0;
        }).doOnNext(System.out::println);
    //process data
    // stream.subscribe(data -> System.out.println(data), err -> System.out.println(err), () -> System.out.println("complete"));
    stream.blockingLast();

  }

  public static void backPressure() {
    Flowable<Integer> stream = Flowable.range(1, 10)
      .map(i -> {
        System.out.println("Map method runs on " + Thread.currentThread().getName());
        return i * 2;
      })
      .subscribeOn(Schedulers.newThread())
      .filter(j -> {
        System.out.println("Filter method runs on " + Thread.currentThread().getName());
        return j % 2 == 0;
      }).doOnNext(System.out::println);
    //stream.subscribe(data -> System.out.println(data), err -> System.out.println(err), () -> System.out.println("complete"));
    stream.blockingLast();
  }

  public static void main(String[] args) {
    // createStreamusingrange();
    backPressure();
//    try {
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }
}
