package com.chips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
            if (event.getAction() == KeyEvent.ACTION_UP) {
                try {
                    URL chips = new URL("http://cs110chips.phpfogapp.com/index.php/mobile/find_foods/caraway");
                    BufferedReader in = new BufferedReader(
                          new InputStreamReader(
                          chips.openStream()));
        
                    String inputLine = in.readLine();
        
                    if ((inputLine = in.readLine()) != null) {
                        Toast.makeText(SearchFoodActivity.this, 
                                inputLine, 
                                Toast.LENGTH_SHORT).show();
                    }
                        
                    in.close();
                } catch (IOException e) {
                    Toast.makeText(SearchFoodActivity.this, 
                            "Communication Error", 
                            Toast.LENGTH_SHORT).show();
                }

                return true;
            }
            
            return false;
        }
    }
    
    private EditText searchFoodEditText;
}
