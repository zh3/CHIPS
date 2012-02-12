package com.chips.dataclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.chips.datarecord.FoodRecord;
import com.chips.xmlhandler.FoodHandler;

public class FoodSearchClient extends XMLDataClient {
    private static final String BASE_SEARCH_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/find_foods/";
    
    private FoodSearchClient() {
        foodRecords = new ArrayList<FoodRecord>();
        searchTerm = "";
    }

    @Override
    protected URL getXMLURL() throws MalformedURLException {
        return new URL(BASE_SEARCH_URL + searchTerm);
    }
    
    public void setSearchTerm(String newTerm) {
        searchTerm = newTerm.trim();
        
        try {
            xmlURL = getXMLURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reloadData() {
        FoodHandler handler = new FoodHandler();
        parse(handler);
        foodRecords.clear();
        
        foodRecords.addAll(handler.getFoodRecords());
    }
    
    // Singleton
    public static FoodSearchClient getInstance() {
        return FoodSearchClientHolder.client;
    }

    private static class FoodSearchClientHolder {
        public static final FoodSearchClient client = new FoodSearchClient();
    }
    
    public List<FoodRecord> getFoodRecords() {
        return foodRecords;
    }
    
    private String searchTerm;
    private List<FoodRecord> foodRecords;
}
