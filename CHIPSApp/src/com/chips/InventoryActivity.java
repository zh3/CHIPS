package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.FoodClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InventoryActivity extends AsynchronousDataClientActivity implements HomeBar {
    private static final String INVENTORY_LIST_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "list_foods_in_inventory";
    private static final int SEARCH_REQUEST_CODE = 0;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.inventory);
        
        FoodClient foodClient = new FoodClient();
        FoodClientObserver foodClientObserver 
            = new FoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, foodClientObserver);
        
        foodClientObserver.loadFoundItems(
            (ListView) findViewById(R.id.inventoryListView),
            android.R.layout.simple_list_item_1
        );
        
        foodClient.setURL(
                INVENTORY_LIST_URL, 
                ""
        );

        foodClient.refreshClient();
        setupIntents();
    }
    
    private void setupIntents() {
        addFoodToInventoryIntent = new Intent(this, AddFoodToInventoryActivity.class);
        searchFoodIntent = new Intent(this, SearchFoodActivity.class);
    }
    
    /*
     * Callback function for when 'Scan Barcode' is clicked
     */
    public void scanBarcodeClicked(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
    
    // Callback for barcode scanner activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
        case SEARCH_REQUEST_CODE:
            handleSearchResult(requestCode, resultCode, intent);
            break;
        default:
            handleScanResult(requestCode, resultCode, intent);
        }
    }
    
    private void handleSearchResult(int requestCode, int resultCode, 
            Intent intent) {
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            FoodRecord selectedFood = (FoodRecord) extras.get("selectedFood");
            
            Toast toast;
            toast = Toast.makeText(getApplicationContext(), "Result is: " + selectedFood.toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    }
    
    private void handleScanResult(int requestCode, int resultCode, 
            Intent intent) {
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
    
    public void addFoodToInventoryClicked(View view) {
        startActivity(addFoodToInventoryIntent);
    }
    
    public void searchFoodClicked(View view) {
        startActivityForResult(searchFoodIntent, SEARCH_REQUEST_CODE);
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private Intent addFoodToInventoryIntent;
    private Intent searchFoodIntent;
}
