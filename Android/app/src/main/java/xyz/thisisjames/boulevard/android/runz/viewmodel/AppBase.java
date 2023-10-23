package xyz.thisisjames.boulevard.android.runz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import xyz.thisisjames.boulevard.android.runz.model.AppRepository;
@Singleton
public class AppBase extends AndroidViewModel {

    private AppRepository appRepository;

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
        return  appRepository.getUser() == null ;
    }

    public boolean isVerified(){
        return appRepository.getVerificationStatus();
    }


}
