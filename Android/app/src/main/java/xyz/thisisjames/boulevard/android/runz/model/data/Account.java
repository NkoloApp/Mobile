package xyz.thisisjames.boulevard.android.runz.model.data;

public class Account {

    String accountName, accountIdentifyer, accountType, accountStatus;

    public Account() {
        accountStatus = "unverified";
    }

    public Account(String accountName, String accountIdentifyer, String accountImage,
                   String accountStatus) {
        this.accountName = accountName;
        this.accountIdentifyer = accountIdentifyer;
        this.accountType = accountImage;
        this.accountStatus = accountStatus;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountIdentifyer() {
        return accountIdentifyer;
    }

    public void setAccountIdentifyer(String accountIdentifyer) {
        this.accountIdentifyer = accountIdentifyer;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


    public boolean isComplete(){
        if (this.accountIdentifyer.isEmpty() || this.accountType.isEmpty() || this.accountStatus.isEmpty()
        || this.accountName.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
