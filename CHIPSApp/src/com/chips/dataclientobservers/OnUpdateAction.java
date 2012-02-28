package com.chips.dataclientobservers;

import java.util.Observable;

import com.chips.dataclient.XMLDataClient;

public interface OnUpdateAction {
    public void doUpdateAction(Observable dataClient, Object data, 
            XMLDataClient client);
}
