package com.njongjames.nkolo.credentials;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.njongjames.nkolo.databinding.ActivityCheckinBinding;

import com.njongjames.nkolo.R;

public class Checkin extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCheckinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckinBinding.inflate(getLayoutInflater());

        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        setContentView(binding.getRoot());


        View decorView = getWindow().getDecorView();
        //int uioptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        int uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uioptions);



    }


}