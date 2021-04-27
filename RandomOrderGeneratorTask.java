package com.javarush.task.task27.task2712;

import java.util.ArrayList;
import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    List<Tablet> list;
    int interval;
    public RandomOrderGeneratorTask(List<Tablet> list, int ORDER_CREATING_INTERVAL) {
       this.list = list;
       this.interval = ORDER_CREATING_INTERVAL;
    }

    @Override
    public void run() {
        Tablet tablet = list.get((int)Math.random()*(list.size()-1));
        try {
            while (true) {
                Thread.sleep(interval);
                tablet.createTestOrder();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
