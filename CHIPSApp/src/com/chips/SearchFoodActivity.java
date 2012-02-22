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

import com.chips.dataclient.FoodSearchClient;
import com.chips.datarecord.FoodRecord;

public class SearchFoodActivity extends Activity implements Observer {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_food);
        
        searchFoodEditText = (EditText) findViewById(R.id.searchFoodEditText);
        loadFoundItems();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        FoodSearchClient.getInstance().deleteObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FoodSearchClient.getInstance().addObserver(this);
        update(null,null);
        FoodSearchClient.getInstance().refreshClient();
    }
    
    public void update(Observable dataClient, Object data) {
        foodRecordAdapter.notifyDataSetChanged();
        
        List<?> list = FoodSearchClient.getInstance().getFoodRecords();
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
                    FoodSearchClient.getInstance().getFoodRecords());
        
        foundItemsView.setAdapter(foodRecordAdapter);
    }
    
    public void doSearchFoodButtonClicked(View view) {
        FoodSearchClient client = FoodSearchClient.getInstance();
        client.setSearchTerm(searchFoodEditText.getText().toString());
        client.refreshClient();
    }
    
    private ArrayAdapter<FoodRecord> foodRecordAdapter;
    private EditText searchFoodEditText;
}
