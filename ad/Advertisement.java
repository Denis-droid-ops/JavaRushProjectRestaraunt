package com.javarush.task.task27.task2712.ad;

import java.text.DecimalFormat;

public class Advertisement {
    private long amountPerOneDisplaying;
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        if(hits!=0) amountPerOneDisplaying = initialAmount/hits;
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }

    public void revalidate(){
        if(hits<=0) throw new UnsupportedOperationException();
        hits--;
    }

    @Override
    public String toString() {
        return String.format("%s is displaying... %d, %d", name, amountPerOneDisplaying, amountPerOneDisplaying * 1000 / duration);
    }

    public boolean isActive() {
        return hits > 0;
    }
}
