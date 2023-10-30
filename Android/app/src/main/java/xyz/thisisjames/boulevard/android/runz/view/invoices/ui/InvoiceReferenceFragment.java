package xyz.thisisjames.boulevard.android.runz.view.invoices.ui;

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

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceRecipientBinding;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceReferenceBinding;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class InvoiceReferenceFragment extends Fragment {

    @Inject
    AppBase base ;

    private FragmentInvoiceReferenceBinding binding ;

    public InvoiceReferenceFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        binding = FragmentInvoiceReferenceBinding.inflate(getLayoutInflater());

        sendBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceReferenceFragment_to_invoiceRecipientFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reference = binding.reference.getText().toString().trim();

                if (reference == null || reference.isEmpty()){
                    binding.reference.setError("a reference is required");
                     return ;
                }

                base.invoiceController.invoice.setInvoiceReference(reference);

                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceReferenceFragment_to_invoiceExpiryFragment);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        if (base.invoiceController.invoice.getInvoiceReference() != null){
            binding.reference.setText(base.invoiceController.invoice.getInvoiceReference());
        }
    }
}