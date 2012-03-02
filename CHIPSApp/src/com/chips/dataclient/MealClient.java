package com.chips.dataclient;

import java.util.ArrayList;
import java.util.List;

import com.chips.datarecord.MealRecord;
import com.chips.xmlhandler.MealHandler;


public class MealClient extends XMLDataClient {

    public MealClient() {
        meals = new ArrayList<MealRecord>();
    }
    
    @Override
    protected void reloadData() {
        MealHandler handler = new MealHandler();
        parse(handler);
        
        meals.clear();
        meals.addAll(handler.getMealRecords());
    }
    
    public List<MealRecord> getMealRecords() {
        return meals;
    }

    private List<MealRecord> meals;
}
