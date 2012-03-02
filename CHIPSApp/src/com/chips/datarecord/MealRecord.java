package com.chips.datarecord;

import java.util.ArrayList;
import java.util.List;

public class MealRecord extends DataRecord {
    private static final long serialVersionUID = -688249945600134511L;
    
    public MealRecord(String newId, String newMealType, String newScheduledDate, 
            String newConfirmedEatenValue) {
        super(newId);
        scheduledDate = newScheduledDate;
        confirmedEaten = newConfirmedEatenValue;
        mealType = newMealType;
        foods = new ArrayList<FoodRecord>();
    }
    
    public String getScheduledDateString() {
        return scheduledDate;
    }
    
    public String getConfirmedEatenString() {
        return confirmedEaten;
    }
    
    public String getMealTypeString() {
        return mealType;
    }
    
    public List<FoodRecord> getFoods() {
        return foods;
    }
    
    public void setFoods(List<FoodRecord> newFoods) {
        foods.addAll(newFoods);
    }
    
    public String toString() {
        String mealDescription = "food id is: " + id + ", foods are:\n";
        
        for (FoodRecord food : foods) {
            mealDescription += food.toString() + "\n";
        }
        
        return mealDescription;
    }

    private String scheduledDate;
    private String confirmedEaten;
    private String mealType;
    private List<FoodRecord> foods;
}
