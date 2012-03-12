package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.chips.adapters.ExpandableFoodListAdapter;
import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.ExpandableFoodClientObserver;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class InventoryActivity extends DataClientActivity implements HomeBar {
    private static final String BASE_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String INVENTORY_LIST_URL 
        = BASE_URL + "list_foods_in_inventory";
    private static final String ADD_FOOD_TO_INVENTORY_URL
        = BASE_URL + "add_food_to_inventory/";
    private static final String QUANTITY_UPDATE_URL 
          = BASE_URL + "set_quantity_of_food_in_inventory";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.inventory);
        
        FoodClient foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver
            = new ExpandableFoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
        ExpandableListView inventoryView 
            = (ExpandableListView) findViewById(R.id.inventoryListView);
        expandableFoodClientObserver.setListViewLayout(
            inventoryView, 
            new ExpandableFoodListAdapter(this, foodClient.getFoodRecords(), 
                    inventoryView, QUANTITY_UPDATE_URL)
        );
        
        foodClient.setURL(INVENTORY_LIST_URL, PersistentUser.getSessionID());

        foodClient.asynchronousLoadClientData();
        setupIntents();
    }
    
    private void setupIntents() {
        Bundle b = new Bundle();
        b.putString(AddFoodActivity.BUNDLE_ADD_KEY, 
                ADD_FOOD_TO_INVENTORY_URL);

        addFoodToInventoryIntent 
            = new Intent(this, AddFoodActivity.class);
        addFoodToInventoryIntent.putExtras(b);
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
