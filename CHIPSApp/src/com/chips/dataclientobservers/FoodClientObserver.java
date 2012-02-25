package com.chips.dataclientobservers;

import java.util.List;
import java.util.Observable;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chips.dataclient.FoodClient;
import com.chips.datarecord.FoodRecord;

public class FoodClientObserver extends DataClientObserver {
    public FoodClientObserver(Activity parentActivity, FoodClient newClient) {
        super(parentActivity);
        client = newClient;
    }
    
    public void loadFoundItems(ListView destinationView, int layout) {
        recordAdapter = new ArrayAdapter<FoodRecord>(parentActivity, layout, 
                    client.getFoodRecords());
        
        destinationView.setAdapter(recordAdapter);
    }
    
    public void update(Observable dataClient, Object data) {
        recordAdapter.notifyDataSetChanged();
        
        List<?> list = client.getFoodRecords();
        if (list.size() == 0 && client.hasLoadedOnce()) {
            Toast.makeText(parentActivity, 
                    "No items found", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private ArrayAdapter<FoodRecord> recordAdapter;
    protected FoodClient client;
}
