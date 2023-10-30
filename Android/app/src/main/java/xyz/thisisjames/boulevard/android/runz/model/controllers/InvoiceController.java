package xyz.thisisjames.boulevard.android.runz.model.controllers;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;

public class InvoiceController {

    public Invoice invoice;

    public InvoiceController(){
        invoice = new Invoice();
    }

    public void setPayables( ){


        DatabaseReference id = FirebaseDatabase.getInstance().getReference().push();

        this.invoice.setInvoiceID(id.getKey());
        this.invoice.setInvoiceReferenceCode(id.getKey().substring(4,11));

        this.invoice.setInvoiceSendTime(System.currentTimeMillis());

    }




    @SuppressLint("Range")
    public String getContactDisplayNameByNumber(String number, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "?";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }

}
