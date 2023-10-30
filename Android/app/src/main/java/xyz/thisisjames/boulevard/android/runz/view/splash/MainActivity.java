package xyz.thisisjames.boulevard.android.runz.view.splash;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.ActivityMainBinding;
import xyz.thisisjames.boulevard.android.runz.view.home.HomeActivity;
import xyz.thisisjames.boulevard.android.runz.view.pin.PinActivity;
import xyz.thisisjames.boulevard.android.runz.view.start.StartActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;


public class MainActivity extends AppCompatActivity {

    private static int progressCount;
    private AppBase base ;
    private static CircularProgressIndicator circularProgress  ;

    private static void setProgress () {
        progressCount = 3000;

        circularProgress.setVisibility(View.VISIBLE);
        circularProgress.setCurrentProgress(progressCount);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (progressCount == 3000) {
                movePage();
            } else {
                setProgress();
                binding.rootView.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                mHandler.postDelayed(this, 2000);
            }
        }
    };

    private Handler mHandler  = new Handler() ;

    private static int progrssCount = 30 ;

    private ActivityMainBinding _binding   = null;
    private ActivityMainBinding binding  = _binding ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View decorview = getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        circularProgress = binding.circleCenter;
        circularProgress.setMaxProgress(3000);
        circularProgress.setVisibility(View.GONE);


        base = new ViewModelProvider(this).get(AppBase.class);

        mHandler.postDelayed(mRunnable,3000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        binding = null;
    }


    private void movePage(){
        if (base.isLoggedIn()){
            //should go to pin
            Intent intent = new Intent(getBaseContext(), PinActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getBaseContext(), StartActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

}