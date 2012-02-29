package com.chips.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chips.R;

public class MealDisplayAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.bubble,
            R.drawable.bubble,
            R.drawable.bubble,
    };
    
    private String[] mTitleStrings = {
            "Next Meal",
            "2 Meals Time",
            "3 Meals Time",
    };

    public MealDisplayAdapter(Context c) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.ApplicationHubMealsDisplay);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.ApplicationHubMealsDisplay_android_galleryItemBackground, 0);
        attr.recycle();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(
            new Gallery.LayoutParams(
                Gallery.LayoutParams.MATCH_PARENT, 
                Gallery.LayoutParams.WRAP_CONTENT
            )
        );
        
        linearLayout.setPadding(10, 10, 10, 10);
       
        TextView textView = new TextView(mContext);
        textView.setText(mTitleStrings[position]);
        textView.setBackgroundResource(mImageIds[position]);
        linearLayout.addView(textView);
        
        return linearLayout;
    }
}
