package xyz.thisisjames.boulevard.android.runz.view.start.ui;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentOTPBinding;
import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.view.pin.PinActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class OTPFragment extends Fragment {

    private FirebaseAuth mAuth;

    private String mVerificationId;


    @Inject
    AppBase base ;

    private String Number ;
    private AlertDialog alertDialog;

    private FragmentOTPBinding binding;

    public OTPFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
             Number = getArguments().getString("Number");
             sendVerification();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOTPBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.verifBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_OTPFragment_to_phoneFragment);
            }
        });

        binding.verifNotes.setText(getString(R.string.wevesent)+Number);
        binding.verifOtpEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String code = charSequence.toString().trim();
                if (code.length() == 6){
                    showProgress("Verifying code");
                    verifyCode(code);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // verification methods

    private Object mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.

                    final String code = phoneAuthCredential.getSmsCode();
                    verifyCode(code);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    alertDialog.dismiss();
                    notifyUser(e.getMessage());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_OTPFragment_to_phoneFragment);
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(verificationId, forceResendingToken);
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:" + verificationId);

                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = forceResendingToken;

                    alertDialog.dismiss();

                    binding.verifNotes.setText(getString(R.string.wevesent)+Number);
                    binding.verifResendButton.setVisibility(View.GONE);

                    showResendButton();
                }
            };

    private void verifyCode(String code) {
        if(code != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential ) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                alertDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent = new Intent(getContext(), PinActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    notifyUser(task.getException().getMessage());
                }
            }
        });

    }

    private void sendVerification(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        showProgress("sending verification code to "+Number);
    }


    private void showProgress(String messageString){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

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

    private void notifyUser(String messageString){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

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

    private void showResendButton(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(36000);
                    binding.verifResendButton.setVisibility(View.VISIBLE);
                } catch (Exception e) {

                }
            }
        };

        thread.start();

    }
}