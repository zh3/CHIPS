package com.chips.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chips.R;
import com.chips.dataclient.DataPushClient;
import com.chips.datarecord.FoodRecord;
import com.chips.datarecord.MealRecord;
import com.chips.user.PersistentUser;

public class ExpandableFavoritesAdapter extends BaseExpandableListAdapter {
    private static final String QUANTITY_UPDATE_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
          + "set_quantity_of_food_in_inventory";
    
    public ExpandableFavoritesAdapter(Context newContext, 
            List<MealRecord> newItems, ExpandableListView newAssociatedView) {
        context = newContext;
        items = newItems;
        associatedView = newAssociatedView;
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, 
            boolean isLastChild, View convertView, ViewGroup parent) {
        MealRecord meal = (MealRecord) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_child, null);
        }
        
        setTextViewString(convertView, R.id.childCaloriesDisplay, 
                          "" + meal.getCalories());
        setTextViewString(convertView, R.id.childCarbohydratesDisplay, 
                meal.getCarbohydrates() + "g");
        setTextViewString(convertView, R.id.childProteinDisplay, 
                meal.getProtein() + "g");
        setTextViewString(convertView, R.id.childFatDisplay, 
                meal.getFat() + "g");
        setTextViewString(convertView, R.id.childServingSizeDisplay, 
                meal.getServingSize() + "g");
        
        populateIngredientsTable(convertView, meal);
//        Button updateButton 
//            = (Button) convertView.findViewById(R.id.childUpdateButton);
//        updateButton.setFocusable(false);
//        updateButton.setOnClickListener(
//                new FoodUpdateOnClickListener(
//                        groupPosition, childPosition, quantityEditText,
//                        QUANTITY_UPDATE_URL));
        
        return convertView;
    }
    
    protected void setTextViewString(View outerView, int textViewId, 
            String newText) {
        TextView childView 
            = (TextView) outerView.findViewById(textViewId);
        childView.setText(newText);
    }

    @Override
    public int getChildrenCount(int arg0) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        MealRecord meal = (MealRecord) getGroup(groupPosition);
        
        String group = meal.getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.meal_group, null);
        }
        TextView groupName 
            = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(group);
        
        
        return convertView;
    }
    
    private void populateIngredientsTable(View convertView, MealRecord meal) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        LinearLayout ingredientsLayout
            = (LinearLayout) convertView.findViewById(R.id.ingredientsLayout);
        
        for (FoodRecord food : meal.getFoods()) {
            insertFoodRow(convertView, ingredientsLayout, food, inflater);
        }
    }
    
    private void insertFoodRow(View convertView, LinearLayout tableLayout, 
            FoodRecord food, LayoutInflater inflater) {
        View mealIngredientRow = inflater.inflate(R.layout.meal_ingredient_row, 
                null);
        
        TextView ingredientNameView 
            = (TextView) mealIngredientRow.findViewById(
                    R.id.ingredientNameView);
        ingredientNameView.setText(food.getName());
        
        TextView ingredientQuantityView 
            = (TextView) mealIngredientRow.findViewById(
                    R.id.ingredientQuantityView);
        ingredientQuantityView.setText(food.getQuantity() + "g");
        
        tableLayout.addView(mealIngredientRow);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
    
    protected class FoodUpdateOnClickListener 
            implements OnClickListener, Observer {
        
        public FoodUpdateOnClickListener(int groupPosition, 
                int childPosition, EditText associatedQuantityEditText,
                String newUpdateURL) {
            buttonGroupPosition = groupPosition;
            buttonChildPosition = childPosition;
            quantityEditText = associatedQuantityEditText;
            updateURL = newUpdateURL;
            
            pushClient = new DataPushClient();
            pushClient.addObserver(this);
        }

        @Override
        public void onClick(View v) {
            FoodRecord food = (FoodRecord) getChild(buttonGroupPosition, 
                    buttonChildPosition);
            food.setQuantity(Integer.parseInt(
                    quantityEditText.getText().toString().trim()));
            
            ArrayList<String> quantityUpdateArguments = new ArrayList<String>();
            quantityUpdateArguments.add(PersistentUser.getSessionID());
            quantityUpdateArguments.add(food.getId() + "");
            quantityUpdateArguments.add(food.getQuantity() + "");
            
            pushClient.setURL(updateURL, quantityUpdateArguments);
            pushClient.asynchronousLoadClientData();
            pushClient.logURL();
            
            if (food.getQuantity() == 0) {
                associatedView.collapseGroup(buttonGroupPosition);
                items.remove(food);
            }
            notifyDataSetChanged();
        }
        
        @Override
        public void update(Observable observable, Object data) {
            if (!pushClient.lastCompletedPushSuccessful()) {
                Toast.makeText(context, "Update Error", 
                               Toast.LENGTH_LONG).show();
            }
        }
        
        private DataPushClient pushClient;
        private int buttonGroupPosition;
        private int buttonChildPosition;

        private EditText quantityEditText;
        private String updateURL;
    }
    
    protected Context context;
    protected List<MealRecord> items;
    private ExpandableListView associatedView;
}
