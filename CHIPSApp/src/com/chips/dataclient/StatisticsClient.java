package com.chips.dataclient;

import com.chips.xmlhandler.StatisticsHandler;

public class StatisticsClient extends XMLDataClient {
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_WEIGHT = 110;

    @Override
    protected void reloadData() {
        StatisticsHandler handler = new StatisticsHandler();
        parse(handler);
        
        height = handler.getHeight();
        weight = handler.getWeight();
    }

    public int getHeight() {
        return height;
    }
    
    public int getWeight() {
        return weight;
    }

    private int height = DEFAULT_HEIGHT;
    private int weight = DEFAULT_WEIGHT;
}
