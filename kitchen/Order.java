package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();

    }

    protected void initDishes() throws IOException{
        dishes = dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime(){
        int totalCookingTime=0;
        for(Dish d:dishes){
            totalCookingTime=totalCookingTime+d.getDuration();
        }
        return totalCookingTime;
    }

    public boolean isEmpty(){
        if(getTotalCookingTime()==0) return true;
        else return false;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    @Override
    public String toString() {
        return "Your order: "+ dishes.toString()+" of "+tablet.toString()+", cooking time "+getTotalCookingTime()+"min";

    }
}
