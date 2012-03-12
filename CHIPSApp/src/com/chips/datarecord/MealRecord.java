package com.chips.datarecord;

import java.util.ArrayList;
import java.util.List;

public class MealRecord extends DataRecord {
    private static final long serialVersionUID = -688249945600134511L;
    
    public MealRecord(String newId, String newMealType, String newScheduledDate, 
            String newConfirmedEatenValue) {
        super(newId);
        scheduledDate = newScheduledDate.trim();
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

    private String scheduledDate;
    private String confirmedEaten;
    private String mealType;
    private List<FoodRecord> foods;

	public String getQuantity() {
		// TODO Auto-generated method stub
		return "-1";
	}

	public String getCalories() {
		// TODO Auto-generated method stub
		return "-1";
	}

	public String getCarbohydrates() {
		// TODO Auto-generated method stub
		return "-1";
	}

	public String getFat() {
		// TODO Auto-generated method stub
		return "-1";
	}

	public String getProtein() {
		// TODO Auto-generated method stub
		return "-1";
	}
}
