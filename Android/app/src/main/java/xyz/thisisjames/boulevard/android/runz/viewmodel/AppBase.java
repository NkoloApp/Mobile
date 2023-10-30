package xyz.thisisjames.boulevard.android.runz.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import xyz.thisisjames.boulevard.android.runz.model.AppRepository;
import xyz.thisisjames.boulevard.android.runz.model.controllers.InvoiceController;
import xyz.thisisjames.boulevard.android.runz.model.data.Business;
import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;

@Singleton
public class AppBase extends AndroidViewModel {

    private String preferences = "xyz.thisisjames.boulevard.android.runz.model.preferences";
    private AppRepository appRepository;

    public InvoiceController invoiceController;

    @Inject
    public AppBase(@NonNull Application application) {

        super(application);

        appRepository = new AppRepository();

    }


    public static String baseDateFormat(Long time, String pattern){
        Calendar calenda = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calenda.setTimeInMillis(time);
        return new SimpleDateFormat(pattern).format(calenda.getTime());
    }


    public boolean isLoggedIn(){
        return  appRepository.getUser() != null ;
    }


    // Data Persistence
    public void savePin(Application app, String pin){
        SharedPreferences preference = app.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        editor.putString("PIN",pin);
        editor.commit();
        editor.apply();
    }

    public String getPin(Application app){
        SharedPreferences preference = app.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        return preference.getString("PIN","");
    }

    public void createBusiness(Business business){
        appRepository.createBusiness(business);
    }

    public LiveData<Business> getBusiness(){
        MutableLiveData<Business> mutableLiveData = new MutableLiveData<>();
        appRepository.getBusiness(mutableLiveData);
        LiveData<Business> businessLiveData = Transformations.map(mutableLiveData,input->input);
        return businessLiveData;
    }

    public LiveData<List<Invoice>> getTransactions(){
        MutableLiveData<List<Invoice>> mutableLiveData = new MutableLiveData<>();
        appRepository.getTransactions(mutableLiveData);
        LiveData<List<Invoice>> transactionsLiveData = Transformations.map(mutableLiveData,input->input);
        return transactionsLiveData;
    }


    public void setupNewInvoice(){
        invoiceController = new InvoiceController();
    }

    public void createInvoice(){
        appRepository.createNewInvoice(invoiceController.invoice);
    }
}
