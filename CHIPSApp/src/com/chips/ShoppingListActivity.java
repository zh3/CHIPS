package com.chips;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.chips.adapters.ExpandableCheckableFoodListAdapter;
import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.ExpandableFoodClientObserver;
import com.chips.dataclientobservers.ReloadClientOnPushSuccessObserver;
import com.chips.datarecord.FoodRecord;
import com.chips.homebar.HomeBar;
import com.chips.homebar.HomeBarAction;
import com.chips.user.PersistentUser;

public class ShoppingListActivity extends DataClientActivity 
        implements HomeBar {
    private static final String BASE_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/";
    private static final String SHOPPING_LIST_URL 
        = BASE_URL + "list_foods_in_shopping_list";
    private static final String ADD_FOOD_TO_SHOPPING_LIST_URL
        = BASE_URL + "add_food_to_shopping_list/";
    private static final String PURCHASE_ALL_URL
        = BASE_URL + "purchase_entire_shopping_list/";
    private static final String PURCHASE_URL
    = BASE_URL + "purchase_food_in_shopping_list/";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeBarAction.inflateHomeBarView(this, R.layout.shopping_list);
        
        setupIntents();
        
        foodClient = new FoodClient();
        ExpandableFoodClientObserver expandableFoodClientObserver 
            = new ExpandableFoodClientObserver(this, foodClient);
        
        foodClient.setURL(SHOPPING_LIST_URL, PersistentUser.getSessionID());

        foodClient.logURL();
        foodClient.asynchronousLoadClientData();
        
        final ExpandableListView shoppingListView 
            = (ExpandableListView) findViewById(R.id.shoppingListView);
        shoppingListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        
        
        foodAdapter = new ExpandableCheckableFoodListAdapter(this, 
                foodClient.getFoodRecords(), shoppingListView);
        expandableFoodClientObserver.setListViewLayout(
            shoppingListView, 
            foodAdapter
        );
        
        addClientObserverPair(foodClient, expandableFoodClientObserver);
        
        setupPushClient();
    }
    
    private void setupPushClient() {
        pushClient = new DataPushClient();
        ReloadClientOnPushSuccessObserver pushObserver 
            = new ReloadClientOnPushSuccessObserver(this, foodClient);
        addClientObserverPair(pushClient, pushObserver);
    }
    
    private void setupIntents() {
        Bundle b = new Bundle();
        b.putString(AddFoodActivity.BUNDLE_ADD_KEY, 
                ADD_FOOD_TO_SHOPPING_LIST_URL);

        addFoodToShoppingListIntent 
            = new Intent(this, AddFoodActivity.class);
        addFoodToShoppingListIntent.putExtras(b);
    }
    
    public void goHomeClicked(View view) {
        HomeBarAction.goHomeClicked(this, view);
    }
    
    public void addFavoriteClicked(View view) {
        HomeBarAction.addFavoriteClicked(this, view);
    }
    
    public void addFoodToShoppingListClicked(View view) {
        startActivity(addFoodToShoppingListIntent);
    }
    
    public void purchaseButtonClicked(View view) {
        List<FoodRecord> checkedFoods = foodAdapter.getCheckedFoods();
        List<String> arguments = new ArrayList<String>();
        DataPushClient purchasePushClient = new DataPushClient();
        
        arguments.add(PersistentUser.getSessionID());
        for (FoodRecord food : checkedFoods) {
            Log.d("Checked Food", food.toString());
            arguments.add(food.getId() + "");
            purchaseFood(purchasePushClient, arguments);
            
            // Remove top
            arguments.remove(1);
        }
        
        foodAdapter.removeCheckedFoods();
    }
    
    private void purchaseFood(DataPushClient client, List<String> arguments) {
        client.setURL(PURCHASE_URL, arguments);
        client.asynchronousLoadClientData();
        client.logURL();
    }
    
    public void purchaseAllButtonClicked(View view) {
        pushClient.setURL(PURCHASE_ALL_URL, PersistentUser.getSessionID());
        pushClient.asynchronousLoadClientData();
    }
    
    private Intent addFoodToShoppingListIntent;
    private DataPushClient pushClient;
    private FoodClient foodClient;
    private ExpandableCheckableFoodListAdapter foodAdapter;
}
