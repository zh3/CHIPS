package com.chips.dataclient;

import com.chips.xmlhandler.DataPushHandler;

public class DataPushClient extends XMLDataClient {
    
    public DataPushClient() {
        pushSuccessful = false;
    }

    @Override
    protected void reloadData() {
        DataPushHandler handler = new DataPushHandler();
        parse(handler);
        
        pushSuccessful = handler.lastPushSuccessful();
    }
    
    public boolean lastCompletedPushSuccessful() {
        return pushSuccessful;
    }
    
    
    
    private boolean pushSuccessful;
}
