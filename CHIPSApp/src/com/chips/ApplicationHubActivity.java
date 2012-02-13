package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    
    private Intent searchFoodActivityIntent;
    private Intent shoppingListActivityIntent;
    private Intent calendarActivityIntent;
    private Intent inventoryActivityIntent;
    private Intent preferencesActivityIntent;
    private Intent statisticsActivityIntent;
}