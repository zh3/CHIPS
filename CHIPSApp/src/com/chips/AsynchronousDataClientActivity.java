package com.chips;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.chips.dataclient.XMLDataClient;
import com.chips.dataclientobservers.DataClientObserver;

public abstract class AsynchronousDataClientActivity 
        extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        dataClients = new ArrayList<XMLDataClient>();
        dataClientObservers = new ArrayList<DataClientObserver>();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        for (int i = 0; i < dataClients.size(); i++) {
            dataClients.get(i).deleteObserver(dataClientObservers.get(i));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < dataClients.size(); i++) {
            dataClients.get(i).addObserver(dataClientObservers.get(i));
            dataClientObservers.get(i).update(null,null);
            dataClients.get(i).refreshClient();
        }
    }
    
    protected void addClientObserverPair(
            XMLDataClient dataClient, 
            DataClientObserver dataClientObserver
    ) {
        dataClients.add(dataClient);
        dataClientObservers.add(dataClientObserver);
    }

    protected List<XMLDataClient> dataClients;
    protected List<DataClientObserver> dataClientObservers;
}
