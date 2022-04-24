package mazzoubi.ldjobs.com.newworkflow.ViewModel.Permissions;

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

import mazzoubi.ldjobs.com.newworkflow.Data.Permissions.classPermissions;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class PermissionViewModel extends ViewModel {
    public MutableLiveData<classPermissions> permission = new MutableLiveData<>();
    public MutableLiveData<ArrayList<classPermissions>> listOfUserPermission = new MutableLiveData<>();

    public void getPermissions(Activity c){
        listOfUserPermission = new MutableLiveData<>();
        ArrayList<classPermissions> temp = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","1");
            jsonObject.put("Key","");

            setProgressDialog(c);
            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.GetPermessions, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        if (response.getString("response_state").equals("1")){
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray.length()!=0){
                                for (int i=0 ; i<jsonArray.length();i++){
                                    classPermissions a = new classPermissions();
                                    try{a.adb=jsonArray.getJSONObject(i).getString("adb");}catch(Exception e){}
                                    try{a.bank=jsonArray.getJSONObject(i).getString("bank");}catch(Exception e){}
                                    try{a.charge=jsonArray.getJSONObject(i).getString("charge");}catch(Exception e){}
                                    try{a.compare=jsonArray.getJSONObject(i).getString("compare");}catch(Exception e){}
                                    try{a.emp_id=jsonArray.getJSONObject(i).getString("emp_id");}catch(Exception e){}
                                    try{a.emp_name=jsonArray.getJSONObject(i).getString("emp_name");}catch(Exception e){}
                                    try{a.exchange=jsonArray.getJSONObject(i).getString("exchange");}catch(Exception e){}
                                    try{a.expenses=jsonArray.getJSONObject(i).getString("expenses");}catch(Exception e){}
                                    try{a.ezdist=jsonArray.getJSONObject(i).getString("ezdist");}catch(Exception e){}
                                    try{a.fingerprint=jsonArray.getJSONObject(i).getString("fingerprint");}catch(Exception e){}
                                    try{a.manager=jsonArray.getJSONObject(i).getString("manager");}catch(Exception e){}
                                    try{a.mgmt=jsonArray.getJSONObject(i).getString("mgmt");}catch(Exception e){}
                                    try{a.new_client=jsonArray.getJSONObject(i).getString("new_client");}catch(Exception e){}
                                    try{a.new_finance=jsonArray.getJSONObject(i).getString("new_finance");}catch(Exception e){}
                                    try{a.new_visit_qr=jsonArray.getJSONObject(i).getString("new_visit_qr");}catch(Exception e){}
                                    try{a.no_qr=jsonArray.getJSONObject(i).getString("no_qr");}catch(Exception e){}
                                    try{a.open_invoice=jsonArray.getJSONObject(i).getString("open_invoice");}catch(Exception e){}
                                    try{a.stock11=jsonArray.getJSONObject(i).getString("stock11");}catch(Exception e){}
                                    try{a.stock2=jsonArray.getJSONObject(i).getString("stock2");}catch(Exception e){}
                                    try{a.view_exchange=jsonArray.getJSONObject(i).getString("view_exchange");}catch(Exception e){}
                                    try{a.view_finance=jsonArray.getJSONObject(i).getString("view_finance");}catch(Exception e){}
                                    try{a.view_fingerprint=jsonArray.getJSONObject(i).getString("view_fingerprint");}catch(Exception e){}
                                    try{a.view_notification=jsonArray.getJSONObject(i).getString("view_notification");}catch(Exception e){}
                                    try{a.view_open_invoice=jsonArray.getJSONObject(i).getString("view_open_invoice");}catch(Exception e){}
                                    try{a.view_visit=jsonArray.getJSONObject(i).getString("view_visit");}catch(Exception e){}
                                    temp.add(a);
                                }
                                listOfUserPermission.setValue(temp);
                            }else {
                                errorDialog(c,"خطأ في عملية جلب الصلاحيات الرجاء المحاولة مرة اخرى!");
                            }
                        }else {
                         errorDialog(c, response.getString("response_state"));
                        }
                    }catch (Exception e){
                        errorDialog(c,e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,error.toString());
                }
            }));
        }catch (Exception e){
            errorDialog(c,e.toString());
        }
    }

    public void getPermissionsByUserId(Activity c,String userId){
        permission = new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try {
            String query = " and emp_id= "+userId ;
            jsonObject.put("Datatype","1");
            jsonObject.put("Key",query);

            setProgressDialog(c);
            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.GetPermessions, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        if (response.getString("response_state").equals("1")){
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray.length()!=0){
                                classPermissions a = new classPermissions();
                                try{a.adb=jsonArray.getJSONObject(0).getString("adb");}catch(Exception e){}
                                try{a.bank=jsonArray.getJSONObject(0).getString("bank");}catch(Exception e){}
                                try{a.charge=jsonArray.getJSONObject(0).getString("charge");}catch(Exception e){}
                                try{a.compare=jsonArray.getJSONObject(0).getString("compare");}catch(Exception e){}
                                try{a.emp_id=jsonArray.getJSONObject(0).getString("emp_id");}catch(Exception e){}
                                try{a.emp_name=jsonArray.getJSONObject(0).getString("emp_name");}catch(Exception e){}
                                try{a.exchange=jsonArray.getJSONObject(0).getString("exchange");}catch(Exception e){}
                                try{a.expenses=jsonArray.getJSONObject(0).getString("expenses");}catch(Exception e){}
                                try{a.ezdist=jsonArray.getJSONObject(0).getString("ezdist");}catch(Exception e){}
                                try{a.fingerprint=jsonArray.getJSONObject(0).getString("fingerprint");}catch(Exception e){}
                                try{a.manager=jsonArray.getJSONObject(0).getString("manager");}catch(Exception e){}
                                try{a.mgmt=jsonArray.getJSONObject(0).getString("mgmt");}catch(Exception e){}
                                try{a.new_client=jsonArray.getJSONObject(0).getString("new_client");}catch(Exception e){}
                                try{a.new_finance=jsonArray.getJSONObject(0).getString("new_finance");}catch(Exception e){}
                                try{a.new_visit_qr=jsonArray.getJSONObject(0).getString("new_visit_qr");}catch(Exception e){}
                                try{a.no_qr=jsonArray.getJSONObject(0).getString("no_qr");}catch(Exception e){}
                                try{a.open_invoice=jsonArray.getJSONObject(0).getString("open_invoice");}catch(Exception e){}
                                try{a.stock11=jsonArray.getJSONObject(0).getString("stock11");}catch(Exception e){}
                                try{a.stock2=jsonArray.getJSONObject(0).getString("stock2");}catch(Exception e){}
                                try{a.view_exchange=jsonArray.getJSONObject(0).getString("view_exchange");}catch(Exception e){}
                                try{a.view_finance=jsonArray.getJSONObject(0).getString("view_finance");}catch(Exception e){}
                                try{a.view_fingerprint=jsonArray.getJSONObject(0).getString("view_fingerprint");}catch(Exception e){}
                                try{a.view_notification=jsonArray.getJSONObject(0).getString("view_notification");}catch(Exception e){}
                                try{a.view_open_invoice=jsonArray.getJSONObject(0).getString("view_open_invoice");}catch(Exception e){}
                                try{a.view_visit=jsonArray.getJSONObject(0).getString("view_visit");}catch(Exception e){}

                                permission.setValue(a);
                            }else {
                                errorDialog(c,"خطأ في عملية جلب الصلاحيات الرجاء المحاولة مرة اخرى!");
                            }
                        }else {
                            errorDialog(c, response.getString("response_state"));
                        }
                    }catch (Exception e){
                        errorDialog(c,e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,error.toString());
                }
            }));
        }catch (Exception e){
            errorDialog(c,e.toString());
        }
    }

    public void updatePermissions(Activity c,classPermissions a ){
        JSONObject jsonObject = new JSONObject();
        try{jsonObject.put("adb",a.adb);}catch(Exception e){}
        try{jsonObject.put("bank",a.bank);}catch(Exception e){}
        try{jsonObject.put("charge",a.charge);}catch(Exception e){}
        try{jsonObject.put("compare",a.compare);}catch(Exception e){}
        try{jsonObject.put("emp_id",a.emp_id);}catch(Exception e){}
        try{jsonObject.put("emp_name",a.emp_name);}catch(Exception e){}
        try{jsonObject.put("exchange",a.exchange);}catch(Exception e){}
        try{jsonObject.put("expenses",a.expenses);}catch(Exception e){}
        try{jsonObject.put("ezdist",a.ezdist);}catch(Exception e){}
        try{jsonObject.put("fingerprint",a.fingerprint);}catch(Exception e){}
        try{jsonObject.put("manager",a.manager);}catch(Exception e){}
        try{jsonObject.put("mgmt",a.mgmt);}catch(Exception e){}
        try{jsonObject.put("new_client",a.new_client);}catch(Exception e){}
        try{jsonObject.put("new_finance",a.new_finance);}catch(Exception e){}
        try{jsonObject.put("new_visit_qr",a.new_visit_qr);}catch(Exception e){}
        try{jsonObject.put("no_qr",a.no_qr);}catch(Exception e){}
        try{jsonObject.put("open_invoice",a.open_invoice);}catch(Exception e){}
        try{jsonObject.put("stock11",a.stock11);}catch(Exception e){}
        try{jsonObject.put("stock2",a.stock2);}catch(Exception e){}
        try{jsonObject.put("view_exchange",a.view_exchange);}catch(Exception e){}
        try{jsonObject.put("view_finance",a.view_finance);}catch(Exception e){}
        try{jsonObject.put("view_fingerprint",a.view_fingerprint);}catch(Exception e){}
        try{jsonObject.put("view_notification",a.view_notification);}catch(Exception e){}
        try{jsonObject.put("view_open_invoice",a.view_open_invoice);}catch(Exception e){}
        try{jsonObject.put("view_visit",a.view_visit);}catch(Exception e){}

        setProgressDialog(c);
        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.UpdatePermessions, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getString("response_state").equals("1")){
                        successDialog(c,response.getString("response_state"));
                    }else {
                        errorDialog(c, response.getString("response_state"));
                    }
                }catch (Exception e){
                    errorDialog(c,e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
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
