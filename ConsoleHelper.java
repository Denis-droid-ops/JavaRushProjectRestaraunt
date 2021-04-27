package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException{
        return reader.readLine();
}

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();
        String dish="";
        writeMessage(Dish.allDishesToString());
        writeMessage("Enter the dish:");
        while (true){
            dish=readString();
            if (dish.equals("exit")) break;
            switch (dish){
                case "FISH":dishes.add(Dish.FISH); break;
                case "STEAK":dishes.add(Dish.STEAK); break;
                case "SOUP":dishes.add(Dish.SOUP);break;
                case "JUICE":dishes.add(Dish.JUICE);break;
                case "WATER":dishes.add(Dish.WATER); break;
                default: writeMessage("is not on the menu");
            }


        }
        return dishes;
    }


}
