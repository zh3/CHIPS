package com.chips.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chips.R;
import com.chips.datarecord.FoodRecord;

public class ExpandableCheckableFoodListAdapter 
            extends ExpandableFoodListAdapter {
    
    public ExpandableCheckableFoodListAdapter(Context newContext, 
            List<FoodRecord> newItems, ExpandableListView newAssociatedView,
            String newQuantityUpdateURL) {
        super(newContext, newItems, newAssociatedView, newQuantityUpdateURL);
        checkedPositions = new ArrayList<Integer>();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, 
            View convertView, ViewGroup parent) {        
        FoodRecord food = (FoodRecord) getGroup(groupPosition);
        
        String group = food.toString();
        
        
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.shopping_food_group, 
                    null);
        }
        
        CheckBox shoppingGroupCheckBox 
            = (CheckBox) convertView.findViewById(
                    R.id.shoppingGroupCheckbox);
        shoppingGroupCheckBox.setChecked(false);
        shoppingGroupCheckBox.setOnClickListener(
                new ShoppingGroupCheckboxOnClickListener(
                        shoppingGroupCheckBox, groupPosition));
        
        TextView groupName 
            = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(group);
        setTextViewString(convertView, R.id.groupQuantity, 
                food.getQuantity() + "g");
        
        return convertView;
    }
    
    public List<FoodRecord> getCheckedFoods() {
        ArrayList<FoodRecord> checkedRecords = new ArrayList<FoodRecord>();
        
        for (Integer i : checkedPositions) {
            checkedRecords.add(items.get(i));
        }
        
        return checkedRecords;
    }
    
    public void removeCheckedFoods() {
        for (Integer i : checkedPositions) {
            items.remove(i.intValue());
        }
        
        checkedPositions.clear();
        notifyDataSetChanged();
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, 
            boolean isLastChild, View convertView, ViewGroup parent) {
        FoodRecord food = (FoodRecord) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_child, null);
        }
        
        setTextViewString(convertView, R.id.childCaloriesDisplay, 
                          "" + food.getCalories());
        setTextViewString(convertView, R.id.childCarbohydratesDisplay, 
                "" + food.getCarbohydrates());
        setTextViewString(convertView, R.id.childProteinDisplay, 
                "" + food.getProtein());
        setTextViewString(convertView, R.id.childFatDisplay, 
                "" + food.getFat());
        EditText quantityEditText 
            = (EditText) convertView.findViewById(R.id.childQuantityEditText);
        quantityEditText.setText(food.getQuantity() + "");
        Button updateButton 
            = (Button) convertView.findViewById(R.id.childUpdateButton);
        updateButton.setFocusable(false);
        updateButton.setOnClickListener(
                new FoodUpdateOnClickListener(
                        groupPosition, childPosition, quantityEditText,
                        quantityUpdateURL));
        
        return convertView;
//        View childView = super.getChildView(groupPosition, childPosition, 
//                isLastChild, convertView, parent);
//        
//        if (convertView != childView) {
//            EditText quantityEditText = (EditText) childView.findViewById(
//                    R.id.childQuantityEditText);
//            Button updateButton 
//                = (Button) childView.findViewById(R.id.childUpdateButton);
//            updateButton.setFocusable(false);
//            updateButton.setOnClickListener(
//                    new FoodUpdateOnClickListener(
//                            groupPosition, childPosition, quantityEditText,
//                            SHOPPING_LIST_QUANTITY_UPDATE_URL));
//        }
//        
//        return childView;
    }
    
    private class ShoppingGroupCheckboxOnClickListener 
            implements OnClickListener {
        public ShoppingGroupCheckboxOnClickListener(CheckBox box, 
                int newGroupPosition) {
            groupPosition = newGroupPosition;
            associatedCheckBox = box;
            checked = false;
        }
        @Override
        public void onClick(View v) {
            associatedCheckBox.setChecked(checked = !checked);
            
            if (checked) {
                checkedPositions.add(groupPosition);
            } else {
                checkedPositions.remove(groupPosition);
            }
        }
        
        private CheckBox associatedCheckBox;
        private Integer groupPosition;
        private boolean checked;
    }
    
    private ArrayList<Integer> checkedPositions;
}
