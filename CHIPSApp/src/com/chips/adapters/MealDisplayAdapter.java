package com.chips.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
        return position;
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
            MealRecord currentMeal = mealRecords.get(position);
            List<FoodRecord> foods = currentMeal.getFoods();
            LinearLayout mealIngredientGroup 
                = (LinearLayout) view.findViewById(R.id.mealIngredientGroup);
            
            for (FoodRecord food : foods) {
                mealIngredientGroup.addView(getMealItemView(food.getName(), 
                        food.getQuantity() + ""));
                
                registerMealDisplayButtonListeners(view);
            }
            
            mealTypeView.setText("Today's " + currentMeal.getMealTypeString()
                    + ":");
            
        }
        
        return view;
    }
    
    private void registerMealDisplayButtonListeners(ViewGroup view) {
        ImageButton acceptButton = (ImageButton) view.findViewById(R.id.buttonAccept);
        acceptButton.setOnClickListener(new MealAcceptOnClickListener());
    }
    
    private LinearLayout getMealItemView(String name, String quantity) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(
                R.layout.meal_view_item, null);
        
        TextView nameView 
            = (TextView) view.findViewById(R.id.mealIngredientName);
        nameView.setText(name);
        TextView quantityView 
            = (TextView) view.findViewById(R.id.mealIngredientQuantity);
        quantityView.setText(quantity + "g");
        
        return view;
    }
    
    private class MealAcceptOnClickListener implements OnClickListener {
        public void onClick(View view) {
            view.setVisibility(View.INVISIBLE);
            
        }
        
    }
    
    private List<MealRecord> mealRecords;
}
