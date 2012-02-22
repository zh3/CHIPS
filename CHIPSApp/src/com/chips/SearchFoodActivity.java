package com.chips;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chips.dataclient.FoodClient;
import com.chips.datarecord.FoodRecord;

public class SearchFoodActivity extends Activity implements Observer {
    private static final String BASE_SEARCH_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/list_foods_in_nutrition_database_with_name/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_food);
        
        searchFoodEditText = (EditText) findViewById(R.id.searchFoodEditText);
        searchClient = new FoodClient();
        loadFoundItems();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        searchClient.deleteObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchClient.addObserver(this);
        update(null,null);
        searchClient.refreshClient();
    }
    
    public void update(Observable dataClient, Object data) {
        foodRecordAdapter.notifyDataSetChanged();
        
        List<?> list = searchClient.getFoodRecords();
        if (list.size() == 0) {
            Toast.makeText(SearchFoodActivity.this, 
                    "No items found", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    public void loadFoundItems() {
        ListView foundItemsView 
            = (ListView) findViewById(R.id.searchResultView);
        
        foodRecordAdapter = new ArrayAdapter<FoodRecord>(this,
                android.R.layout.simple_list_item_1, 
                    searchClient.getFoodRecords());
        
        foundItemsView.setAdapter(foodRecordAdapter);
    }
    
    public void doSearchFoodButtonClicked(View view) {
        searchClient.setURL(
                BASE_SEARCH_URL + searchFoodEditText.getText().toString()
        );
        searchClient.refreshClient();
    }
    
    private ArrayAdapter<FoodRecord> foodRecordAdapter;
    private EditText searchFoodEditText;
    private FoodClient searchClient;
}
