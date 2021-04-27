package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.*;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();
    AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager(){

    }

    public static StatisticAdvertisementManager getInstance() {
        return instance;
    }

    public List<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> result = new ArrayList<>();
        for (Advertisement advertisement : advertisementStorage.list()) {
            if (!isActive ^ advertisement.isActive()) {
                result.add(advertisement);
            }
        }
        return result;
    }
}
