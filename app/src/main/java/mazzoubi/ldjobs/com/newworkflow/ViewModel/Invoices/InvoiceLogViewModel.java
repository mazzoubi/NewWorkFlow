package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;

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

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceLogModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class InvoiceLogViewModel extends ViewModel {
    public MutableLiveData<ArrayList<InvoiceLogModel>> listOfLog = new MutableLiveData<>();


    public void getLogs(Activity c, String invoiceId){
        ArrayList<InvoiceLogModel> temp = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","1");
            jsonObject.put("Key",invoiceId);
        }catch (Exception e){}

        try {
            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST
                    , ClassAPIs.GetInvoicesLog, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i=0 ; i< jsonArray.length();i++){
                            InvoiceLogModel a = new InvoiceLogModel();
                            a.setInvoiceId("invoice_id");
                            a.setUserId("user_id");
                            a.setType("trans_type");
                            a.setInvoice_state_after_trans("invoice_state_after_trans");
                            a.setDate("created_date");
                            a.setTime("created_time");
                            a.setDescription("note");
                            a.setId("log_id");
                            a.setIs_deleted("is_deleted");
                            try {
                                a.setUserName("user_name");
                            }catch (Exception e){
                                a.setUserName("");
                            }
                            temp.add(a);
                        }
                        listOfLog.setValue(temp);
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
