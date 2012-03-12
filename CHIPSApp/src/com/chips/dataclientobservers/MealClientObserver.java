package com.chips.dataclientobservers;

import java.util.List;
import java.util.Observable;

import android.app.Activity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chips.adapters.ExpandableMealListAdapter;
import com.chips.adapters.MealDisplayAdapter;
import com.chips.dataclient.MealClient;
import com.chips.datarecord.MealRecord;

public class MealClientObserver extends DataClientObserver {
    public MealClientObserver(Activity parentActivity, Gallery gallery,
            MealClient newClient) {
        super(parentActivity);
        list = false;
        client = newClient;
        recordAdapter = new MealDisplayAdapter(parentActivity, 
                client.getMealRecords());
        gallery.setAdapter(recordAdapter);
    }
    
      // Overloaded ctor for a ListView instead of a gallery
    public MealClientObserver(Activity parentActivity, ExpandableListView listView,
            MealClient newClient) {
        super(parentActivity);
        client = newClient;
        list = true;
        mealListAdapter = new ExpandableMealListAdapter(parentActivity, 
        		client.getMealRecords(), listView);
//        recordAdapter = new MealDisplayAdapter(parentActivity, 
//                client.getMealRecords());
        listView.setAdapter(mealListAdapter);
    }
    
    public void update(Observable dataClient, Object data) {
        if(list) {
        	mealListAdapter.notifyDataSetChanged();
        }
        else {
    	  recordAdapter.notifyDataSetChanged();
        }
        
        
        List<?> list = client.getMealRecords();
        if (list.size() == 0 && client.hasLoadedOnce()) {
            Toast.makeText(parentActivity, 
                    "No items found", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private MealDisplayAdapter recordAdapter;
    private ExpandableMealListAdapter mealListAdapter;
    protected MealClient client;
    private boolean list;
}
