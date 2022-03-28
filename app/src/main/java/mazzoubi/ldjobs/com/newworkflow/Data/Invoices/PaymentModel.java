package mazzoubi.ldjobs.com.newworkflow.Data.Invoices;

import org.json.JSONObject;

public class PaymentModel {
        private String invoice_id="";
        private String user_id="";
        private String user_name="";
        private String created_date="";
        private String created_time="";
        private String type="";
        private String amount="";
        private String payment_type="";
        private String is_deleted="";
        private String payment_id="";
        private String note="";



    public PaymentModel() { }

    public PaymentModel(String invoice_id, String user_id, String user_name, String created_date,
                        String created_time, String type, String amount,
                        String payment_type, String is_deleted, String payment_id, String note) {
        this.invoice_id = invoice_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.created_date = created_date;
        this.created_time = created_time;
        this.type = type;
        this.amount = amount;
        this.payment_type = payment_type;
        this.is_deleted = is_deleted;
        this.payment_id = payment_id;
        this.note = note;
    }

    public PaymentModel (JSONObject jsonObject) throws Exception{
        this.invoice_id = jsonObject.getString("invoice_id");
        this.user_id = jsonObject.getString("user_id");
        this.user_name = jsonObject.getString("user_name");
        this.created_date = jsonObject.getString("created_date");
        this.created_time = jsonObject.getString("created_time");
        this.type = jsonObject.getString("type");
        this.amount = jsonObject.getString("amount");
        this.payment_type = jsonObject.getString("payment_type");
        this.is_deleted = jsonObject.getString("is_deleted");
        this.payment_id = jsonObject.getString("payment_id");
        this.note = jsonObject.getString("note");

    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
