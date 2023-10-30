package xyz.thisisjames.boulevard.android.runz.model.data;

public class Invoice {

    String invoiceAmount,invoiceRecipientName,InvoiceRecipientNumber,invoiceReferenceCode,
            invoiceReference, invoiceID;


    String invoicePaymentStatus, invoiceTelcoReference ;

    Long invoiceSendTime, invoicePaidTime,  invoiceExpiryDate;

    int invoiceTransferAmount,invoiceCollectionFees, invoiceTotalPayable;


    public Invoice() {
    }

    public Invoice(String invoiceAmount, String invoiceRecipientName, String invoiceRecipientNumber, String invoiceReferenceCode, String invoiceReference, String invoiceID, String invoicePaymentStatus, String invoiceTelcoReference, Long invoiceSendTime, Long invoicePaidTime, Long invoiceExpiryDate, int invoiceTransferAmount, int invoiceCollectionFees, int invoiceTotalPayable) {
        this.invoiceAmount = invoiceAmount;
        this.invoiceRecipientName = invoiceRecipientName;
        InvoiceRecipientNumber = invoiceRecipientNumber;
        this.invoiceReferenceCode = invoiceReferenceCode;
        this.invoiceReference = invoiceReference;
        this.invoiceID = invoiceID;
        this.invoicePaymentStatus = invoicePaymentStatus;
        this.invoiceTelcoReference = invoiceTelcoReference;
        this.invoiceSendTime = invoiceSendTime;
        this.invoicePaidTime = invoicePaidTime;
        this.invoiceExpiryDate = invoiceExpiryDate;
        this.invoiceTransferAmount = invoiceTransferAmount;
        this.invoiceCollectionFees = invoiceCollectionFees;
        this.invoiceTotalPayable = invoiceTotalPayable;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceRecipientName() {
        return invoiceRecipientName;
    }

    public void setInvoiceRecipientName(String invoiceRecipientName) {
        this.invoiceRecipientName = invoiceRecipientName;
    }

    public String getInvoiceRecipientNumber() {
        return InvoiceRecipientNumber;
    }

    public void setInvoiceRecipientNumber(String invoiceRecipientNumber) {
        InvoiceRecipientNumber = invoiceRecipientNumber;
    }

    public String getInvoiceReferenceCode() {
        return invoiceReferenceCode;
    }

    public void setInvoiceReferenceCode(String invoiceReferenceCode) {
        this.invoiceReferenceCode = invoiceReferenceCode;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }


    public Long getInvoiceSendTime() {
        return invoiceSendTime;
    }

    public void setInvoiceSendTime(Long invoiceSendTime) {
        this.invoiceSendTime = invoiceSendTime;
    }

    public Long getInvoicePaidTime() {
        return invoicePaidTime;
    }

    public void setInvoicePaidTime(Long invoicePaidTime) {
        this.invoicePaidTime = invoicePaidTime;
    }


    public String getInvoicePaymentStatus() {
        return invoicePaymentStatus;
    }

    public void setInvoicePaymentStatus(String invoicePaymentStatus) {
        this.invoicePaymentStatus = invoicePaymentStatus;
    }

    public String getInvoiceTelcoReference() {
        return invoiceTelcoReference;
    }

    public void setInvoiceTelcoReference(String invoiceTelcoReference) {
        this.invoiceTelcoReference = invoiceTelcoReference;
    }

    public int getInvoiceTransferAmount() {
        return invoiceTransferAmount;
    }

    public void setInvoiceTransferAmount(int invoiceTransferAmount) {
        this.invoiceTransferAmount = invoiceTransferAmount;
    }

    public int getInvoiceCollectionFees() {
        return invoiceCollectionFees;
    }

    public void setInvoiceCollectionFees(int invoiceCollectionFees) {
        this.invoiceCollectionFees = invoiceCollectionFees;
    }

    public int getInvoiceTotalPayable() {
        return invoiceTotalPayable;
    }

    public void setInvoiceTotalPayable(int invoiceTotalPayable) {
        this.invoiceTotalPayable = invoiceTotalPayable;
    }



    public Long getInvoiceExpiryDate() {
        return invoiceExpiryDate;
    }

    public void setInvoiceExpiryDate(Long invoiceExpiryDate) {
        this.invoiceExpiryDate = invoiceExpiryDate;
    }
}
