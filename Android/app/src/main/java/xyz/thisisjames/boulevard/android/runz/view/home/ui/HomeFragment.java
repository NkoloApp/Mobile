package xyz.thisisjames.boulevard.android.runz.view.home.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentHomeBinding;
import xyz.thisisjames.boulevard.android.runz.model.adapters.HistoryAdapter;
import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;
import xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class HomeFragment extends Fragment  {

    private FragmentHomeBinding binding;

    @Inject
    AppBase base;

    private Business biz;
    private Dialog alertDialog;

    public HomeFragment() {
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        base.getTransactions().observe(getViewLifecycleOwner(), new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> invoices) {
                if (invoices.size() >0){
                    binding.transactions.setVisibility(View.VISIBLE);
                    binding.nohistory.setVisibility(View.GONE);
                    if (invoices.size() >2){
                        HistoryAdapter adapter = new HistoryAdapter(2,invoices);
                        binding.mlist.setAdapter(adapter);
                    }else{
                        HistoryAdapter adapter = new HistoryAdapter(invoices.size(),invoices);
                        binding.mlist.setAdapter(adapter);
                    }

                }else{
                    binding.transactions.setVisibility(View.VISIBLE);
                    binding.nohistory.setVisibility(View.GONE);
                }
            }
        });


        binding.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_transactionsFragment);
            }
        });

        binding.newinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SendInvoiceActivity.class));
            }
        });

        binding.withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (biz.getBusinessBalance() == 0){
                    notifyUser("You can only do withdrawals when your account balance is above 2000 xaf.");
                    return;
                }
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_withdrawFragment);
            }
        });


        base.getBusiness().observe(getViewLifecycleOwner(), new Observer<Business>() {
            @Override
            public void onChanged(Business business) {

                biz = business ;

            binding.businessName.setText(String.format("Hello %s",business.getBusinessOwnerName()));

                if (business.getBusinessBalance() == 0){
                    binding.businessBalance.setText(String.format("0.00"));
                }else{
                    binding.businessBalance.setText(String.format("%f",business.getBusinessBalance()));
                }
            }
        });
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


}