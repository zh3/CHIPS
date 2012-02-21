package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.chips.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ApplicationHubActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_hub);
        
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
        searchFoodClicked();
        shoppingListClicked();
        calendarClicked();
        inventoryClicked();
        preferencesClicked();
//        statisticsClicked();
        
    }
    
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
    
    /*
     * Callback function for when 'Scan Barcode' is clicked
     */
    public void scanBarcodeClicked(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
    
    public void searchFoodClicked() {
    	// For the Shopping List button.
        final Button button1 = (Button) findViewById(R.id.imagePlus);
            button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Search for food!", Toast.LENGTH_SHORT).show();
            	startActivity(searchFoodActivityIntent);
            }
            }); 
    }
    
    public void shoppingListClicked() {
    	// For the Shopping List button.
        final Button button1 = (Button) findViewById(R.id.imageShopping);
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
        final Button button2 = (Button) findViewById(R.id.imageCalendar);
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
        final Button button3 = (Button) findViewById(R.id.imageInventory);
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
        final Button button4 = (Button) findViewById(R.id.imageSettings);
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
        final Button button5 = (Button) findViewById(R.id.imagePlus);
            button5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	
            	Toast.makeText(ApplicationHubActivity.this, "Going to statistics!", Toast.LENGTH_SHORT).show();
            	startActivity(statisticsActivityIntent);
            }
            }); 
    }
    
    private Intent searchFoodActivityIntent;
    private Intent shoppingListActivityIntent;
    private Intent calendarActivityIntent;
    private Intent inventoryActivityIntent;
    private Intent preferencesActivityIntent;
    private Intent statisticsActivityIntent;
}