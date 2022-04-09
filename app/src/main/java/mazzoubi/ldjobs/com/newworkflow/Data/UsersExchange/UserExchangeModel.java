package mazzoubi.ldjobs.com.newworkflow.Data.UsersExchange;

public class UserExchangeModel {
    private String id= "" ;
    private String fromUserId= "" ;
    private String fromUserName= "" ;
    private String toUserId= "" ;
    private String toUserName= "" ;
    private String date= "" ;
    private String time= "" ;
    private String amount= "" ;
    private String state= "" ;
    private String acceptDate= "" ;
    private String acceptTime= "" ;
    private String notes= "" ;


    public UserExchangeModel() {
    }

    public UserExchangeModel(String id, String fromUserId, String fromUserName, String toUserId, String toUserName, String date,
                             String time, String amount, String state, String acceptDate, String acceptTime,String notes) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.state = state;
        this.acceptDate = acceptDate;
        this.acceptTime = acceptTime;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }
}
