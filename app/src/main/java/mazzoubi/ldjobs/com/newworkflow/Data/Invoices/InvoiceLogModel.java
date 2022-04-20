package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

public class InvoiceLogModel {
    private String id ="";
    private String userId ="";
    private String userName = "" ;
    private String date ="";
    private String time ="";
    private String clientId ="";
    private String clientName ="";
    private String invoiceId ="";
    private String description ="";
    private String invoice_state_after_trans ="";
    private String is_deleted ="";
    private String type ="";         // 1- created    2-add payment   3-delete payment   4-deleted   5- updated

    public InvoiceLogModel() {}

    public InvoiceLogModel(String id, String userId, String userName, String date, String time,
                           String clientId, String clientName, String invoiceId, String description,
                           String type , String invoice_state_after_trans,String is_deleted) {
        this.is_deleted=is_deleted;
        this.invoice_state_after_trans=invoice_state_after_trans;
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.date = date;
        this.time = time;
        this.clientId = clientId;
        this.clientName = clientName;
        this.invoiceId = invoiceId;
        this.description = description;
        this.type = type;
    }

    public String getInvoice_state_after_trans() {
        return invoice_state_after_trans;
    }

    public void setInvoice_state_after_trans(String invoice_state_after_trans) {
        this.invoice_state_after_trans = invoice_state_after_trans;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
