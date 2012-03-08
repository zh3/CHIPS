package com.chips.adapters;

import android.view.View;

import com.chips.datarecord.MealRecord;

public class NoOpMealDisplayButtonListener 
    implements MealDisplayButtonOnClickListener {

    @Override
    public void onClick(View view, MealRecord meal) {
        // Do Nothing
    }

}
