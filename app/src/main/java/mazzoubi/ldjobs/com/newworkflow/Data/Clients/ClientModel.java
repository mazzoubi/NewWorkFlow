package mazzoubi.ldjobs.com.newworkflow.Data.Clients;

public class ClientModel {
    private String clientName="";
    private String clientId="autoIncrement";
    private String clientPhone="";
    private String clientEmail="";
    private String holderId="";
    private String holderName="";
    private String max_debt="500";
    private String max_inv="2";
    private String constraint="1";
    private String state="1";
    private String dateOfRegister="";
    private String crm_id="";
    private String password="";
    private String location="";
    private String passcode="";

    public ClientModel() { }

    public ClientModel(String clientName, String clientId, String clientPhone, String clientEmail, String holderId, String holderName, String max_debt, String max_inv, String constraint, String state, String dateOfRegister, String crm_id, String password, String location, String passcode) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.holderId = holderId;
        this.holderName = holderName;
        this.max_debt = max_debt;
        this.max_inv = max_inv;
        this.constraint = constraint;
        this.state = state;
        this.dateOfRegister = dateOfRegister;
        this.crm_id = crm_id;
        this.password = password;
        this.location = location;
        this.passcode = passcode;
    }

    public String getCrm_id() {
        return crm_id;
    }

    public void setCrm_id(String crm_id) {
        this.crm_id = crm_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getMax_debt() {
        return max_debt;
    }

    public void setMax_debt(String max_debt) {
        this.max_debt = max_debt;
    }

    public String getMax_inv() {
        return max_inv;
    }

    public void setMax_inv(String max_inv) {
        this.max_inv = max_inv;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(String dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }
}
