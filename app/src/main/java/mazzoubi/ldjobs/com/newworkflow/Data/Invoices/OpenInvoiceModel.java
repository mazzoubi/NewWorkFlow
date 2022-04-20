package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

public class OpenInvoiceModel {
    private String  clientNam= "" ;
    private String  clientId= "" ;
    private String  userName= "" ;
    private String  userId= "" ;
    private String  invoiceId= "" ;
    private String  invoiceNo= "" ;
    private String  invoiceAmount= "" ;
    private String  date= "" ;
    private String  time= "" ;
    private String  sharedBody= "" ;
    private String  state= "" ;
    private String  id= "" ;
    private String  chargeState= "" ;
    private String  note= "" ;
    private String  pointDept= "" ;
    private String  invUnpaid= "" ;




    public OpenInvoiceModel() { }

    public OpenInvoiceModel(String clientNam, String clientId, String userName,
                            String userId, String invoiceId, String invoiceNo,
                            String invoiceAmount, String date, String time, String sharedBody,
                            String state,String id,String chargeState,String note,
                            String pointDept,String invUnpaid) {
        this.clientNam = clientNam;
        this.clientId = clientId;
        this.userName = userName;
        this.userId = userId;
        this.invoiceId = invoiceId;
        this.invoiceNo = invoiceNo;
        this.invoiceAmount = invoiceAmount;
        this.date = date;
        this.time = time;
        this.sharedBody = sharedBody;
        this.state = state;
        this.id = id;
        this.chargeState = chargeState;
        this.note = note;
        this.pointDept = pointDept;
        this.invUnpaid = invUnpaid;
    }

    public String getPointDept() {
        return pointDept;
    }

    public void setPointDept(String pointDept) {
        this.pointDept = pointDept;
    }

    public String getInvUnpaid() {
        return invUnpaid;
    }

    public void setInvUnpaid(String invUnpaid) {
        this.invUnpaid = invUnpaid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getChargeState() {
        return chargeState;
    }

    public void setChargeState(String chargeState) {
        this.chargeState = chargeState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientNam() {
        return clientNam;
    }

    public void setClientNam(String clientNam) {
        this.clientNam = clientNam;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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

    public String getSharedBody() {
        return sharedBody;
    }

    public void setSharedBody(String sharedBody) {
        this.sharedBody = sharedBody;
    }
}
