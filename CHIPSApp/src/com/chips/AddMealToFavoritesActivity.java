package com.chips;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.chips.adapters.ExpandableFoodListAdapter;
import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.FoodClient;
import com.chips.dataclientactions.PushClientToastOnFailureAction;
import com.chips.dataclientobservers.ExpandableFoodClientObserver;
import com.chips.dataclientobservers.UpdateActionDataClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class AddMealToFavoritesActivity extends DataClientActivity 
        implements HomeBar, Serializable {
	
	private static final String BASE_URL 
      = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String ADD_MEAL_TO_FAVORITES_URL
      = BASE_URL + "add_meal_to_favorites/";

	private static final int SEARCH_REQUEST_CODE = 0;
	
	private List<FoodRecord> currentMeal = new ArrayList<FoodRecord>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.add_meal_to_favorites);
        
        foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver
            = new ExpandableFoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
        ExpandableListView favoritesView 
            = (ExpandableListView) findViewById(R.id.mealFoodsListView);
        expandableFoodClientObserver.setListViewLayout(
            favoritesView, 
            new ExpandableFoodListAdapter(this, currentMeal, 
                    favoritesView)
        );
        
        setupAddURL();
        setupWebsiteCommunication();
        setupIntents();
    }
    
    private void setupIntents() {
    	Bundle b = new Bundle();
        b.putString(AddFoodActivity.BUNDLE_ADD_KEY, 
                ADD_MEAL_TO_FAVORITES_URL);

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
    	// TODO push the new favorite meal to the website here - gives Communication Failed errors.
    	if( pushMealToAddToFavorites() )   finish();
    	else { Toast.makeText(this, "Problem with pushing to website.", 
                Toast.LENGTH_LONG).show();
    	}
    }
    
      /* -- Web site Communications -- */
    private void setupAddURL() {
        addURL = ADD_MEAL_TO_FAVORITES_URL;
    }
    
    private void setupWebsiteCommunication() {
        pushClient = new DataPushClient();
        foodClient = new FoodClient();
        UpdateActionDataClientObserver updateActionObserver 
            = new UpdateActionDataClientObserver(this, pushClient);
        Toast failureToast = Toast.makeText(this, 
                "Website Communication Failed", Toast.LENGTH_LONG);
        PushClientToastOnFailureAction action 
            = new PushClientToastOnFailureAction(failureToast);
        updateActionObserver.addAction(action);
        
        addClientObserverPair(pushClient, updateActionObserver);
    }
    
    private boolean pushMealToAddToFavorites() {
        ArrayList<String> addFoodArguments = new ArrayList<String>();
        addFoodArguments.add(PersistentUser.getSessionID());
        addFoodArguments.add("THIS IS A TEST");
        addFoodArguments.add("THIS IS A SECOND TEST");
//        addFoodArguments.add(foodToAdd.getId() + "");
//        addFoodArguments.add(quantityField.getText().toString());      
        
        pushClient.setURL(addURL, addFoodArguments);
        pushClient.logURL();
        pushClient.synchronousLoadClientData();
        
        boolean success = pushClient.lastCompletedPushSuccessful();
        if (!success) {
            Toast.makeText(this, "Communication Error", 
                    Toast.LENGTH_LONG).show();
        }
        
        return success;
    }
      // -------------- end website communication --
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private Intent addFoodToFavoriteIntent;
    private String addURL;
    private DataPushClient pushClient;
    private FoodClient foodClient;
}
