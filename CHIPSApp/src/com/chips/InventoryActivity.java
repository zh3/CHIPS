package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.FoodClientObserver;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

public class InventoryActivity extends AsynchronousDataClientActivity implements HomeBar {
    private static final String INVENTORY_LIST_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "list_foods_in_inventory";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.inventory);
        
        FoodClient foodClient = new FoodClient();
        FoodClientObserver foodClientObserver 
            = new FoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, foodClientObserver);
        
        foodClientObserver.loadFoundItems(
            (ListView) findViewById(R.id.inventoryListView),
            android.R.layout.simple_list_item_1
        );
        
        foodClient.setURL(
                INVENTORY_LIST_URL, 
                ""
        );

        foodClient.refreshClient();
        setupIntents();
    }
    
    private void setupIntents() {
        addFoodToInventoryIntent = new Intent(this, AddFoodToInventoryActivity.class);
    }
    
    public void addFoodToInventoryClicked(View view) {
        startActivity(addFoodToInventoryIntent);
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private Intent addFoodToInventoryIntent;
}
