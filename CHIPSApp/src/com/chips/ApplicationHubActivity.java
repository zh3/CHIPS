package com.chips;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;

import com.chips.dataclient.MealClient;
import com.chips.dataclientobservers.MealClientObserver;
import com.chips.user.PersistentUser;
import com.chips.R;

public class ApplicationHubActivity extends DataClientActivity {
    private static final String LIST_MEALS_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/list_meals/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_hub);
        
        Gallery gallery = (Gallery) findViewById(R.id.gallery);        
//        gallery.setAdapter(new MealDisplayAdapter(this));
        
        MealClient client = new MealClient();
        MealClientObserver observer 
            = new MealClientObserver(this, gallery, client);
        
        addClientObserverPair(client, observer);
        
        client.setURL(LIST_MEALS_URL, PersistentUser.getSessionID());
        client.logURL();
        client.asynchronousLoadClientData();
        
        setupIntents();
    }
    
    private void setupIntents() {
        searchFoodActivityIntent = new Intent(this, SearchFoodActivity.class);
        shoppingListActivityIntent = new Intent(this, ShoppingListActivity.class);
        calendarActivityIntent = new Intent(this, CalendarActivity.class);
        inventoryActivityIntent = new Intent(this, InventoryActivity.class);
        preferencesActivityIntent = new Intent(this, PreferencesActivity.class);
        statisticsActivityIntent = new Intent(this, StatisticsActivity.class);
        loginActivityIntent = new Intent(this, LoginActivity.class);
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
        // TODO implement actual logout calling website
        finish();
    }
    
    public void addFavoriteClicked(View view) {
      Intent favoriteActivityIntent 
          = new Intent(this, FavoritesActivity.class);
      startActivity(favoriteActivityIntent);
    }
    
    private Intent searchFoodActivityIntent;
    private Intent shoppingListActivityIntent;
    private Intent calendarActivityIntent;
    private Intent inventoryActivityIntent;
    private Intent preferencesActivityIntent;
    private Intent statisticsActivityIntent;
    private Intent loginActivityIntent;
}