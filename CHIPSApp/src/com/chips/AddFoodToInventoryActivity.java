package com.chips;

import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AddFoodToInventoryActivity extends Activity implements HomeBar {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.add_food_to_inventory);
    }
    
    public void addFoodToInventoryClicked(View view) {
        
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
}
