package xyz.thisisjames.boulevard.android.runz.model.data;

import java.util.regex.Pattern;

public class Business {

    String businessName, businessNIU, businessOwnerName, businessOwnerEmail, businessCurrency;

    float businessBalance = 0 ;


    boolean businessOwnerEmailVerified = false;
    boolean businessNIUIsVerified = false;


    public Business() {
        this.businessNIU = "optional";
        this.businessCurrency = "XAF";
    }

    public Business(String businessName, String businessNIU, String businessOwnerName,
                    String businessOwnerEmail, String businessCurrency, float businessBalance,
                    boolean businessOwnerEmailVerified, boolean businessNIUIsVerified) {
        this.businessName = businessName;
        this.businessNIU = businessNIU;
        this.businessOwnerName = businessOwnerName;
        this.businessOwnerEmail = businessOwnerEmail;
        this.businessCurrency = businessCurrency;
        this.businessBalance = businessBalance;
        this.businessOwnerEmailVerified = businessOwnerEmailVerified;
        this.businessNIUIsVerified = businessNIUIsVerified;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessNIU() {
        return businessNIU;
    }

    public void setBusinessNIU(String businessNIU) {
        this.businessNIU = businessNIU;
    }

    public String getBusinessOwnerName() {
        return businessOwnerName;
    }

    public void setBusinessOwnerName(String businessOwnerName) {
        this.businessOwnerName = businessOwnerName;
    }

    public String getBusinessOwnerEmail() {
        return businessOwnerEmail;
    }

    public void setBusinessOwnerEmail(String businessOwnerEmail) {
        this.businessOwnerEmail = businessOwnerEmail;
    }

    public boolean isBusinessOwnerEmailVerified() {
        return businessOwnerEmailVerified;
    }

    public void setBusinessOwnerEmailVerified(boolean businessOwnerEmailVerified) {
        this.businessOwnerEmailVerified = businessOwnerEmailVerified;
    }

    public boolean isBusinessNIUIsVerified() {
        return businessNIUIsVerified;
    }

    public void setBusinessNIUIsVerified(boolean businessNIUIsVerified) {
        this.businessNIUIsVerified = businessNIUIsVerified;
    }

    public String getBusinessCurrency() {
        return businessCurrency;
    }

    public void setBusinessCurrency(String businessCurrency) {
        this.businessCurrency = businessCurrency;
    }

    public float getBusinessBalance() {
        return businessBalance;
    }

    public void setBusinessBalance(float businessBalance) {
        this.businessBalance = businessBalance;
    }

    public boolean isComplete(){

        if (this.businessName == null || this.businessNIU== null || this.businessOwnerName== null||
                this.businessOwnerEmail== null){
            return  false;
        }

        if (this.businessName.isEmpty() || this.businessNIU.isEmpty() || this.businessOwnerName.isEmpty() ||
        this.businessOwnerEmail.isEmpty()){
            return  false;
        }

        if (!verifyInput(this.businessOwnerEmail)){
            return false;
        }
        return true;
    }

    public boolean verifyInput(String content){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


        return Pattern.compile(regexPattern)
                .matcher(content)
                .matches();
    }
}
