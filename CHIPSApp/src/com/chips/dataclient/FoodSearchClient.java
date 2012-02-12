package com.chips.dataclient;

import java.net.MalformedURLException;
import java.net.URL;

public class FoodSearchClient extends XMLDataClient {
    private static final String SITE_URL = "http://cs110chips.phpfogapp.com/";
    
    private FoodSearchClient() {
        
    }

    @Override
    protected URL getXMLURL() throws MalformedURLException {
        return new URL("http://cs110chips.phpfogapp.com/index.php/mobile/find_foods/caraway/");
    }

    @Override
    protected void reloadData() {
        
    }
    
    // Singleton
    public static XMLDataClient getInstance() {
        return XMLDataClientHolder.client;
    }

    private static class XMLDataClientHolder {
        public static final XMLDataClient client = new FoodSearchClient();
    }
}
