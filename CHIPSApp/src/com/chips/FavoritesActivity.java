package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

public class FavoritesActivity extends AsynchronousDataClientActivity implements HomeBar {
/*
	private static final String INVENTORY_LIST_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "list_foods_in_inventory";
*/    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.favorites);

/*        
        FoodClient foodClient = new FoodClient();
//        FoodClientObserver foodClientObserver 
//            = new FoodClientObserver(this, foodClient);
        ExpandableFoodClientObserver expandableFoodClientObserver
            = new ExpandableFoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
//        foodClientObserver.setListViewLayout(
//            (ListView) findViewById(R.id.inventoryListView),
//            android.R.layout.simple_list_item_1
//        );
        
        expandableFoodClientObserver.setListViewLayout(
            (ExpandableListView) findViewById(R.id.favoriteMealListView), 
            new ExpandableFoodListAdapter(this, foodClient.getFoodRecords())
        );
*/    
/*        
        foodClient.setURL(
                INVENTORY_LIST_URL, 
                ""
        );
*/

//        foodClient.refreshClient();
        setupIntents();
    }
    
    private void setupIntents() {
        addFoodToFavoriteIntent = new Intent(this, AddFoodToFavoriteMealActivity.class);
    }
    
    public void addFoodToFavoriteClicked(View view) {
        startActivity(addFoodToFavoriteIntent);
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private Intent addFoodToFavoriteIntent;
}
