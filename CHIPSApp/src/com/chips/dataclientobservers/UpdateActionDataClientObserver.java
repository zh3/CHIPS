package com.chips.dataclientobservers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.app.Activity;

import com.chips.dataclient.XMLDataClient;

public class UpdateActionDataClientObserver extends DataClientObserver {
    public UpdateActionDataClientObserver(Activity activity, 
            XMLDataClient client) {
        super(activity);
        
        actions = new ArrayList<OnUpdateAction>();
    }
    
    @Override
    public void update(Observable observable, Object data) {
        for (OnUpdateAction action : actions) {
            action.doUpdateAction(observable, data, client);
        }
    }
    
    public void addAction(OnUpdateAction action) {
        actions.add(action);
    }

    private List<OnUpdateAction> actions;
    protected XMLDataClient client;
}
