package xyz.thisisjames.boulevard.android.runz.model;

import static androidx.lifecycle.Transformations.map;

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

    //create user
    public boolean createUser(String email, String password){
        final boolean[] createStatus = {false};

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                if(task.isSuccessful()){
                    createStatus[0] = true;
                }else{
                    createStatus[0] = false;
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createStatus[0] = false;
            }
        });


        return false;
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


    // is user verified
    public boolean getVerificationStatus(){
        if (user == null){
            return false;
        }
        return user.isEmailVerified();
    }






}
