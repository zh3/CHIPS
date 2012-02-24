package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.Toast;

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
        
        // Initialize buttons --
//        searchFoodClicked();
        shoppingListClicked();
        calendarClicked();
        inventoryClicked();
        preferencesClicked();
        statisticsClicked();
        
    }
    
    
    /*
    // Callback for barcode scanner activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Toast toast;
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            toast = Toast.makeText(getApplicationContext(), scanResult.toString(), Toast.LENGTH_LONG);
            
        } else {
            toast = Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_LONG);
        }
        // else continue with any other code you need in the method
        toast.show();
    }
    */
    
    
    /*
     * Callback function for when 'Scan Barcode' is clicked
     */
    /*
    public void scanBarcodeClicked(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
*/

    public void searchFoodClicked() {
    	// For the search food button.
        final ImageButton button = (ImageButton) findViewById(R.id.doSearchFoodButton);
            button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Search for food!", Toast.LENGTH_SHORT).show();
            	startActivity(searchFoodActivityIntent);
            }
            });
    }
    
    public void shoppingListClicked() {
    	// For the Shopping List button.
        final ImageButton button1 = (ImageButton) findViewById(R.id.shoppingButton);
            button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Going to shopping lists!", Toast.LENGTH_SHORT).show();
            	startActivity(shoppingListActivityIntent);
            }
            }); 
    }
    
    public void calendarClicked() {
    	// For the Calendar button.
        final ImageButton button2 = (ImageButton) findViewById(R.id.calendarButton);
            button2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Going to calendar!", Toast.LENGTH_SHORT).show();
            	
            	//Intent intent = new Intent(ApplicationHubActivity.this, CalendarActivity.class);
            	startActivity(calendarActivityIntent);
            }
            }); 
            // -----------------------
        //startActivity(calendarActivityIntent);
    }
    
    public void inventoryClicked() {
    	// For the Inventory button.
        final ImageButton button3 = (ImageButton) findViewById(R.id.inventoryButton);
            button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Going to inventory!", Toast.LENGTH_SHORT).show();
            	startActivity(inventoryActivityIntent);
            }
            }); 
    }
    
    public void preferencesClicked() {
    	// For the Preferences button.
        final ImageButton button4 = (ImageButton) findViewById(R.id.applicationHubSettingsButton);
            button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Going to preferences!", Toast.LENGTH_SHORT).show();
            	startActivity(preferencesActivityIntent);
            }
            }); 
    }
    
    public void statisticsClicked() {
    	// For the Statistics button.
        final ImageButton button5 = (ImageButton) findViewById(R.id.addButton);
//            button5.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
                // Perform action on clicks
            	
//            	Toast.makeText(ApplicationHubActivity.this, "Going to statistics!", Toast.LENGTH_SHORT).show();
//            	startActivity(statisticsActivityIntent);
//            }
//            }); 
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