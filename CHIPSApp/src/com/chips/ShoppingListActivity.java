package com.chips;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;

public class ShoppingListActivity extends AsynchronousFoodRecordListViewActivity 
        implements HomeBar {
    private static final String SHOPPING_LIST_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "list_foods_in_shopping_list";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.shopping_list);
        
        loadFoundItems(android.R.layout.simple_list_item_multiple_choice);
        setupShoppingListView();
        
        client.setURL(
                SHOPPING_LIST_URL, 
                ""
        );

        client.refreshClient();
    }
    
    private void setupShoppingListView() {
        final ListView lv = (ListView) findViewById(R.id.shoppingListView);
        
        // Make items not focusable to avoid listitem / button conflicts
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Listen for checked items
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int arg2,
                    long arg3) {
                ((CheckedTextView)v).toggle();
            }
        });
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }

    @Override
    protected ListView getListView() {
        return (ListView) findViewById(R.id.shoppingListView);
    }
}
