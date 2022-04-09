package mazzoubi.ldjobs.com.newworkflow.Data.Banks;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

public class BankTransferModel extends ViewModel {

    private String id="";
    private String bankId="";
    private String bankName="";
    private String image="";
    private String amount="";
    private String createdDate="";
    private String createdTime="";
    private String createdByUserId="";
    private String acceptedByUserId="";
    private String acceptedDate="";
    private String acceptedTime="";
    private String state=""; // (0) معلقة   (1) مقبولة  (-1) مرفوضة

    public BankTransferModel() {
    }

    public BankTransferModel(String id, String bankId, String bankName, String image,
                             String amount, String createdDate, String createdTime, String createdByUserId,
                             String acceptedByUserId, String acceptedDate, String acceptedTime, String state) {
        this.id = id;
        this.bankId = bankId;
        this.bankName = bankName;
        this.image = image;
        this.amount = amount;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.createdByUserId = createdByUserId;
        this.acceptedByUserId = acceptedByUserId;
        this.acceptedDate = acceptedDate;
        this.acceptedTime = acceptedTime;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getAcceptedByUserId() {
        return acceptedByUserId;
    }

    public void setAcceptedByUserId(String acceptedByUserId) {
        this.acceptedByUserId = acceptedByUserId;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
