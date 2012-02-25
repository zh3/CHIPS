package com.chips;

import android.app.Activity;
import android.os.Bundle;

import com.chips.dataclient.FoodClient;
import com.chips.dataclientobservers.FoodClientObserver;

public abstract class AsynchronousFoodRecordListViewActivity 
        extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        client = new FoodClient();
        foodClientObserver = new FoodClientObserver(this, client);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        client.deleteObserver(foodClientObserver);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        client.addObserver(foodClientObserver);
        foodClientObserver.update(null,null);
        client.refreshClient();
    }
    
//    public void loadFoundItems(int layout) {
//        ListView foundItemsView = getListView();
//        
//        recordAdapter = new ArrayAdapter<FoodRecord>(this, layout, 
//                    client.getFoodRecords());
//        
//        foundItemsView.setAdapter(recordAdapter);
//    }
//    
//    public void update(Observable dataClient, Object data) {
//        recordAdapter.notifyDataSetChanged();
//        
//        List<?> list = client.getFoodRecords();
//        if (list.size() == 0 && client.hasLoadedOnce()) {
//            Toast.makeText(this, 
//                    "No items found", 
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//    
//    protected abstract ListView getListView();
//    
//    private ArrayAdapter<FoodRecord> recordAdapter;
    protected FoodClient client;
    protected FoodClientObserver foodClientObserver;
}
