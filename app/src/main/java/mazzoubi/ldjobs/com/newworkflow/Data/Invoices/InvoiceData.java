package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

import org.json.JSONObject;

public class InvoiceData {
    private String createdDate="";
    private String createdTime="";
    private String createdByEmpId="";
    private String invType="";
    private String invoiceAmount="";
    private String invoiceId="";
    private String invoiceNo="";
    private String isDeleted="";
    private String pointId="";

    public InvoiceData() { }

    public InvoiceData(String createdDate, String createdTime, String createdByEmpId, String invType,
                       String invoiceAmount, String invoiceId, String invoiceNo, String isDeleted,
                       String pointId) {
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.createdByEmpId = createdByEmpId;
        this.invType = invType;
        this.invoiceAmount = invoiceAmount;
        this.invoiceId = invoiceId;
        this.invoiceNo = invoiceNo;
        this.isDeleted = isDeleted;
        this.pointId = pointId;
    }


    public InvoiceData(JSONObject jsonObject) throws Exception{
           this.createdDate = jsonObject.getString("createdDate");
           this.createdTime = jsonObject.getString("createdTime");
           this.createdByEmpId = jsonObject.getString("createdByEmpId");
           this.invType = jsonObject.getString("invType");
           this.invoiceAmount = jsonObject.getString("invoiceAmount");
           this.invoiceId = jsonObject.getString("invoiceId");
           this.invoiceNo = jsonObject.getString("invoiceNo");
           this.isDeleted = jsonObject.getString("isDeleted");
           this.pointId = jsonObject.getString("pointId");
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

    public String getCreatedByEmpId() {
        return createdByEmpId;
    }

    public void setCreatedByEmpId(String createdByEmpId) {
        this.createdByEmpId = createdByEmpId;
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

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
}
