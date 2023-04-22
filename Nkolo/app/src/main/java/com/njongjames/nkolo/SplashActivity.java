package com.njongjames.nkolo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.njongjames.nkolo.credentials.Checkin;
import com.njongjames.nkolo.databinding.ActivitySplashBinding;
import com.njongjames.nkolo.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    private boolean contentHasLoaded = false ;

    private ActivitySplashBinding binding ;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    };

    private Handler handler = new Handler() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        androidx.core.splashscreen.SplashScreen
                splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        splashScreen.setKeepOnScreenCondition(() -> true );

        handler.postDelayed(runnable,2000);
    }
}