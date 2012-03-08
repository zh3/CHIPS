package com.chips;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
        
        setupAddURL();
        setupWebsiteCommunication();
        
        addMealIntent = new Intent(this, AddMealToFavoritesActivity.class);
    }
    
    private void setupAddURL() {
    	addURL = ADD_FOOD_TO_FAVORITES_URL;
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
    
    private void populateFields(FoodRecord food) {
        if (food != null) {
            nameField.setText(food.getName());
            caloriesField.setText(Double.toString(food.getCalories()));
            carbohydratesField.setText(Double.toString(food.getCarbohydrates()));
            proteinField.setText(Double.toString(food.getProtein()));
            fatField.setText(Double.toString(food.getFat()));
            
            setNutritionFieldsEnabled(false);
        }
    }
    
    private void setNutritionFieldsEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        nameField.setFocusable(enabled);
        caloriesField.setEnabled(enabled);
        caloriesField.setFocusable(enabled);
        carbohydratesField.setEnabled(enabled);
        carbohydratesField.setFocusable(enabled);
        proteinField.setEnabled(enabled);
        proteinField.setFocusable(enabled);
        fatField.setEnabled(enabled);
        fatField.setFocusable(enabled);
    }
       
    
    public void addFoodToInventoryClicked(View view) {
        ImageButton addFoodToInventoryButton 
            = (ImageButton) findViewById(R.id.addButton);
        addFoodToInventoryButton.requestFocus();
       
        if (pushFoodToAddToInventory()) finish();
    }
    
    private boolean createFoodFromFields() {
        ArrayList<String> createFoodArguments = new ArrayList<String>();
        createFoodArguments.add(PersistentUser.getSessionID());
        createFoodArguments.add(nameField.getText().toString());
        createFoodArguments.add(caloriesField.getText().toString());
        createFoodArguments.add(carbohydratesField.getText().toString());
        createFoodArguments.add(fatField.getText().toString());
        createFoodArguments.add(proteinField.getText().toString());
        
        foodClient.setURL(FAVORITES_LIST_URL, createFoodArguments);
        foodClient.logURL();
        foodClient.synchronousLoadClientData();

        // make foodToAdd the returned food
        List<FoodRecord> createdFoodList = foodClient.getFoodRecords();
        boolean success = (createdFoodList.size() > 0);
        
        if (success) {
            foodToAdd = createdFoodList.get(0);
        } else {
            Toast.makeText(this, "Communication Error", 
                    Toast.LENGTH_LONG).show();
        }
        
        return success;
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

    private boolean missingFoodFieldValuesExist() {
        return (nameField.getText().toString().equals("")
                    || caloriesField.getText().toString().equals("")
                    || carbohydratesField.getText().toString().equals("")
                    || proteinField.getText().toString().equals("")
                    || fatField.getText().toString().equals("")
                    || quantityField.getText().toString().equals(""));
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
    private EditText nameField;
    private EditText caloriesField;
    private EditText carbohydratesField;
    private EditText proteinField;
    private EditText fatField;
    private EditText quantityField;
    private EditText barcodeField;
    private EditText barcodeFormatField;
    private DataPushClient pushClient;
    private FoodClient foodClient;
    private FoodRecord foodToAdd;
    private String addURL;
}
