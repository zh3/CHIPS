package com.chips.dataclientobservers;

import java.util.List;
import java.util.Observable;

import android.app.Activity;
import android.widget.Gallery;
import android.widget.Toast;

import com.chips.adapters.MealDisplayAdapter;
import com.chips.dataclient.MealClient;
import com.chips.dataclientactions.OnUpdateAction;

public class MealClientObserver extends DataClientObserver {
    public MealClientObserver(Activity parentActivity, Gallery gallery,
            MealClient newClient, MealDisplayAdapter newAdapter, 
            OnUpdateAction action) {
        super(parentActivity);
        client = newClient;
        recordAdapter = newAdapter;
        gallery.setAdapter(recordAdapter);
        updateAction = action;
    }
    
    
    public void update(Observable dataClient, Object data) {
    	recordAdapter.notifyDataSetChanged();
        
        List<?> list = client.getMealRecords();
        if (list.size() == 0 && client.hasLoadedOnce()) {
            Toast.makeText(parentActivity, 
                    "No items found", 
                    Toast.LENGTH_SHORT).show();
        }
        
        if (updateAction != null) {
            updateAction.doUpdateAction(dataClient, data, client);
        }
    }
    
    private MealDisplayAdapter recordAdapter;
    protected MealClient client;
    private OnUpdateAction updateAction;
}
