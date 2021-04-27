package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue queue;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){
        Order order = null;
        try {
            order = new Order(this);
            ConsoleHelper.writeMessage(order.toString());

            if(!order.isEmpty()) {
                AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime()*60);
                //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(advertisementManager.getBestList(),(long)advertisementManager.getBestSum(),(int)advertisementManager.getBestDuration()));

                queue.add(order);
                //setChanged();
                //notifyObservers(order);
                advertisementManager.processVideos();
            }

            return order;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;

        } catch (NoVideoAvailableException e){
            logger.log(Level.INFO, "No video is available for the order "+order);
            return null;
        }
    }

    public void createTestOrder(){
        TestOrder testOrder = null;
        try {
            testOrder = new TestOrder(this);
            ConsoleHelper.writeMessage(testOrder.toString());

            if(!testOrder.isEmpty()) {
                AdvertisementManager advertisementManager = new AdvertisementManager(testOrder.getTotalCookingTime()*60);
                //StatisticManager.getInstance().register(new VideoSelectedEventDataRow(advertisementManager.getBestList(),(long)advertisementManager.getBestSum(),(int)advertisementManager.getBestDuration()));


                queue.add(testOrder);
                advertisementManager.processVideos();
            }


        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");


        } catch (NoVideoAvailableException e){
            logger.log(Level.INFO, "No video is available for the order "+testOrder);

        }
    }

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Tablet{number="+number+"}";
    }
}
