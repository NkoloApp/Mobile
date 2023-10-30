package xyz.thisisjames.boulevard.android.runz.view.invoices.ui;

import static android.provider.ContactsContract.Directory.DISPLAY_NAME;

import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.pagePosition;
import static xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity.sendBackButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.FragmentInvoiceRecipientBinding;
import xyz.thisisjames.boulevard.android.runz.view.invoices.SendInvoiceActivity;
import xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase;

@AndroidEntryPoint
public class InvoiceRecipientFragment extends Fragment {

    private static final int REQUEST_CONTACT =1 ;
    @Inject
    AppBase base;

    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private static final int PICK = 1;

    private FragmentInvoiceRecipientBinding binding;
    private Dialog alertDialog;

    private boolean dismissAndContinue = false;

    public InvoiceRecipientFragment() {
    }

    private boolean hasContactsPermission()
    {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    // Request contact permission if it
    // has not been granted already
    private void requestContactsPermission()
    {
        if (!hasContactsPermission())
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0)
        {
            //updateButton(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        binding = FragmentInvoiceRecipientBinding.inflate(getLayoutInflater());

        sendBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                        navigate(R.id.action_invoiceRecipientFragment_to_invoiceAmountFragment);
            }
        });
        sendBackButton.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestContactsPermission();

        binding.getContacts.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // Intent to pick contacts
                final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(pickContact,PICK);
            }
        });

        binding.recipientNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String newName = base.invoiceController.getContactDisplayNameByNumber(charSequence.toString().trim(), getContext());
                    if (newName == null || newName.isEmpty() || newName.equalsIgnoreCase("?")){
                        newName = "unknown recipient";
                    }
                    binding.recipientName.setText(newName);
                    binding.recipientName.setVisibility(View.VISIBLE);

                    base.invoiceController.invoice.setInvoiceRecipientName(newName);
                    base.invoiceController.invoice.setInvoiceRecipientNumber(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.recipientNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                String number = binding.recipientNumber.getText().toString().trim();
                String name = binding.recipientName.getText().toString().trim();

                if (number == null || number.isEmpty()){
                    binding.recipientNumber.setError("Invalid input");
                }else if(name.equalsIgnoreCase("unknown recipient")){
                    pagePosition = 2 ;
                    notifyUser("The number "+number+" does not look like any  other number in your phone book. Are you sure you want to invoice a stranger ?");
                }else{
                    pagePosition = 2 ;

                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                            navigate(R.id.action_invoiceRecipientFragment_to_invoiceReferenceFragment);

                }
                base.invoiceController.invoice.setInvoiceRecipientName(name);
                base.invoiceController.invoice.setInvoiceRecipientNumber(number);

                return false;
            }
        });

        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = binding.recipientNumber.getText().toString().trim();
                String name = binding.recipientName.getText().toString().trim();

                if (number == null || number.isEmpty()){
                    binding.recipientNumber.setError("Invalid input");
                }else if(name.equalsIgnoreCase("unknown recipient")){

                    notifyUser("The number "+number+" does not look like any  other number in your phone book. Are you sure you want to invoice a stranger ?");
                }else{

                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                            navigate(R.id.action_invoiceRecipientFragment_to_invoiceReferenceFragment);

                }
                base.invoiceController.invoice.setInvoiceRecipientName(name);
                base.invoiceController.invoice.setInvoiceRecipientNumber(number);

                return ;
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        if (base.invoiceController.invoice.getInvoiceRecipientNumber() != null){
            binding.recipientNumber.setText(base.invoiceController.invoice.getInvoiceRecipientNumber());
        }
    }

    private final String DISPLAY_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;


    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);



        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();

            // Specify which fields you want
            // your query to return values for
            String[] queryFields = new String[] {
                    ContactsContract.Contacts._ID,
                    DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER
            };

            // Perform your query - the contactUri
            // is like a "where" clause here
            ContentResolver cr = getContext().getContentResolver();
            Cursor cursor = cr
                    .query(contactUri, queryFields, null, null, null);
            try
            {

                // Double-check that you
                // actually got results
                if (cursor.getCount() == 0) return;

                // Pull out the first column of
                // the first row of data
                // that is your contact's name
                cursor.moveToFirst();


                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Integer hasPhone = cursor.getInt(2);



                binding.recipientName.setText(name);
                binding.recipientName.setVisibility(View.VISIBLE);

                // get the user's phone number
                String phone = null;
                if (hasPhone > 0) {
                    Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (cp != null && cp.moveToFirst()) {
                        phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        binding.recipientNumber.setText(phone);
                        cp.close();
                    }
                }

            }
            finally
            {
                cursor.close();
            }
        }
    }



    private void notifyUser(String messageString){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);



        Button button = dialogView.findViewById(R.id.alert_button);
        button.setText("Yes, continue");
        dialogView.findViewById(R.id.alert_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAndContinue= true;
                alertDialog.dismiss();
            }
        });

        TextView message = (TextView) dialogView.findViewById(R.id.alert_message);
        message.setText(messageString);

        dialogView.findViewById(R.id.alert_progress).setVisibility(View.GONE);

        alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dismissAndContinue){
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_send_invoice).
                            navigate(R.id.action_invoiceRecipientFragment_to_invoiceReferenceFragment);
                }
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                    dismissAndContinue = false;
            }
        });
    }





}