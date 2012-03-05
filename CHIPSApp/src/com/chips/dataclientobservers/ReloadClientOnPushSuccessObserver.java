package com.chips.dataclientobservers;

import java.util.Observable;

import android.app.Activity;

import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.XMLDataClient;

public class ReloadClientOnPushSuccessObserver extends DataClientObserver {
    public ReloadClientOnPushSuccessObserver(Activity parentActivity, 
            XMLDataClient newClientToReload) {
        super(parentActivity);
        clientToReload = newClientToReload;
    }

    @Override
    public void update(Observable observable, Object data) {
        DataPushClient updatedClient = (DataPushClient) observable;
         
        if (updatedClient != null 
                && updatedClient.lastCompletedPushSuccessful()) {
            clientToReload.asynchronousLoadClientData();
        }
    }
    
    private XMLDataClient clientToReload;
}
