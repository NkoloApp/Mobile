package xyz.thisisjames.boulevard.android.runz.view.invoices.ui;

import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.appbar;
import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.pagePosition;
import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.sendBackButton;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceAmountBinding;
import xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class InvoiceAmountFragment extends Fragment {

    @Inject
    AppBase base ;

    private FragmentInvoiceAmountBinding binding;

    public InvoiceAmountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceAmountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.invoiceAmountEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                String text = binding.invoiceAmountEdt.getText().toString().trim();

                if (text == null || text.isEmpty()){
                    binding.invoiceAmountEdt.setError("Invalid input");
                }else{
                    onContinue(text);
                }

                return false;
            }
        });


        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.invoiceAmountEdt.getText().toString().trim();

                if (text == null || text.isEmpty()){
                    binding.invoiceAmountEdt.setError("Invalid input");
                }else{
                    onContinue(text);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            sendBackButton.setVisibility(View.GONE);
        }catch (Exception e){

        }

        if (base.invoiceController.invoice.getInvoiceAmount() != null){
            binding.invoiceAmountEdt.setText(base.invoiceController.invoice.getInvoiceAmount().split(" ")[0].trim());
        }


    }


    private void onContinue(String text){
        int amount = Integer.valueOf(text);
        base.invoiceController.invoice.setInvoiceAmount(text+" xaf");
        base.invoiceController.invoice.setInvoiceTransferAmount(amount);


        int fees = (int) Math.floor(amount*(0.01));

        base.invoiceController.invoice.setInvoiceCollectionFees(fees);

        base.invoiceController.invoice.setInvoiceTotalPayable(amount-fees);


        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                navigate(R.id.action_invoiceAmountFragment_to_invoiceRecipientFragment);

    }

}