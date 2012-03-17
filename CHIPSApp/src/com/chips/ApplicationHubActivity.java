package com.chips;


import java.util.ArrayList;
import java.util.Observable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chips.adapters.MealDisplayAdapter;
import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.MealClient;
import com.chips.dataclient.XMLDataClient;
import com.chips.dataclientactions.OnUpdateAction;
import com.chips.dataclientobservers.MealClientObserver;
import com.chips.datarecord.MealRecord;
import com.chips.datarecord.MealState;
import com.chips.user.PersistentUser;

public class ApplicationHubActivity extends DataClientActivity {
    private static final double GALLERY_SCALE = 0.3;
    private static final String BASE_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String LIST_MEALS_URL 
        = BASE_URL + "get_todays_meals/";
    private static final String ACCEPT_URL = BASE_URL + "accept_meal/";
    //private static final String REJECT_URL = BASE_URL + "suggest_another/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_hub);
        
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setOnItemSelectedListener(new MealItemSelectedListener());
        scaleMealViewToScreen(gallery);
//        gallery.setAdapter(new MealDisplayAdapter(this));
        
        pushClient = new DataPushClient();
        client = new MealClient();
        mealAdapter = new MealDisplayAdapter(this, 
                client.getMealRecords());
        MealClientObserver observer 
            = new MealClientObserver(this, gallery, client, mealAdapter,
                    new ApplicationHubUpdateAction());
        
        addClientObserverPair(client, observer);
        
        client.setURL(LIST_MEALS_URL, PersistentUser.getSessionID());
        client.logURL();
        client.asynchronousLoadClientData();
        
        acceptButton = findViewById(R.id.buttonAccept);
        suggestAnotherButton = findViewById(R.id.buttonSuggestAnother);
        
        setupIntents();
    }
    
    private void scaleMealViewToScreen(Gallery gallery) {
        WindowManager wm 
            = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        int height = (int) Math.round(display.getHeight() * GALLERY_SCALE);
        
        gallery.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height));
    }
    
    private void setupIntents() {
        searchFoodActivityIntent = new Intent(this, SearchFoodActivity.class);
        shoppingListActivityIntent = new Intent(this, ShoppingListActivity.class);
        calendarActivityIntent = new Intent(this, CalendarActivity.class);
        inventoryActivityIntent = new Intent(this, InventoryActivity.class);
        preferencesActivityIntent = new Intent(this, StatisticsActivity.class);
        loginActivityIntent = new Intent(this, LoginActivity.class);
        customizeActivityIntent = new Intent(this, CustomizeActivity.class);
    }

    public void searchFoodClicked(View v) {
        startActivity(searchFoodActivityIntent);
    }
    
    public void shoppingListClicked(View v) {
        startActivity(shoppingListActivityIntent);
    }
    
    public void calendarClicked(View v) {
        startActivity(calendarActivityIntent);
    }
    
    public void inventoryClicked(View v) {
        startActivity(inventoryActivityIntent);
    }
    
    public void preferencesClicked(View v) {
        startActivity(preferencesActivityIntent);
    }
    
    public void statisticsClicked(View v) {
        startActivity(statisticsActivityIntent);
    }
    
    public void logoutClicked(View v) {
        startActivity(loginActivityIntent);
        PersistentUser.setLoginAutomaticallyEnabled(this, false);
        finish();
    }
    
    public void addFavoriteClicked(View view) {
      Intent favoriteActivityIntent 
          = new Intent(this, FavoritesActivity.class);
      startActivity(favoriteActivityIntent);
    }
    
    public void acceptClicked(View view) {
        MealRecord selectedItem = (MealRecord) gallery.getSelectedItem();
        if (selectedItem != null) {
            updateMealStatus(ACCEPT_URL, "Meal marked as eaten", selectedItem);
            acceptButton.setEnabled(false);
        }
    }
    
    public void suggestAnotherClicked(View view) {
        MealRecord selectedItem = (MealRecord) gallery.getSelectedItem();
        if (selectedItem != null) {
            Log.d("Selected item first food:", selectedItem.calendarToString());
        }
    }
    
    private void updateMealStatus(String updateURL, String successMessage, 
            MealRecord selectedItem) {
        ArrayList<String> arguments = new ArrayList<String>();
        arguments.add(PersistentUser.getSessionID());
        arguments.add(selectedItem.getId() + "");
        
        pushClient.setURL(updateURL, arguments);
        pushClient.synchronousLoadClientData();
        
        if (pushClient.lastCompletedPushSuccessful()) {
            Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
            client.asynchronousLoadClientData();
        } else {
            Toast.makeText(this, "Website communication failed", 
                    Toast.LENGTH_LONG).show();
        }
    }
    
    public void customizeClicked(View view) {
        MealRecord selectedMeal = (MealRecord) gallery.getSelectedItem();
        
        if (selectedMeal != null) {
            customizeActivityIntent.putExtra(CustomizeActivity.SELECTED_MEAL, 
                    new Integer(selectedMeal.getId()));
            startActivity(customizeActivityIntent);
            client.asynchronousLoadClientData();
        }
    }
    
    public void switchMealToFavouriteClicked(View view) {
        // TODO dialog to swap displayed meal to favourite
    }
    
    private void setButtonEnabledStates(MealRecord item) {
        if (item != null) {
            acceptButton.setEnabled(
                    item.getMealState() == MealState.NOT_EATEN);
            suggestAnotherButton.setEnabled(
                    item.getMealState() == MealState.NOT_EATEN);
        }
    }
    
    private class MealItemSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, 
                int position, long id) {
            MealRecord item = (MealRecord) parent.getItemAtPosition(position);
            setButtonEnabledStates(item);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // Do Nothing            
        }
    }
    
    private class ApplicationHubUpdateAction implements OnUpdateAction {

        @Override
        public void doUpdateAction(Observable dataClient, Object data,
                XMLDataClient client) {
            MealRecord item = (MealRecord) gallery.getSelectedItem();
            setButtonEnabledStates(item);
        }
        
    }
    
    private Intent searchFoodActivityIntent;
    private Intent shoppingListActivityIntent;
    private Intent calendarActivityIntent;
    private Intent inventoryActivityIntent;
    private Intent preferencesActivityIntent;
    private Intent statisticsActivityIntent;
    private Intent loginActivityIntent;
    private Intent customizeActivityIntent;
    private MealDisplayAdapter mealAdapter;
    private Gallery gallery;
    private View acceptButton;
    private View suggestAnotherButton;
    private MealClient client;
    private DataPushClient pushClient;
}