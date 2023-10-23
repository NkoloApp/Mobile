package xyz.thisisjames.boulevard.android.runz;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class Runz extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
