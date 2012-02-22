package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;

import com.chips.adapters.MealDisplayAdapter;

public class ApplicationHubActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_hub);
        
        Gallery gallery = (Gallery) findViewById(R.id.gallery);        
        gallery.setAdapter(new MealDisplayAdapter(this));
        
        setupIntents();
    }
    
    private void setupIntents() {
        searchFoodActivityIntent = new Intent(this, SearchFoodActivity.class);
        shoppingListActivityIntent = new Intent(this, ShoppingListActivity.class);
        calendarActivityIntent = new Intent(this, CalendarActivity.class);
        inventoryActivityIntent = new Intent(this, InventoryActivity.class);
        preferencesActivityIntent = new Intent(this, PreferencesActivity.class);
        statisticsActivityIntent = new Intent(this, StatisticsActivity.class);
    }
    
    public void searchFoodClicked(View view) {
        startActivity(searchFoodActivityIntent);
    }
    
    public void shoppingListClicked(View view) {
        startActivity(shoppingListActivityIntent);
    }
    
    public void calendarClicked(View view) {
        startActivity(calendarActivityIntent);
    }
    
    public void inventoryClicked(View view) {
        startActivity(inventoryActivityIntent);
    }
    
    public void preferencesClicked(View view) {
        startActivity(preferencesActivityIntent);
    }
    
    public void statisticsClicked(View view) {
        startActivity(statisticsActivityIntent);
    }
    
    public void addFavoriteClicked(View view) {
      Intent favoriteActivityIntent 
          = new Intent(this, FavoritesActivity.class);
      favoriteActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(favoriteActivityIntent);
  }
    
    private Intent searchFoodActivityIntent;
    private Intent shoppingListActivityIntent;
    private Intent calendarActivityIntent;
    private Intent inventoryActivityIntent;
    private Intent preferencesActivityIntent;
    private Intent statisticsActivityIntent;
}