package xyz.thisisjames.boulevard.android.runz.view.invoices.ui;

import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.appbar;
import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.sendBackButton;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceConfirmBinding;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class InvoiceConfirmFragment extends Fragment {


     @Inject
    AppBase base;

     private FragmentInvoiceConfirmBinding binding;

    public InvoiceConfirmFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        binding = FragmentInvoiceConfirmBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appbar.setVisibility(View.GONE);
        base.invoiceController.setPayables();


        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceConfirmFragment_to_invoiceExpiryFragment);

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.amount.setText(base.invoiceController.invoice.getInvoiceAmount());
        binding.invoicePhoneNumber.setText(base.invoiceController.invoice.getInvoiceRecipientNumber());
        binding.invoiceName.setText(base.invoiceController.invoice.getInvoiceRecipientName());
        binding.invoiceReason.setText(base.invoiceController.invoice.getInvoiceReference());
        binding.invoiceFees.setText(String.format("%d xaf",base.invoiceController.invoice.getInvoiceCollectionFees()) );
        binding.invoiceRefereneCode.setText(base.invoiceController.invoice.getInvoiceReferenceCode());
        binding.invoicePayable.setText(String.format("%d xaf",base.invoiceController.invoice.getInvoiceTotalPayable()));

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.createInvoice();
                sendSMS();
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        appbar.setVisibility(View.GONE);
    }


    private void sendSMS(){
        String url = "https://www.nkolopayments/invoices/?id="+base.invoiceController.invoice.getInvoiceID();
        String sms_body = "Hello"+"\n Please use this link to pay the "+base.invoiceController.invoice.getInvoiceAmount()
                +" for "+base.invoiceController.invoice.getInvoiceReference()+". You can also pay at any Nkolo agent, with the reference code: "
                +base.invoiceController.invoice.getInvoiceReferenceCode()+". "+url;
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", sms_body);
        sendIntent.putExtra("address"  , new String ( base.invoiceController.invoice.getInvoiceRecipientNumber()));
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }
}