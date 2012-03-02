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
	    MEALID, MEALTYPE, SCHEDULEDDATE, CONFIRMEDEATEN, FOODS
	}
}
