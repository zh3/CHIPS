package com.chips.datarecord;

public class FoodRecord extends DataRecord {

    public FoodRecord(String newId, String name, String calories, String carbs,
            String fat, String protein) {
        super(newId);
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

    private String name;
    private double calories;
    private double carbs;
    private double fat;
    private double protein;
}
