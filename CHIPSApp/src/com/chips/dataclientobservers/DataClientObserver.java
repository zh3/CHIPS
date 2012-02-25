package com.chips.dataclientobservers;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;

public abstract class DataClientObserver implements Observer {
    public DataClientObserver(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public abstract void update(Observable observable, Object data);
    
    protected Activity parentActivity;
}
