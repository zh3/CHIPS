package com.chips;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class HomeBarAction {
    public static void goHomeClicked(Activity callingActivity, View view) {
        Intent applicationHubActivityIntent 
            = new Intent(callingActivity, ApplicationHubActivity.class);
        applicationHubActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        callingActivity.startActivity(applicationHubActivityIntent);
    }
    
    public static void addFavoriteClicked(Activity callingActivity, View view) {
        Intent favoriteActivityIntent 
            = new Intent(callingActivity, FavoritesActivity.class);
        favoriteActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        callingActivity.startActivity(favoriteActivityIntent);
    }
}
