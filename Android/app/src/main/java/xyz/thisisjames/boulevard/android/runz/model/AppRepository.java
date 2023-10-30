package xyz.thisisjames.boulevard.android.runz.model;

import static androidx.lifecycle.Transformations.map;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;

public class AppRepository {



    private FirebaseAuth mAuth ;

    private FirebaseUser user ;

    private DocumentReference baseDocumentReference ;



    public AppRepository() {
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        if (user != null){
            baseDocumentReference = FirebaseFirestore.getInstance().collection("Core").document(user.getUid());
        }
    }


    //who is the user  ?
    public FirebaseUser getUser(){
        return this.user;
    }



    //login user
    public boolean loginUser(String email, String password){
        final boolean[] loginStatus = {false};

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loginStatus[0] = true;
                }else{
                    loginStatus[0] = false;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginStatus[0] = false;
            }
        });

        return loginStatus[0];
    }



    // create business

    public void createBusiness(Business business){
        baseDocumentReference.collection("Business").add(business);
    }

    public MutableLiveData<Business> getBusiness(MutableLiveData<Business> businessMutableLiveData){

        baseDocumentReference.collection("Business").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Business business = new Business() ;
                if (value != null){
                    for (QueryDocumentSnapshot doc : value){
                        if (doc != null){
                            business  = doc.toObject(Business.class);
                        }
                    }
                }
                businessMutableLiveData.setValue(business);
            }
        });

        return businessMutableLiveData;
    }

    public void createNewInvoice(Invoice invoice){
        DocumentReference documentReference = baseDocumentReference.collection("Invoices").document();
        invoice.setInvoiceID(documentReference.getId());
        documentReference.set(invoice);
    }


    public MutableLiveData<List<Invoice>> getTransactions(MutableLiveData<List<Invoice>> invoicesMutableLiveData){
        baseDocumentReference.collection("Invoices").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Invoice> transactions = new ArrayList<>();
                if (value != null){
                    for (QueryDocumentSnapshot doc : value){
                        if (doc != null){
                            transactions.add(doc.toObject(Invoice.class));
                        }
                    }
                }
                invoicesMutableLiveData.setValue(transactions);
            }
        });
        return invoicesMutableLiveData ;
    }


}
