package com.chips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.FoodClientObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

public class SearchFoodActivity extends AsynchronousDataClientActivity 
        implements HomeBar {
    private static final String BASE_SEARCH_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/list_foods_in_nutrition_database_with_name/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.search_food);
        
        searchFoodEditText = (EditText) findViewById(R.id.searchFoodEditText);
        
        foodClient = new FoodClient();
        FoodClientObserver foodClientObserver 
            = new FoodClientObserver(this, foodClient);
        
        addClientObserverPair(foodClient, foodClientObserver);
        
        ListView searchResultView 
            = (ListView) findViewById(R.id.searchResultView);
        searchResultView.setOnItemClickListener(
                new SearchResultOnClickListener()
        );
        
        foodClientObserver.setListViewLayout(
                searchResultView,
                android.R.layout.simple_list_item_1
        );
    }
    
    public void doSearchFoodButtonClicked(View view) {
        foodClient.setURL(
                BASE_SEARCH_URL, 
                searchFoodEditText.getText().toString()
        );
        foodClient.refreshClient();
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private class SearchResultOnClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            if (parent.getClass() == ListView.class) {
                Intent intent = new Intent();
                
                ListView parentList = (ListView) parent;
                
                FoodRecord selectedFood 
                    = (FoodRecord) parentList.getItemAtPosition(position);
                intent.putExtra("selectedFood", selectedFood);
                
                if (selectedFood != null) {
                    setResult(RESULT_OK, intent);
                }
                
                finish();
            }
        }
    }
    
    private EditText searchFoodEditText;
    private FoodClient foodClient;
}
