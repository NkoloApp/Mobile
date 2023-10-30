package xyz.thisisjames.boulevard.android.runz.view.pin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.ActivityPinBinding;
import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.view.business.CreateBusinessActivity;
import xyz.thisisjames.boulevard.android.runz.view.home.HomeActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class PinActivity extends AppCompatActivity {

    private ActivityPinBinding binding;

    private String code ;

    private boolean creating = false;

    private Boolean hasBusinessSetUp;

    @Inject
    AppBase base;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());
        View dc = getWindow().getDecorView();
        dc.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        binding.pinEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 5){
                    validate();
                    binding.pinTakeout.setVisibility(View.VISIBLE);
                }else {
                    binding.pinTakeout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        try{
            base.getBusiness().observe(this, new Observer<Business>() {
                @Override
                public void onChanged(Business business) {
                    hasBusinessSetUp = business.isComplete();
                }
            });

        }catch (Exception e){}
    }

    private void moveClass(){
        Intent intent ;

        if (hasBusinessSetUp){
            intent = new Intent(this, HomeActivity.class);
        }else {
            intent  = new Intent(this, CreateBusinessActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void validate(){
        String pin = binding.pinEdt.getText().toString().trim();
        if (pin.isEmpty() || pin.length()!=5){
            binding.pinEdt.setError("Invalid PIN");
        }else if (creating){
            base.savePin(getApplication(),pin);
            checkBusinessStatus();
        }else if (code.equalsIgnoreCase(pin)){
            checkBusinessStatus();
        }else {
            binding.pinEdt.setError("Invalid PIN");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        code = base.getPin(getApplication());

        if (code.trim().isEmpty()){
            creating = true;
        }else {
            creating = false ;
            binding.message.setText("Enter your five digit PIN to unlock your account.");
        }
    }

    private void checkBusinessStatus(){
        if (hasBusinessSetUp == null){
            showProgress("Verifying business information for this account.");
        }else{
            try{
                alertDialog.dismiss();
            }catch (Exception e){}
            moveClass();
        }
    }

    private void showProgress(String messageString){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        dialogView.findViewById(R.id.alert_button).setVisibility(View.GONE);
        TextView message = (TextView) dialogView.findViewById(R.id.alert_message);
        message.setText(messageString);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}