package xyz.thisisjames.boulevard.android.runz.view.home.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentTransactionsBinding;

public class TransactionsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentTransactionsBinding binding;

    // Spinner Drop down elements
    List<String> categories = new ArrayList<String>();


    public TransactionsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories.add(getString(R.string.all));
        categories.add(getString(R.string.paid));
        categories.add(getString(R.string.pending));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = binding.spinner;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<String> adapter = new ArrayAdapter( getContext(),android.R.layout.simple_spinner_item,
                categories
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item is selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos).

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}