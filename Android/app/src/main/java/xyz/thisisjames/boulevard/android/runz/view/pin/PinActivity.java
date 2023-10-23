package xyz.thisisjames.boulevard.android.runz.view.pin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.checkerframework.checker.units.qual.A;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.ActivityPinBinding;
import xyz.thisisjames.boulevard.android.runz.view.home.HomeActivity;

public class PinActivity extends AppCompatActivity {

    private ActivityPinBinding binding;

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
                    binding.pinTakeout.setVisibility(View.GONE);
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
                String pin = binding.pinEdt.getText().toString().trim();
                if (pin.isEmpty() || pin.length()!=5){
                    binding.pinEdt.setError("Invalid pin");
                }else {
                    Intent intent = new Intent(PinActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}