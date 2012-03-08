package com.chips;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;


import com.chips.adapters.ExpandableFoodListAdapter;
import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.ExpandableFoodClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class AddMealToFavoritesActivity extends DataClientActivity implements HomeBar, Serializable {

	//import com.chips.adapters.ExpandableFavoritesAdapter; up top ^^
	
	private static final String BASE_URL 
      = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String FAVORITES_LIST_URL 
      = BASE_URL + "list_favorite_meals";
    private static final String ADD_FOOD_TO_FAVORITES_URL
      = BASE_URL + "add_meal_to_favorites/";

	private static final int SEARCH_REQUEST_CODE = 0;
	
	private List<FoodRecord> currentMeal = new ArrayList<FoodRecord>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.add_meal_to_favorites);
        
        FoodClient foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver
            = new ExpandableFoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
        ExpandableListView favoritesView 
            = (ExpandableListView) findViewById(R.id.favoriteMealListView);
        expandableFoodClientObserver.setListViewLayout(
            favoritesView, 
            new ExpandableFoodListAdapter(this, currentMeal, 
                    favoritesView)
        );
        
//        foodClient.setURL(FAVORITES_LIST_URL, PersistentUser.getSessionID());

//        foodClient.asynchronousLoadClientData();
        setupIntents();
        
        // onActivityResult call?
    }
    
    private void setupIntents() {
    	Bundle b = new Bundle();
        b.putString(AddFoodActivity.BUNDLE_ADD_KEY, 
                ADD_FOOD_TO_FAVORITES_URL);

        addFoodToFavoriteIntent 
            = new Intent(this, AddFoodToFavoriteMealActivity.class);
        addFoodToFavoriteIntent.putExtras(b);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
      if (requestCode == 0) {
        if (resultCode == RESULT_OK) {
        
        Bundle extras = intent.getExtras();
        FoodRecord selectedFood = (FoodRecord) extras.get("selectedFood");
        
        currentMeal.add(selectedFood);
        
        } else if (resultCode == RESULT_CANCELED) {
        	// Handle cancel
        	}
      }
    }
    
    public void addFoodToFavoriteClicked(View view) {
        startActivityForResult(addFoodToFavoriteIntent, SEARCH_REQUEST_CODE);
    }
    
    public void saveFavoriteClicked(View view) {
    	// TODO push the new favorite meal to the website here.
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private Intent addFoodToFavoriteIntent;
}
