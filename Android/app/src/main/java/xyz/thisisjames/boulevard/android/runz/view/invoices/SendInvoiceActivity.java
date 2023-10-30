package xyz.thisisjames.boulevard.android.runz.view.invoices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.WindowCompat;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.ActivitySendInvoiceBinding;
import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class SendInvoiceActivity extends AppCompatActivity {


    private ActivitySendInvoiceBinding binding;

    public static  int pagePosition = 0 ;

    public static AppCompatButton sendBackButton ;

    public static AppBarLayout appbar ;

    @Inject
    AppBase base ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendInvoiceBinding.inflate(getLayoutInflater());
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        base.setupNewInvoice();
        sendBackButton = binding.buttonNext ;

        appbar = binding.appBar;

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.invoiceController.invoice = null ;
                base.invoiceController = null ;
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        base.invoiceController = null ;
        finish();
    }
}