package com.chips.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chips.R;
import com.chips.datarecord.FoodRecord;
import com.chips.datarecord.MealRecord;

public class MealDisplayAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;
    private static final int ITEMS_TO_DISPLAY = 3;
    private static final int BREAKFAST_POSITION = 0;
    private static final int LUNCH_POSITION = 1;
    private static final int DINNER_POSITION = 2;

    public MealDisplayAdapter(Context c, List<MealRecord> newMeals) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.ApplicationHubMealsDisplay);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.ApplicationHubMealsDisplay_android_galleryItemBackground, 0);
        attr.recycle();
        mealRecords = newMeals;
    }

    public int getCount() {
        return ITEMS_TO_DISPLAY;
    }

    public Object getItem(int position) {
        return findMeal(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.meal_view, 
                    null);
        view.setBackgroundResource(R.drawable.bubble);
        
        TextView mealTypeView = (TextView) view.findViewById(R.id.mealTypeTextView);
        
        if (mealRecords.size() > position) {
            MealRecord currentMeal = findMeal(position);
            List<FoodRecord> foods = currentMeal.getFoods();
            LinearLayout mealIngredientGroup 
                = (LinearLayout) view.findViewById(R.id.mealIngredientGroup);
            
            for (FoodRecord food : foods) {
                mealIngredientGroup.addView(getMealItemView(food.getName(), 
                        food.getQuantity() + ""));
                
                //registerMealDisplayButtonListeners(view, currentMeal);
            }
            
            mealTypeView.setText("Today's " + currentMeal.getMealTypeString()
                    + ":");
            
        }
        
        return view;
    }
    
    private MealRecord findMeal(int position) {
        String mealType = getMealString(position);
        
        for (int i = 0; i < mealRecords.size(); i++) {
            if (mealRecords.get(i).getMealTypeString().equals(mealType)) {
                return mealRecords.get(i);
            }
        }
        
        return null;
    }
    
    private String getMealString(int position) {
        switch (position) {
        case BREAKFAST_POSITION:
            return "Breakfast";
        case LUNCH_POSITION:
            return "Lunch";
        case DINNER_POSITION:
            return "Dinner";
        default:
            return "";
        }
    }
    
    private RelativeLayout getMealItemView(String name, String quantity) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout view = (RelativeLayout) inflater.inflate(
                R.layout.meal_view_item, null);
        
        TextView nameView 
            = (TextView) view.findViewById(R.id.mealIngredientName);
        nameView.setText(name);
        TextView quantityView 
            = (TextView) view.findViewById(R.id.mealIngredientQuantity);
        quantityView.setText(quantity + "g");
        
        return view;
    }
    
    private List<MealRecord> mealRecords;
}
