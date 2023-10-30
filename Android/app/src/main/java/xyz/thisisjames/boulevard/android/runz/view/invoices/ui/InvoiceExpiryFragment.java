package xyz.thisisjames.boulevard.android.runz.view.invoices.ui;

import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.sendBackButton;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceExpiryBinding;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class InvoiceExpiryFragment extends Fragment {

    @Inject
    AppBase base;

    private FragmentInvoiceExpiryBinding binding;

    private Calendar calendar ;

    public InvoiceExpiryFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentInvoiceExpiryBinding.inflate(getLayoutInflater());

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        sendBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceExpiryFragment_to_invoiceReferenceFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding.datePicker.setMinDate(System.currentTimeMillis());

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceExpiryFragment_to_invoiceConfirmFragment);
            }
        });

        binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                base.invoiceController.invoice.setInvoiceExpiryDate(calendar.getTimeInMillis());
            }
        });

        binding.expCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.datePicker.setVisibility(View.GONE);
                    base.invoiceController.invoice.setInvoiceExpiryDate(0L);
                }else{
                    binding.datePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        return binding.getRoot();
    }
}