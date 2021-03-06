package com.chips.xmlhandler;

public interface TagStateEnum {
	enum FoodTagState implements TagStateEnum {
		ID, NAME, CALORIES, CARBS, FAT, PROTEIN, QUANTITY
	}	
	
	enum SuccessTagState implements TagStateEnum {
	    SUCCESS
    }   
	
	enum LoginTagState implements TagStateEnum {
	    SUCCESS, SESSIONID
	}
	
	enum MealTagState implements TagStateEnum {
	    MEALID, MEAL_NAME, MEALTYPE, SCHEDULEDDATE, CONFIRMEDEATEN, FOODS
	}
	
	enum StatisticsTagState implements TagStateEnum {
	    HEIGHT, WEIGHT
	}
}
