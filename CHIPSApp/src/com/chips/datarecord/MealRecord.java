package com.chips.datarecord;

import java.util.ArrayList;
import java.util.List;

public class MealRecord extends DataRecord {
    private static final long serialVersionUID = -688249945600134511L;
    
    public MealRecord(String newId, String newMealType, String newMealName,
            String newScheduledDate, String newConfirmedEatenValue) {
        super(newId);
        scheduledDate = newScheduledDate.trim();
        mealName = newMealName.trim();
        confirmedEaten = newConfirmedEatenValue.trim();
        mealType = newMealType.trim();
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
    
    public MealState getMealState() {
        if (confirmedEaten.equals("0")) {
            return MealState.NOT_EATEN;
        } else if (confirmedEaten.equals("1")) {
            return MealState.EATEN;
        } else if (confirmedEaten.equals("2")) {
            return MealState.ATE_OUT;
        } else {
            return MealState.UNKNOWN;
        }
    }
    
    public String toString() {
        String mealDescription = "Today's " + mealType + ":\n";
        
        for (FoodRecord food : foods) {
            mealDescription += food.getQuantity() + "g " + food.toString() 
                    + "\n";
        }
        
        return mealDescription;
    }
    
    public String calendarToString() {
    	String meal = "\n" + mealType + ":\n";
    	
    	for (FoodRecord food : foods) {
    		meal += food.toString() +": "+ food.getQuantity() + "g\n";
    	}
    	
		return meal;
    }
    
    public String getName() {
        return mealName;
    }
    
    public String getCarbohydrates() {
        double carbohydrates = 0.0;
        
        for (FoodRecord food : foods) {
            carbohydrates += food.getCarbohydrates();
        }
        
        return Long.toString(Math.round(carbohydrates));
    }
    
    public String getCalories() {
        double calories = 0.0;
        for (FoodRecord food : foods) {
            calories += food.getCalories();
        }
        
        return Long.toString(Math.round(calories));
    }
    
    public String getFat() {
        double fat = 0.0;
        for (FoodRecord food : foods) {
            fat += food.getFat();
        }
        
        return Long.toString(Math.round(fat));
    }
    
    public String getProtein() {
        double protein = 0.0;
        for (FoodRecord food : foods) {
            protein += food.getProtein();
        }
        
        return Long.toString(Math.round(protein));
    }

    public String getServingSize() {
        int servingSize = 0;
        
        for (FoodRecord food : foods) {
            servingSize += food.getQuantity();
        }
        
        return Integer.toString(servingSize);
    }
    
    private String scheduledDate;
    private String confirmedEaten;
    private String mealType;
    private String mealName;
    private List<FoodRecord> foods;
}
