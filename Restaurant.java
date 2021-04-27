package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
 private final static int ORDER_CREATING_INTERVAL = 100;
 private final static LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>(200);
   public static void main(String args[]){
    List<Tablet> tablets = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
     tablets.add(new Tablet(i + 1));
    }
    for (Tablet tablet :
            tablets) {
     tablet.setQueue(ORDER_QUEUE);
    }
    Waiter waiter1 = new Waiter();
    Waiter waiter2 = new Waiter();

    Cook cook1 = new Cook("Amigo");
    cook1.addObserver(waiter1);
    cook1.setQueue(ORDER_QUEUE);
    Cook cook2 = new Cook("Diego");
    cook2.addObserver(waiter2);
    cook2.setQueue(ORDER_QUEUE);

    //StatisticManager.getInstance().register(cook1);
    //StatisticManager.getInstance().register(cook2);
     Thread thread1 = new Thread(cook1);
     Thread thread2 = new Thread(cook2);
     thread1.start();
     thread2.start();




    Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
    thread.start();

    try {
     Thread.sleep(1000);
     thread.interrupt();
     thread.join();
     Thread.sleep(1000);
    } catch (InterruptedException ignore) {
    }

    DirectorTablet directorTablet = new DirectorTablet();
    directorTablet.printAdvertisementProfit();
    directorTablet.printCookWorkloading();
    directorTablet.printActiveVideoSet();
    directorTablet.printArchivedVideoSet();
   }


}
