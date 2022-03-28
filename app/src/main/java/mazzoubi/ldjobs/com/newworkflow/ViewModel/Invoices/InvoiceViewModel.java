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

import java.lang.reflect.Method;
import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceData;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class InvoiceViewModel extends ViewModel {

    public MutableLiveData<ArrayList<InvoiceModel>> listOfInvoices=new MutableLiveData<>();

    public void addInvoice(Activity c , InvoiceData invoiceData){
        if (invoiceData.getInvoiceAmount().isEmpty()){
            errorDialog(c,"الرجاء ادخال قيمة الفاتورة!");
        }else if (invoiceData.getCreatedByEmpId().isEmpty()){
            errorDialog(c,"الرجاء ادخال المستخدم الذي انشأ الفاتورة!");
        }else if (invoiceData.getInvType().isEmpty()){
            errorDialog(c,"الرجاء ادخال نوع الفاتورة!");
        }else if (invoiceData.getPointId().isEmpty()){
            errorDialog(c,"الرجاء ادخال العميل الذي انشأت له هذه الفاتورة!");
        }else {
            setProgressDialog(c);
            JSONObject jsonObject = new JSONObject();
            try {
//                jsonObject.put("", );
            }catch (Exception e){}


            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST, ClassAPIs.InsertInvoices, jsonObject
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        if (response.getString("response_state").equals("1")){
                            successDialog(c,response.getString("response_message"));
                        }else {
                            errorDialog(c,"خطأ في عملية الإضافة!"+"\n"+response.getString("response_message"));
                        }
                    }catch (Exception e){
                        errorDialog(c,"خطأ غير متوقع الرجاء اعادة المحاولة!"+"\n"+e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,"خطأ في عملية الوصول الى الخادم!"+"\n"+error.toString());
                }
            }));
        }
    }

    public void getInvoicesByClintId(Activity c , String clientId){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "5");
            jsonObject.put("Key" , clientId);
        }catch (Exception e){}
        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetInvoices, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    JSONArray jsonArray  = response.getJSONArray("data");
                    for (int i=0 ; i< jsonArray.length() ; i++){
                        InvoiceModel aa = new InvoiceModel();
                        aa.setCreatedDate(jsonArray.getJSONObject(i).getString("created_date"));
                        aa.setCreatedTime(jsonArray.getJSONObject(i).getString("created_time"));
                        aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString("created_by_user_id"));
                        aa.setInvType(jsonArray.getJSONObject(i).getString("inv_type"));
                        aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));
                        aa.setInvoiceId(jsonArray.getJSONObject(i).getString("invoice_id"));
                        aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString("invoice_no"));
                        aa.setClientId(jsonArray.getJSONObject(i).getString("point_id"));
                        aa.setCRM_ID(jsonArray.getJSONObject(i).getString("crm_id"));
                        aa.setInvoiceState(jsonArray.getJSONObject(i).getString("invoice_state"));
                        aa.setInvoicePaid(jsonArray.getJSONObject(i).getString("invoice_payed"));
                        aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));
                        aa.setClientId(jsonArray.getJSONObject(i).getString("client_id"));
                        aa.setClientName(jsonArray.getJSONObject(i).getString("client_name"));
                        aa.setClientPhone(jsonArray.getJSONObject(i).getString("client_phone"));
                        aa.setClientHolderName(jsonArray.getJSONObject(i).getString("holder_name"));

                        temp.add(aa);

                    }

                    listOfInvoices.setValue(temp);

                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع الرجاء اعادة المحاولة!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في عملية الوصول الى الخادم!"+"\n"+error.toString());
            }
        }));
    }

    public void getInvoicesByClintIdAndDate(Activity c , String clientId , String date){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "1");
            jsonObject.put("Key" , "1");
        }catch (Exception e){}
        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetInvoices, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    JSONArray jsonArray  = response.getJSONArray("data");
                    for (int i=0 ; i< jsonArray.length() ; i++){
                        InvoiceModel aa = new InvoiceModel();
                        aa.setClientId(jsonArray.getJSONObject(i).getString("created_date"));
                        aa.setClientName(jsonArray.getJSONObject(i).getString("created_time"));
                        aa.setClientPhone(jsonArray.getJSONObject(i).getString("created_by_user_id"));
                        aa.setInvoiceId(jsonArray.getJSONObject(i).getString("inv_type"));
                        aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));
                        aa.setInvoicePaid(jsonArray.getJSONObject(i).getString("invoice_id"));
                        aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString("invoice_no"));
                        aa.setCreatedByUserName(jsonArray.getJSONObject(i).getString("is_deleted"));
                        aa.setCreatedDate(jsonArray.getJSONObject(i).getString("point_id"));
                        aa.setCreatedTime(jsonArray.getJSONObject(i).getString("note"));
                        aa.setCRM_ID(jsonArray.getJSONObject(i).getString("crm_id"));
                        aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString("invoice_state"));
                        aa.setInvoiceState(jsonArray.getJSONObject(i).getString("invoice_payed"));
                        aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));
                        aa.setModifiedByUserId(jsonArray.getJSONObject(i).getString("client_id"));
                        aa.setModifiedByUserName(jsonArray.getJSONObject(i).getString("client_name"));
                        aa.setModifiedTime(jsonArray.getJSONObject(i).getString("client_phone"));
                        aa.setModifiedDate(jsonArray.getJSONObject(i).getString("holder_name"));

                        temp.add(aa);

                    }

                    listOfInvoices.setValue(temp);

                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع الرجاء اعادة المحاولة!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في عملية الوصول الى الخادم!"+"\n"+error.toString());
            }
        }));
    }

    public void getInvoicesByClintIdAndDate1(Activity c , String clientId , String date){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "1");
            jsonObject.put("name" , "1");
            jsonObject.put("id" , "1");
            jsonObject.put("getApplicationContext" , "1");
            jsonObject.put("Key" , "1");
            jsonObject.put("Key" , "1");
        }catch (Exception e){}
        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetInvoices, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    JSONArray jsonArray  = response.getJSONArray("data");
                    for (int i=0 ; i< jsonArray.length() ; i++){
                        InvoiceModel aa = new InvoiceModel();
                        aa.setClientId(jsonArray.getJSONObject(i).getString(""));
                        aa.setClientName(jsonArray.getJSONObject(i).getString(""));
                        aa.setClientPhone(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoiceId(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoicePaid(jsonArray.getJSONObject(i).getString(""));
                        aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString(""));
                        aa.setCreatedByUserName(jsonArray.getJSONObject(i).getString(""));
                        aa.setCreatedDate(jsonArray.getJSONObject(i).getString(""));
                        aa.setCreatedTime(jsonArray.getJSONObject(i).getString(""));
                        aa.setCRM_ID(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoiceState(jsonArray.getJSONObject(i).getString(""));
                        aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString(""));
                        aa.setModifiedByUserId(jsonArray.getJSONObject(i).getString(""));
                        aa.setModifiedByUserName(jsonArray.getJSONObject(i).getString(""));
                        aa.setModifiedTime(jsonArray.getJSONObject(i).getString(""));
                        aa.setModifiedDate(jsonArray.getJSONObject(i).getString(""));

                        temp.add(aa);

                    }

                    listOfInvoices.setValue(temp);

                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع الرجاء اعادة المحاولة!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في عملية الوصول الى الخادم!"+"\n"+error.toString());
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
