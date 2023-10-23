package xyz.thisisjames.boulevard.android.runz.view.start.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentWelcomeBinding;


public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    public WelcomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWelcomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_phoneFragment);
            }
        });

        TextView tv = binding.prompt;

        SpannableStringBuilder privacy = new SpannableStringBuilder();
        SpannableStringBuilder terms = new SpannableStringBuilder();
        SpannableStringBuilder span = new SpannableStringBuilder();
        String regularText = "Read our ";
        String clickableText = "Privacy Policy. ";
        String regularText2 = "Tap ''Agree and continue'' to accept the ";
        String clickableText2 ="Terms of Service.";
        privacy.append(regularText);
        privacy.append(clickableText);
        terms.append(regularText2);
        terms.append(clickableText2);


        ClickableSpan clickablePrivacySpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.primary));
            }
        };

        ClickableSpan clickableTermsSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.primary));
            }
        };

        privacy.setSpan(clickablePrivacySpan, privacy.length()-clickableText.length(), privacy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        terms.setSpan(clickableTermsSpan, terms.length()-clickableText2.length(), terms.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        span.append(privacy).append(terms);


        tv.setText(span);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }



}