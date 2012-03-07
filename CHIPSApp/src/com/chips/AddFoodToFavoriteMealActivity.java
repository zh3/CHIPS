package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.FoodClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

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
      FoodClientObserver foodClientObserver
          = new FoodClientObserver(this, foodClient);
      
      addClientObserverPair(foodClient, foodClientObserver);
            
      ListView favoriteMealView 
          = (ListView) findViewById(R.id.addFavoriteMealListView);
      foodClientObserver.setListViewLayout(
          favoriteMealView, android.R.layout.simple_list_item_1 
      );
      favoriteMealView.setOnItemClickListener(
              new favoriteItemOnClickListener()
      );
      
      foodClient.setURL(INVENTORY_LIST_URL, PersistentUser.getSessionID());

      foodClient.asynchronousLoadClientData();

      // --------------------- (end inventory list)
      
      
    }
    
    public void addFoodToInventoryClicked(View view) {
        
    }
    
    
    private class favoriteItemOnClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            if (parent.getClass() == ListView.class) {
                Intent intent = new Intent();
                
                ListView parentList = (ListView) parent;
                
                FoodRecord selectedFood 
                    = (FoodRecord) parentList.getItemAtPosition(position);
                intent.putExtra("selectedFood", selectedFood);
                
                if (selectedFood != null) {
                    setResult(RESULT_OK, intent);
                    Toast.makeText(AddFoodToFavoriteMealActivity.this, "made it!", Toast.LENGTH_SHORT).show();
                }
                
                finish();
            }
        }
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
