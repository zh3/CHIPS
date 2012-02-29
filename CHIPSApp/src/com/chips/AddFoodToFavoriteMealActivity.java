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

public class AddFoodToFavoriteMealActivity extends DataClientActivity implements HomeBar {
    private static final int SEARCH_REQUEST_CODE = 0;
    private static final String INVENTORY_LIST_URL 
    = "http://cs110chips.phpfogapp.com/index.php/mobile/"
      + "list_foods_in_inventory";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.add_food_to_favorite);
        
        // TODO Make this search only the inventory, not the whole food database.
        // (Although, actually, it couldn't hurt to let the user add any food to
        //  a favorite meal. They'd just need to be reminded that they're looking
        //  at all foods, not just their inventory.)
        
        // -- The Search button --
        
        searchFoodIntent = new Intent(this, SearchFoodActivity.class);
        
        // -----------------------
        
        // -- The Inventory List --
        
        FoodClient foodClient = new FoodClient();
      ExpandableFoodClientObserver expandableFoodClientObserver
          = new ExpandableFoodClientObserver(this, foodClient);
      
      addClientObserverPair(foodClient, expandableFoodClientObserver);
      
      ExpandableListView favoriteMealView 
          = (ExpandableListView) findViewById(R.id.addFavoriteMealListView);
      expandableFoodClientObserver.setListViewLayout(
          favoriteMealView, 
          new ExpandableFoodListAdapter(this, foodClient.getFoodRecords(), 
                                        favoriteMealView)
      );
      
      foodClient.setURL(
              INVENTORY_LIST_URL, 
              ""
      );

      foodClient.asynchronousLoadClientData();

      // --------------------- (end inventory list)
      
    }
    
    public void addFoodToInventoryClicked(View view) {
        
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    public void searchFoodClicked(View view) {
        startActivityForResult(searchFoodIntent, SEARCH_REQUEST_CODE);
    }
    
    private Intent searchFoodIntent;
}
