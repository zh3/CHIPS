package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PreferencesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        
        // For the back button.
    final Button button = (Button) findViewById(R.id.imageHome);
        button.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            // Perform action on clicks
        	
        	Toast.makeText(PreferencesActivity.this, "Going back!", Toast.LENGTH_SHORT).show();
        	
        	Intent intent = new Intent(PreferencesActivity.this, ApplicationHubActivity.class);
        	startActivity(intent);
        }
        }); 
        // -----------------------
    }
    
    
    
    // super calls for basic activity-changing functions.
        @Override
        protected void onStart() {
            super.onStart();
            // The activity is about to become visible.
        }
        @Override
        protected void onResume() {
            super.onResume();
            // The activity has become visible (it is now "resumed").
        }
        @Override
        protected void onPause() {
            super.onPause();
            // Another activity is taking focus (this activity is about to be "paused").
        }
        @Override
        protected void onStop() {
            super.onStop();
            // The activity is no longer visible (it is now "stopped")
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            // The activity is about to be destroyed.
        }

}
