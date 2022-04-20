package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

public class InvoiceModel {

    private String CRM_ID="";
    private String invoiceState=""; // 1- open     2- subPaid     3- closed     4- overpaid
    private String invoiceNumber= "" ;
    private String invoiceId= "" ;
    private String invoiceAmount= "" ;
    private String invoicePaid= "" ;
    private String invoiceUnpaid= "" ;
    private String clientId= "" ;
    private String clientName= "" ;
    private String clientPhone= "" ;
    private String createdByUserName= "" ;
    private String createdByUserId= "" ;
    private String createdDate= "" ;
    private String createdTime= "" ;
    private String modifiedByUserName= "" ;
    private String modifiedByUserId= "" ;
    private String modifiedTime= "" ;
    private String modifiedDate= "" ;
    private String invType= "" ;
    private String clientHolderName= "" ;
    private String clientHolderId= "" ;
    private String isDeleted= "" ;
    private String note= "" ;

    public InvoiceModel() { }

    public InvoiceModel(String CRM_ID, String invoiceState, String invoiceNumber,
                        String invoiceId, String invoiceAmount, String invoicePaid,
                        String invoiceUnpaid, String clientId, String clientName, String clientPhone,
                        String createdByUserName, String createdByUserId, String createdDate, String createdTime,
                        String modifiedByUserName, String modifiedByUserId, String modifiedTime, String modifiedDate,
                        String invType , String clientHolderName,String clientHolderId,String isDeleted,String note) {
        this.CRM_ID = CRM_ID;
        this.invoiceState = invoiceState;
        this.invoiceNumber = invoiceNumber;
        this.invoiceId = invoiceId;
        this.invoiceAmount = invoiceAmount;
        this.invoicePaid = invoicePaid;
        this.invoiceUnpaid = invoiceUnpaid;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.createdByUserName = createdByUserName;
        this.createdByUserId = createdByUserId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.modifiedByUserName = modifiedByUserName;
        this.modifiedByUserId = modifiedByUserId;
        this.modifiedTime = modifiedTime;
        this.modifiedDate = modifiedDate;
        this.invType = invType;
        this.clientHolderName = clientHolderName;
        this.clientHolderId = clientHolderId;
        this.isDeleted = isDeleted;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getClientHolderId() {
        return clientHolderId;
    }

    public void setClientHolderId(String clientHolderId) {
        this.clientHolderId = clientHolderId;
    }

    public String getClientHolderName() {
        return clientHolderName;
    }

    public void setClientHolderName(String clientHolder) {
        this.clientHolderName = clientHolder;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getCRM_ID() {
        return CRM_ID;
    }

    public void setCRM_ID(String CRM_ID) {
        this.CRM_ID = CRM_ID;
    }

    public String getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(String invoiceState) {
        this.invoiceState = invoiceState;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoicePaid() {
        return invoicePaid;
    }

    public void setInvoicePaid(String invoicePaid) {
        this.invoicePaid = invoicePaid;
    }

    public String getInvoiceUnpaid() {
        return invoiceUnpaid;
    }

    public void setInvoiceUnpaid(String invoiceUnpaid) {
        this.invoiceUnpaid = invoiceUnpaid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
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

    public String getModifiedByUserName() {
        return modifiedByUserName;
    }

    public void setModifiedByUserName(String modifiedByUserName) {
        this.modifiedByUserName = modifiedByUserName;
    }

    public String getModifiedByUserId() {
        return modifiedByUserId;
    }

    public void setModifiedByUserId(String modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    //invoices
//    private String createdDate="";
//    private String createdTime="";
//    private String createdByEmpId="";
//    private String invType="";
//    private String invoiceAmount="";
//    private String invoiceId="";
//    private String invoiceNo="";
//    private String isDeleted="";
//    private String pointId="";



    //invoicePayment
//    private String  invoiceId= "" ;
//    private String  userId= "" ;
//    private String  date= "" ;
//    private String  time= "" ;
//    private String  type= "" ;  // 1- add    2- remove
//    private String  amount= "" ;
//    private String  paymentType= "" ; // 1- cash   2- bank  3- check
//    private String  isDeleted= "" ;


    //invoiceLog
//    private String  invoiceId= "" ;
//    private String  userId= "" ;
//    private String  transType= "" ; // 1- created    2- removed    3- updateInfo   4- addPayment   5- removePayment
//    private String  invoiceStateAfterTrans= "" ;
//    private String  invoicePaymentsAfterTrans= "" ;
//    private String  date= "" ;
//    private String  time= "" ;
//
//
//
//

    //cashFlow
//    private String  userId= "" ;
//    private String  cashAmount = "" ;
//    private String  userDebtBefore= "" ;
//    private String  userDeptAfter= "" ;
//    private String  transType= "" ;  // 1- (+)    2- (-)
//    private String  date= "" ;
//    private String  time= "" ;
//    private String  page= "" ;
//    private String  note= "" ;


//    private String CRM_ID="";
//    private String InvoiceState="";
//    private String client_email="";
//    private String client_id="";
//    private String createdAtDate="";
//    private String createdAtTime="";
//    private String createdByEmpId="";
//    private String createdByEmpName="";
//    private String daftara_sync="";
//    private String date="";
//    private String device="";
//    private String invType="";
//    private String invoiceAmount="";
//    private String invoiceId="";
//    private String invoiceNo="";
//    private String invoicePayment="";
//    private String invoiceUnpaid="";
//    private String isDeleted="";
//    private String modifiedAtDate="";
//    private String modifiedAtTime="";
//    private String pointHolder="";
//    private String pointId="";
//    private String pointName="";
//    private String staff_id="";
//    private String state="";
//    private String transId="";




}
