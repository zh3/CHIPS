package com.chips;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.chips.adapters.ExpandableCheckableFoodListAdapter;
import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.ExpandableFoodClientObserver;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class ShoppingListActivity extends DataClientActivity 
        implements HomeBar {
    private static final String SHOPPING_LIST_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "list_foods_in_shopping_list";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.shopping_list);
        
        FoodClient foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver 
            = new ExpandableFoodClientObserver(this, foodClient);
        
        foodClient.setURL(SHOPPING_LIST_URL, PersistentUser.getSessionID());

        foodClient.logURL();
        foodClient.asynchronousLoadClientData();
        
        final ExpandableListView shoppingListView 
            = (ExpandableListView) findViewById(R.id.shoppingListView);
        shoppingListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        expandableFoodClientObserver.setListViewLayout(
            shoppingListView, 
            new ExpandableCheckableFoodListAdapter(this, 
                    foodClient.getFoodRecords(), shoppingListView)
        );

        addClientObserverPair(foodClient, expandableFoodClientObserver);
    }
    
//    private void setupShoppingListView() {
        // Make items not focusable to avoid listitem / button conflicts
//        lv.setItemsCanFocus(false);
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        // Listen for checked items
//        lv.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View v, int arg2,
//                    long arg3) {
//                ((CheckedTextView)v).toggle();
//            }
//        });
//    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
}
