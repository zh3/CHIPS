package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PreferencesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
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
    
    public void goHomeClicked(View view) {
        Intent applicationHubActivityIntent 
            = new Intent(this, ApplicationHubActivity.class);
        applicationHubActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(applicationHubActivityIntent);
    }
}
