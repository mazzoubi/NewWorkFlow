package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class PaymentViewModel extends ViewModel {

    public MutableLiveData<ArrayList<PaymentModel>>listOfPayments = new MutableLiveData<>();

    public void addNewPayment(Activity c , PaymentModel paymentModel){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InvoiceId" ,paymentModel.getInvoice_id() );
            jsonObject.put("UserId" ,paymentModel.getUser_id() );
            jsonObject.put("Type" ,paymentModel.getType() );
            jsonObject.put("Amount" ,paymentModel.getAmount() );
            jsonObject.put("PaymentType" ,paymentModel.getPayment_type() );
            jsonObject.put("Note" ,paymentModel.getNote() );
        }catch (Exception e){}

        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.InsertPayments, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("response_state").equals("1")){
                        successDialog(c,response.getString("response_message"));
                    }else {
                        errorDialog(c,response.getString("response_message"));
                    }
                }catch (Exception e){
                    errorDialog(c,e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorDialog(c,error.toString());
            }
        }));
    }

    public void addNewInvoicesPayments(Activity c,String clientID,String amount,String paymentType,
                                       String type ,String bankId){

        try {
            JSONObject j = new JSONObject();
            j.put("ClientID" , clientID);
            j.put("UserId" , UserInfo.getUser(c).getId());
            j.put("Type" ,type );
            j.put("Amount" , amount);
            j.put("PaymentType" , paymentType);
            j.put("BankId" , bankId);
            j.put("Note" , "");

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.InsertPayments, j, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray =response.getJSONArray("Response");
                        String aa= "تم تسديد الفواتير التالية" +"\n" ;
                        for (int i=0 ; i< jsonArray.length();i++){
                            aa+="الفاتورة رقم: "+ jsonArray.getJSONObject(i).getString("invoice_no")
                                    +" " +jsonArray.getJSONObject(i).getString("response_message");
                        }
                        successDialog(c,response.toString());
                    }catch (Exception e){
                        errorDialog(c,e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorDialog(c,error.toString());
                }
            }));
        }catch (Exception e){}

    }

    public void getPayments(Activity c){
        listOfPayments = new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype", "1");
            jsonObject.put("Key", "");
        }catch (Exception e){}
        setProgressDialog(c);
        Volley.newRequestQueue(c).add(new JsonObjectRequest(
                Request.Method.POST, ClassAPIs.GetPayments, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<PaymentModel> temp = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0 ; i<jsonArray.length();i++){
                        temp.add(new PaymentModel(jsonArray.getJSONObject(i)));
                    }
                    listOfPayments.setValue(temp);
                }catch (Exception e){
                    errorDialog(c,e.toString());
                }
                dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorDialog(c,error.toString());
            }
        }));
    }

    public void getPaymentsByFilter(Activity c, String dateFrom , String dateTo,
                                    String userId , String clientId ){
        JSONObject jsonObject = new JSONObject();
        listOfPayments=new MutableLiveData<>();

        String query = " and Convert(date, pay.created_date, 23) " +
                "between" +
                " Convert(date, '"+dateFrom+"', 23) and Convert(date, '"+dateTo+"', 23)" ;
        try {
            jsonObject.put("type","1");
            jsonObject.put("dateFrom",dateFrom);
            jsonObject.put("dateTp",dateTo);

            if (userId.isEmpty()&&clientId.isEmpty()){

            }else if (!userId.isEmpty()&&clientId.isEmpty()){
                jsonObject.put("userId",userId);
            }if (userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("clientId",clientId);
            }if (!userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("userId",userId);
                jsonObject.put("clientId",userId);
            }

            setProgressDialog(c);
            ArrayList<PaymentModel> temp = new ArrayList<>();
            JSONObject jj = new JSONObject();
            jj.put("Datatype","2");
            jj.put("Key","");
            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST
                    , ClassAPIs.GetPayments, jj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("response_state").equals("1")){
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i=0 ; i <jsonArray.length();i++){
                                PaymentModel a=new PaymentModel();
                                a.setInvoice_id(jsonArray.getJSONObject(i).getString("invoice_id"));
                                a.setUser_id(jsonArray.getJSONObject(i).getString("user_id"));
                                a.setCreated_date(jsonArray.getJSONObject(i).getString("created_date"));
                                a.setCreated_time(jsonArray.getJSONObject(i).getString("created_time"));
                                a.setType(jsonArray.getJSONObject(i).getString("type"));
                                a.setAmount(jsonArray.getJSONObject(i).getString("invoice_id"));
                                a.setPayment_type(jsonArray.getJSONObject(i).getString("payment_type"));
                                a.setIs_deleted(jsonArray.getJSONObject(i).getString("is_deleted"));
                                a.setPayment_id(jsonArray.getJSONObject(i).getString("payment_id"));
                                a.setNote(jsonArray.getJSONObject(i).getString("note"));

                                temp.add(a);

                            }
                            dismissProgressDialog();
                            listOfPayments.setValue(temp);
                        }else {
                            errorDialog(c,response.getString("response_message"));
                        }
                    }catch (Exception e){
                        errorDialog(c,e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorDialog(c, error.toString());
                }
            }));
        }catch (Exception e){
            errorDialog(c,e.toString());
        }


    }

    public void getBankPaymentsByFilter(Activity c, String dateFrom , String dateTo,
                                    String userId , String clientId ,String bankId){
        listOfPayments=new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type","1");
            jsonObject.put("dateFrom",dateFrom);
            jsonObject.put("dateTo",dateTo);
            if (!bankId.isEmpty()){
                jsonObject.put("bankId",bankId);
            }
            if (userId.isEmpty()&&clientId.isEmpty()){

            }else if (!userId.isEmpty()&&clientId.isEmpty()){
                jsonObject.put("userId",userId);
            }if (userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("clientId",clientId);
            }if (!userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("userId",userId);
                jsonObject.put("clientId",userId);
            }

            setProgressDialog(c);
            ArrayList<PaymentModel> temp = new ArrayList<>();
            JSONObject jj = new JSONObject();
            jj.put("Datatype","1");
            jj.put("Key","");
            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST
                    , ClassAPIs.GetPayments, jj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("response_state").equals("1")){
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i=0 ; i <jsonArray.length();i++){
                                PaymentModel a=new PaymentModel();
                                a.setInvoice_id(jsonArray.getJSONObject(i).getString("invoice_id"));
                                a.setUser_id(jsonArray.getJSONObject(i).getString("user_id"));
                                a.setCreated_date(jsonArray.getJSONObject(i).getString("created_date"));
                                a.setCreated_time(jsonArray.getJSONObject(i).getString("created_time"));
                                a.setType(jsonArray.getJSONObject(i).getString("type"));
                                a.setAmount(jsonArray.getJSONObject(i).getString("amount"));
                                a.setPayment_type(jsonArray.getJSONObject(i).getString("payment_type"));
                                a.setIs_deleted(jsonArray.getJSONObject(i).getString("is_deleted"));
                                a.setPayment_id(jsonArray.getJSONObject(i).getString("payment_id"));
                                a.setNote(jsonArray.getJSONObject(i).getString("note"));

                                temp.add(a);

                            }
                            dismissProgressDialog();
                            listOfPayments.setValue(temp);
                        }else {
                            errorDialog(c,response.getString("response_message"));
                        }
                    }catch (Exception e){
                        errorDialog(c,e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorDialog(c, error.toString());
                }
            }));
        }catch (Exception e){
            errorDialog(c,e.toString());
        }

    }

    public void getInvoicePayments(Activity c , String dateFrom ,String dateTo, String userId ,
                                   String clientId , String paymentType){

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("dateFrom",dateFrom);
            jsonObject.put("dateTo",dateTo);
            jsonObject.put("userId",userId);
            jsonObject.put("clientId",clientId);
            jsonObject.put("paymentType",paymentType);
        }catch (Exception e){}


        {


            JSONArray jsonArray = new JSONArray();
            try {
                for (int i=0 ; i<jsonArray.length();i++){
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                    try{}catch (Exception e){}
                }
            }catch (Exception e){}
        }

    }

    public void getPaymentByInvoiceId(Activity c , String invId){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("invoiceId",invId);
        }catch (Exception e){}


    }

    public void deletePayment(Activity c , String paymentId,String userId){

    }

    CustomProgressDialog progressDialog;
    void successDialog(Activity c , String message){
        CustomSuccessDialog a = new  CustomSuccessDialog(c,message);
        try {
            a.show();
        }catch (Exception e){

        }
    }
    void errorDialog(Activity c , String message) {
        CustomErrorDialog a = new CustomErrorDialog(c, message);
        try {
            a.show();
        }catch (Exception e){

        }
    }

    void setProgressDialog(Activity c){
        progressDialog=new CustomProgressDialog(c);
        try {
            progressDialog.show();
        }catch (Exception e){

        }
    }
    void dismissProgressDialog(){
        progressDialog.dismiss();
    }




}
