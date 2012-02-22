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
    
    // Singleton
//    public static FoodSearchClient getInstance() {
//        return FoodSearchClientHolder.client;
//    }
//
//    private static class FoodSearchClientHolder {
//        public static final FoodSearchClient client = new FoodSearchClient();
//    }
    
    public List<FoodRecord> getFoodRecords() {
        return foodRecords;
    }
    
    private List<FoodRecord> foodRecords;
}
