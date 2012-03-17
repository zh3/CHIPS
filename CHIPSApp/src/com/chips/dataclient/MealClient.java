package com.chips.dataclient;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.chips.datarecord.FoodRecord;
import com.chips.datarecord.MealRecord;
import com.chips.xmlhandler.MealHandler;


public class MealClient extends XMLDataClient {

    public MealClient() {
        meals = new ArrayList<MealRecord>();
        firstMealFoods = new ArrayList<FoodRecord>();
    }
    
    @Override
    protected void reloadData() {
        MealHandler handler = new MealHandler();
        parse(handler);
        
        meals.clear();
        meals.addAll(handler.getMealRecords());
        Log.d("meal foods size: ", meals.get(0).getFoods().size() + "");
        
        firstMealFoods.clear();
        if (meals.size() >= 1) {
            firstMealFoods.addAll(meals.get(0).getFoods());
        }
    }
    
    public List<MealRecord> getMealRecords() {
        return meals;
    }
    
    public List<FoodRecord> getFirstMealFoods() {
        return firstMealFoods;
    }

    private List<FoodRecord> firstMealFoods;
    private List<MealRecord> meals;
}
