package com.chips.datarecord;


public class FoodRecord extends DataRecord {
    private static final long serialVersionUID = -5061554920995817677L;
    
    public FoodRecord(String newId, String newName, String newCalories, String newCarbs,
            String newFat, String newProtein, String inventory) {
        super(newId);
        name = newName.trim();
        calories = Double.parseDouble(newCalories.trim());
        carbs = Double.parseDouble(newCarbs.trim());
        fat = Double.parseDouble(newFat.trim());
        protein = Double.parseDouble(newProtein.trim());
        quantity = (inventory.trim().equals("")) 
                        ? -1 
                        : Integer.parseInt(inventory.trim());
    }

    public String getName() {
        return name;
    }
    
    public double getCalories() {
        return calories;
    }
    
    public double getCarbohydrates() {
        return carbs;
    }
    
    public double getFat() {
        return fat;
    }
    
    public double getProtein() {
        return protein;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public String toString() {
        return name;
    }

    private String name;
    private double calories;
    private double carbs;
    private double fat;
    private double protein;
    private int quantity;
}
