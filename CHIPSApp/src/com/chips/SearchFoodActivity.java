package com.chips;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

public class SearchFoodActivity extends AsynchronousFoodRecordListViewActivity 
        implements HomeBar {
    private static final String BASE_SEARCH_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/list_foods_in_nutrition_database_with_name/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.search_food);
        
        searchFoodEditText = (EditText) findViewById(R.id.searchFoodEditText);
        loadFoundItems(android.R.layout.simple_list_item_1);
    }
    
    public void doSearchFoodButtonClicked(View view) {
        client.setURL(
                BASE_SEARCH_URL, 
                searchFoodEditText.getText().toString()
        );
        client.refreshClient();
    }
    
    @Override
    protected ListView getListView() {
        return (ListView) findViewById(R.id.searchResultView);
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    private EditText searchFoodEditText;
}
