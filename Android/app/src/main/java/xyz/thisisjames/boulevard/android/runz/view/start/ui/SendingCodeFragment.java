package xyz.thisisjames.boulevard.android.runz.view.start.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentSendingCodeBinding;


public class SendingCodeFragment extends Fragment {


    private FirebaseAuth mAuth;

    private String VerificationID = "Verification_ID";
    private String ResendToken = "ResendToken";
    private String Number = "Number";

    private String Error ="Error";

    private String mParam1;
    private FragmentSendingCodeBinding binding;

    private Bundle bundle;

    public SendingCodeFragment() {
        bundle = new Bundle();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


        if (getArguments() != null) {
            mParam1 = getArguments().getString("Number") ;
            sendVerification();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSendingCodeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }




    // verification methods

    private String mVerificationId;
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
                    bundle.putString("Code",code);

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    bundle.putString(Error, e.getMessage());
                    //start error activity
                    binding.header.setVisibility(View.VISIBLE);
                    binding.header.setText("Unable to verify phone number");
                    binding.message.setText(e.getMessage());
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(verificationId, forceResendingToken);
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.

                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = forceResendingToken;

                    bundle.putString(VerificationID, verificationId);
                    bundle.putString(ResendToken,""+forceResendingToken);

                }
            };



    private void sendVerification(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mParam1)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}