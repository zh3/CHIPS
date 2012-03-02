package com.chips.dataclientobservers;

import java.util.List;
import java.util.Observable;

import android.app.Activity;
import android.widget.Gallery;
import android.widget.Toast;

import com.chips.adapters.MealDisplayAdapter;
import com.chips.dataclient.MealClient;

public class MealClientObserver extends DataClientObserver {
    public MealClientObserver(Activity parentActivity, Gallery gallery,
            MealClient newClient) {
        super(parentActivity);
        client = newClient;
        recordAdapter = new MealDisplayAdapter(parentActivity, 
                client.getMealRecords());
        gallery.setAdapter(recordAdapter);
    }
    
    public void update(Observable dataClient, Object data) {
        recordAdapter.notifyDataSetChanged();
        
        List<?> list = client.getMealRecords();
        if (list.size() == 0 && client.hasLoadedOnce()) {
            Toast.makeText(parentActivity, 
                    "No items found", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private MealDisplayAdapter recordAdapter;
    protected MealClient client;
}
