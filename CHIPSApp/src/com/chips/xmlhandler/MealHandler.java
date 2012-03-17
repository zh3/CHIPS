package com.chips.xmlhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.chips.datarecord.FoodRecord;
import com.chips.datarecord.MealRecord;
import com.chips.xmlhandler.TagStateEnum.FoodTagState;
import com.chips.xmlhandler.TagStateEnum.MealTagState;

public class MealHandler extends SAXHandler<MealTagState> {

    public MealHandler() {       
        innerBuffers = new EnumMap<FoodTagState, StringBuilder>(
                FoodTagState.class);
        innerValues = Arrays.asList(FoodTagState.values());
        innerBufferFoods = new ArrayList<FoodRecord>();
        mealRecords = new ArrayList<MealRecord>();
        buffers = new EnumMap<MealTagState, StringBuilder>(
                MealTagState.class);
        values = Arrays.asList(MealTagState.values());
    }
    
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        clearInnerTagBuffers();
    }
    
    protected void clearInnerTagBuffers() {
        innerTagState = null;
        for (FoodTagState key : innerValues) {
            innerBuffers.put(key, new StringBuilder());
        }
    }
    
    protected String getString(final FoodTagState key) {
        return innerBuffers.get(key).toString();
    }
    
    protected String getInnerString(final FoodTagState key) {
        return innerBuffers.get(key).toString();
    }
    
    @Override
    protected MealTagState valueOf(String s) {
        return MealTagState.valueOf(normalize(s));
    }
    
    protected FoodTagState innerValueOf(String s) {
        return FoodTagState.valueOf(normalize(s));
    }
    
    @Override
    public void characters(char ch[], int start, int length) {
        super.characters(ch, start, length);
        if (innerTagState!=null) {
            innerBuffers.get(innerTagState).append(
                    new String(ch, start, length));
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName,
            Attributes atts) throws SAXException {
        try {
            super.startElement(namespaceURI, localName, qName, atts);
            innerTagState = innerValueOf(localName);
        } catch (IllegalArgumentException e) {
            
        }
    }

    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("meal")) {
            MealRecord newMeal 
                = new MealRecord(getString(MealTagState.MEALID), 
                                 getString(MealTagState.MEALTYPE),
                                 getString(MealTagState.MEAL_NAME),
                                 getString(MealTagState.SCHEDULEDDATE), 
                                 getString(MealTagState.CONFIRMEDEATEN));
            
            getString(MealTagState.FOODS);
            
            newMeal.setFoods(innerBufferFoods);
            
//            Log.d("Meal: ", newMeal.toString());

            innerBufferFoods.clear();
            mealRecords.add(newMeal);
            clearTagBuffers();
        } else if (localName.equals("food")) {
            FoodRecord newFood = new FoodRecord(
                    getInnerString(FoodTagState.ID),
                    getInnerString(FoodTagState.NAME),
                    getInnerString(FoodTagState.CALORIES),
                    getInnerString(FoodTagState.CARBS),
                    getInnerString(FoodTagState.FAT),
                    getInnerString(FoodTagState.PROTEIN),
                    getInnerString(FoodTagState.QUANTITY));
            innerBufferFoods.add(newFood);
            clearInnerTagBuffers();
        }
    }
    
    public List<MealRecord> getMealRecords() {
        return mealRecords;
    }
    
    protected FoodTagState innerTagState;
    protected Map<FoodTagState, StringBuilder> innerBuffers;
    protected List<FoodTagState> innerValues;
    protected List<FoodRecord> innerBufferFoods;
    private List<MealRecord> mealRecords;
}
