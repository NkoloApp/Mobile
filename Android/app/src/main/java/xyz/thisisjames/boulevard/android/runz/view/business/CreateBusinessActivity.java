package xyz.thisisjames.boulevard.android.runz.view.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.ActivityCreateBusinessBinding;
import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.view.home.HomeActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class CreateBusinessActivity extends AppCompatActivity {

    private ActivityCreateBusinessBinding binding ;

    private Business business = new Business();

    private Dialog alertDialog;

    @Inject
    AppBase base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBusinessBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);



        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business.setBusinessName(binding.cbaInputBname.getText().toString().trim());
                business.setBusinessNIU(binding.cbaInputBniu.getText().toString().trim());
                business.setBusinessOwnerName(binding.cbaInputOname.getText().toString().trim());
                business.setBusinessOwnerEmail(binding.cbaInputOemail.getText().toString().trim());
                try{

                    if (business.isComplete()){
                        base.createBusiness(business);
                        moveClass();
                    } else {
                        notifyUser("Unable to create business profile. Please make sure your have provided all requested data.");
                    }

                }catch (Exception e){
                    notifyUser(e.getMessage().toString().trim());
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void notifyUser(String messageString){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);



        dialogView.findViewById(R.id.alert_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        TextView message = (TextView) dialogView.findViewById(R.id.alert_message);
        message.setText(messageString);

        dialogView.findViewById(R.id.alert_progress).setVisibility(View.GONE);

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void moveClass(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}