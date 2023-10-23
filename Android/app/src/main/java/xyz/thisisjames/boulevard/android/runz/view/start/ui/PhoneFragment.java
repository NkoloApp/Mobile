package xyz.thisisjames.boulevard.android.runz.view.start.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentPhoneBinding;


public class PhoneFragment extends Fragment {


    private String mParam1;



    private FragmentPhoneBinding binding ;

    public PhoneFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.hasFocus()){
                    binding.space.setVisibility(View.GONE);
                }else {
                    binding.space.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber();
                if (mParam1.isEmpty() || mParam1.length()<6){
                    binding.phoneNumber.setError("Please enter a valid phone number");
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("Number",mParam1);
                    Navigation.findNavController(view).navigate(R.id.action_phoneFragment_to_OTPFragment
                    , bundle);
                }
            }
        });
    }

    private void getNumber(){
        String number = binding.phoneNumber.getText().toString().trim();
        String code = binding.ccpPhone.selectedCountryCode();

        mParam1 = String.format("%s %s", code,number);
    }



}