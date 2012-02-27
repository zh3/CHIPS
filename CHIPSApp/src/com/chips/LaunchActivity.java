package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chips.user.PersistentUser;

public class LaunchActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle state){
       super.onCreate(state);

       applicationHubActivityIntent = new Intent(this, ApplicationHubActivity.class);
       loginActivityIntent = new Intent(this, LoginActivity.class);
       
       if (PersistentUser.loginSettingsExist(this)) {
           startActivity(applicationHubActivityIntent);
       } else {
           startActivity(loginActivityIntent);
       }
       
       finish();
    }
    
    private Intent applicationHubActivityIntent;
    private Intent loginActivityIntent;
        
}
