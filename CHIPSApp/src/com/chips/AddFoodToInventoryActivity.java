package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddFoodToInventoryActivity extends Activity implements HomeBar {
    private static final int SEARCH_REQUEST_CODE = 0;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.add_food_to_inventory);
        
        searchFoodIntent = new Intent(this, SearchFoodActivity.class);
        setupEditTexts();
    }
    
    private void setupEditTexts() {
        nameField = (EditText) findViewById(R.id.newFoodNameEditText);
        caloriesField = (EditText) findViewById(R.id.caloriesEditText);
        carbohydratesField 
            = (EditText) findViewById(R.id.carbohydratesEditText);
        proteinField = (EditText) findViewById(R.id.proteinEditText);
        fatField = (EditText) findViewById(R.id.fatEditText);
        quantityField = (EditText) findViewById(R.id.quantityEditText);
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
            
            populateFields(selectedFood);
            Toast toast;
            toast = Toast.makeText(getApplicationContext(), "Result is: " + selectedFood.toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    }
    
    private void populateFields(FoodRecord food) {
        nameField.setText(food.getName());
        caloriesField.setText(Double.toString(food.getCalories()));
        carbohydratesField.setText(Double.toString(food.getCarbohydrates()));
        proteinField.setText(Double.toString(food.getProtein()));
        fatField.setText(Double.toString(food.getFat()));
        quantityField.setText("0");
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
        
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    public void searchFoodClicked(View view) {
        startActivityForResult(searchFoodIntent, SEARCH_REQUEST_CODE);
    }
    
    private Intent searchFoodIntent;
    private EditText nameField;
    private EditText caloriesField;
    private EditText carbohydratesField;
    private EditText proteinField;
    private EditText fatField;
    private EditText quantityField;
}
