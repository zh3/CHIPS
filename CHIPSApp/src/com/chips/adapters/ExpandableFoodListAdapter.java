package com.chips.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chips.R;
import com.chips.datarecord.FoodRecord;

public class ExpandableFoodListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<FoodRecord> items;
    
    public ExpandableFoodListAdapter(Context newContext, 
            List<FoodRecord> newItems) {
        context = newContext;
        items = newItems;
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
        FoodRecord food = (FoodRecord) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_child, null);
        }
        TextView childCalories = (TextView) convertView.findViewById(R.id.childCalories);
        childCalories.setText("   " + food.getName());

        return convertView;
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
        String group = ((FoodRecord) getGroup(groupPosition)).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.food_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.groupName);
        tv.setText(group);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}
