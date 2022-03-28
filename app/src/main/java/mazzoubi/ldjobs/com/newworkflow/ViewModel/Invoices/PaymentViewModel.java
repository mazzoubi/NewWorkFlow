package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class PaymentViewModel {

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
