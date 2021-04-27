package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private boolean busy;
    private String name;
    private LinkedBlockingQueue queue;
    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order){
        busy = true;
        try {
            Thread.sleep(order.getTotalCookingTime()*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConsoleHelper.writeMessage("Start cooking - "+order);
        setChanged();
        notifyObservers(order);
        CookedOrderEventDataRow row = new CookedOrderEventDataRow(order.getTablet().toString(),name,order.getTotalCookingTime()*60,order.getDishes());
        StatisticManager.getInstance().register(row);
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                if (!queue.isEmpty()) {
                    if (!this.isBusy()) {
                        startCookingOrder((Order)queue.take());
                    }
                }
            }
        } catch (InterruptedException e) {

        }
    }
}

