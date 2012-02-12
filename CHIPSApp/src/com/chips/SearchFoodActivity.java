package com.chips;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class SearchFoodActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_food);
        
        searchFoodEditText = (EditText) findViewById(R.id.searchFoodEditText);
        searchFoodEditText.setOnKeyListener(new SearchFoodKeyListener());
    }
    
    private class SearchFoodKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
         // If the event is a key-down event on the "enter" button
            if (event.getAction() == KeyEvent.ACTION_UP) {
              Toast.makeText(SearchFoodActivity.this, 
                             searchFoodEditText.getText(), 
                             Toast.LENGTH_SHORT).show();
              return true;
            }
            
            return false;
        }
    }
    
    private EditText searchFoodEditText;
}
