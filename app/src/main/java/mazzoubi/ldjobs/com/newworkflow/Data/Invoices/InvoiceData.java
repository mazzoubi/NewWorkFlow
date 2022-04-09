package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

import org.json.JSONObject;

public class InvoiceData {
    private String createdDate="";
    private String createdTime="";
    private String createdByUserId="";
    private String invType="";
    private String invoiceAmount="";
    private String invoiceId="";
    private String invoiceNo="";
    private String isDeleted="";
    private String clientId="";

    public InvoiceData() { }

    public InvoiceData(String createdDate, String createdTime,
                       String createdByUserId, String invType, String invoiceAmount,
                       String invoiceId, String invoiceNo, String isDeleted, String clientId) {
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.createdByUserId = createdByUserId;
        this.invType = invType;
        this.invoiceAmount = invoiceAmount;
        this.invoiceId = invoiceId;
        this.invoiceNo = invoiceNo;
        this.isDeleted = isDeleted;
        this.clientId = clientId;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
