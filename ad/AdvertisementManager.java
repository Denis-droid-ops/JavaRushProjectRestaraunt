package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class AdvertisementManager {
    private int timeSeconds;
    final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private List<Advertisement> bestList = null;
    private double bestSum;
    private long bestDuration = Integer.MAX_VALUE;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos(){
        if(storage.videos.isEmpty()) {
           throw new NoVideoAvailableException();
           }
        List<Advertisement> advertisements = storage.list().stream()
                .filter(advertisement -> advertisement.getHits() > 0)
                .collect(Collectors.toList());
        Collections.reverse(advertisements);
       makeAllSets(advertisements);
       Collections.sort(bestList, new Comparator<Advertisement>() {
           @Override
           public int compare(Advertisement o1, Advertisement o2) {
               return (int)(o2.getAmountPerOneDisplaying()-o1.getAmountPerOneDisplaying());
           }
       }.thenComparing(new Comparator<Advertisement>() {
           @Override
           public int compare(Advertisement o1, Advertisement o2) {
               return (int)(o1.getAmountPerOneDisplaying()*1000/o1.getDuration()-o2.getAmountPerOneDisplaying()*1000/o2.getDuration());
           }
       }));
        for (Advertisement ad:bestList) {
            ConsoleHelper.writeMessage(ad.toString());
            ad.revalidate();


        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestList,(long)bestSum,(int)bestDuration));
        }

    private long calcTime(List<Advertisement> list){
        long timeSum = 0;
        for(Advertisement ad:list){
            timeSum= timeSum +ad.getDuration();
        }
        return timeSum;
    }

    private long calcSum(List<Advertisement> list){
        long sumSum = 0;
        for(Advertisement ad:list){
            sumSum = sumSum +ad.getAmountPerOneDisplaying();
        }
        return sumSum;
    }

    private void checkSet(List<Advertisement> list){
        long newDuration = calcTime(list);
        long newAmount = calcSum(list);

        if (bestList == null && newDuration <= timeSeconds) {
            bestList = list;
            bestSum = newAmount;
            bestDuration = newDuration;

        } else {
            if (newDuration <= timeSeconds) {

                if (newAmount > bestSum) {
                    bestList = list;
                    bestSum = newAmount;
                    bestDuration = newDuration;
                }

                if (newAmount == bestSum) {
                    if (newDuration > bestDuration) {
                        bestList = list;
                        bestSum = newAmount;
                        bestDuration = newDuration;
                    }

                    if (newDuration == bestDuration && list.size() < bestList.size()) {
                        bestList = list;
                        bestSum = newAmount;
                        bestDuration = newDuration;
                    }
                }

            }
        }
    }



    //создание всех наборов перестановок значений
    public void makeAllSets(List<Advertisement> list)
    {
        if (list.size()>0)
            checkSet(list);

        for (int i = 0; i < list.size(); i++)
        {
            List<Advertisement> newSet = new ArrayList<Advertisement>(list);

            newSet.remove(i);

            makeAllSets(newSet);
        }

    }

    public List<Advertisement> getBestList() {
        return bestList;
    }

    public double getBestSum() {
        return bestSum;
    }

    public long getBestDuration() {
        return bestDuration;
    }
}
