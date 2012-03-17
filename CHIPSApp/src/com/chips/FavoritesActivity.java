package com.chips;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chips.adapters.ExpandableFavoritesAdapter;
import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.MealClient;
import com.chips.dataclientactions.PushClientToastOnFailureAction;
import com.chips.dataclientobservers.ExpandableMealClientObserver;
import com.chips.dataclientobservers.UpdateActionDataClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class FavoritesActivity extends DataClientActivity
        implements HomeBar {
    private static final int SEARCH_REQUEST_CODE = 0;
    private static final String BASE_URL 
      = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String FAVORITES_LIST_URL 
      = BASE_URL + "list_favorite_meals";
    private static final String ADD_FOOD_TO_FAVORITES_URL
      = BASE_URL + "add_meal_to_favorites/";
    public static final String BUNDLE_ADD_KEY = "addURL";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.favorites);
        
        
        mealClient = new MealClient();
        ExpandableListView favoritesView 
            = (ExpandableListView) findViewById(R.id.favoriteMealsListView);
        ExpandableFavoritesAdapter adapter 
            = new ExpandableFavoritesAdapter(this, mealClient.getMealRecords(), 
                    favoritesView);
        ExpandableMealClientObserver observer 
            = new ExpandableMealClientObserver(this, mealClient);
        observer.setListViewLayout(favoritesView, adapter);
        
        addClientObserverPair(mealClient, observer);

        mealClient.setURL(FAVORITES_LIST_URL, PersistentUser.getSessionID());
        mealClient.asynchronousLoadClientData();
/*       
        foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver
            = new ExpandableFoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
        ExpandableListView favoritesView 
            = (ExpandableListView) findViewById(R.id.favoriteMealsListView);
        expandableFoodClientObserver.setListViewLayout(
            favoritesView, 
            new ExpandableFoodListAdapter(this, foodClient.getFoodRecords(), 
                    favoritesView)
        );
        
        foodClient.setURL(FAVORITES_LIST_URL, PersistentUser.getSessionID());

        foodClient.asynchronousLoadClientData();
*/        
        setupAddURL();
        setupWebsiteCommunication();
        
        addMealIntent = new Intent(this, EditFavoriteActivity.class);
    }
    
    private void setupAddURL() {
    	addURL = ADD_FOOD_TO_FAVORITES_URL;
    }
    
    private void setupWebsiteCommunication() {
        pushClient = new DataPushClient();
        UpdateActionDataClientObserver updateActionObserver 
            = new UpdateActionDataClientObserver(this, pushClient);
        Toast failureToast = Toast.makeText(this, 
                "Website Communication Failed", Toast.LENGTH_LONG);
        PushClientToastOnFailureAction action 
            = new PushClientToastOnFailureAction(failureToast);
        updateActionObserver.addAction(action);
        
        addClientObserverPair(pushClient, updateActionObserver);
    }  
       
    
    public void addFoodToInventoryClicked(View view) {
        ImageButton addFoodToInventoryButton 
            = (ImageButton) findViewById(R.id.addButton);
        addFoodToInventoryButton.requestFocus();
       
        if (pushFoodToAddToInventory()) finish();
    }
    
    private boolean pushFoodToAddToInventory() {
        ArrayList<String> addFoodArguments = new ArrayList<String>();
        addFoodArguments.add(PersistentUser.getSessionID());
        addFoodArguments.add(foodToAdd.getId() + "");
        addFoodArguments.add(quantityField.getText().toString());       
        
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
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    public void addFavoriteMealClicked(View view) {
        startActivityForResult(addMealIntent, SEARCH_REQUEST_CODE);
    }    
    
    private Intent addMealIntent;
    private EditText quantityField;
    private DataPushClient pushClient;
    private MealClient mealClient;
    private FoodRecord foodToAdd;
    private String addURL;
}
