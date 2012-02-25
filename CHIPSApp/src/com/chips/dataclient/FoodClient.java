package com.chips.dataclient;

import java.util.ArrayList;
import java.util.List;

import com.chips.datarecord.FoodRecord;
import com.chips.xmlhandler.FoodHandler;

public class FoodClient extends XMLDataClient {
    
    public FoodClient() {
        foodRecords = new ArrayList<FoodRecord>();
    }

    @Override
    protected void reloadData() {
        FoodHandler handler = new FoodHandler();
        parse(handler);
        foodRecords.clear();
        
        foodRecords.addAll(handler.getFoodRecords());
    }
    
    public List<FoodRecord> getFoodRecords() {
        return foodRecords;
    }
    
    private List<FoodRecord> foodRecords;
}
