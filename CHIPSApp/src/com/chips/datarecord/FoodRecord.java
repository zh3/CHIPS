package com.chips.datarecord;

import android.util.Log;

public class FoodRecord extends DataRecord {

    public FoodRecord(String newId, String newName, String newCalories, String newCarbs,
            String newFat, String newProtein) {
        super(newId);
        name = newName.trim();
        calories = Double.parseDouble(newCalories.trim());
        carbs = Double.parseDouble(newCarbs.trim());
        fat = Double.parseDouble(newFat.trim());
    }

    public String getName() {
        return name;
    }
    
    public double getCalories() {
        return calories;
    }
    
    public double getCarbs() {
        return carbs;
    }
    
    public double getFat() {
        return fat;
    }
    
    public double getProtein() {
        return protein;
    }
    
    public String toString() {
        Log.d("Food record name", name);
        return name;
    }

    private String name;
    private double calories;
    private double carbs;
    private double fat;
    private double protein;
}
