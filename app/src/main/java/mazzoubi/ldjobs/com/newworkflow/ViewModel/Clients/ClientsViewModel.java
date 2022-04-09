package mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class ClientsViewModel extends ViewModel {
    private static final String collectionClients = "Clients";
    public MutableLiveData<ArrayList<ClientModel>> listOfClient = new MutableLiveData<>();

    public void insertNewClients(Activity c , ClientModel clientModel){
        if (clientModel.getClientName().equals("")){
            errorDialog(c,"الرجاء ادخال اسم العميل!");
        }else if (clientModel.getClientEmail().equals("")){
            errorDialog(c,"الرجاء ادخال البريد الإلكتروني!");
        }else if (clientModel.getClientPhone().equals("")){
            errorDialog(c,"الرجاء ادخال رقم هاتف العميل!");
        }else if (clientModel.getHolderId().equals("")){
            errorDialog(c,"الرجاء ادخال مندوب النقطة!");
        }else {
            String phone = phoneCorrection(c,clientModel.getClientPhone());
            if (!phone.equals("0")){
                setProgressDialog(c);

                String key = ClassDate.currentTimeAtMs();
                Map<String,Object> map = new HashMap<>();
                map.put("clientName", clientModel.getClientName());
                map.put("clientPhone", phone);
                map.put("clientEmail", clientModel.getClientEmail());
                map.put("holderId", clientModel.getHolderId());
                map.put("clientId", key);
                map.put("crm_id", "");

                FirebaseFirestore.getInstance().collection(collectionClients).document(key).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dismissProgressDialog();
                        successDialog(c,"تم انشاء العميل بنجاح");
                    }
                });
//                setProgressDialog(c);
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("ClientEmail" , clientModel.getClientEmail() );
//                    jsonObject.put("ClientName" , clientModel.getClientName() );
//                    jsonObject.put("ClientPhone" , clientModel.getClientPhone() );
//                    jsonObject.put("HolderId" , clientModel.getHolderId() );
//                    jsonObject.put("CrmId" , "" );
//
//                }catch (Exception e){}
//
//                Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
//                        ClassAPIs.InsertClients, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        dismissProgressDialog();
//                        try {
//                            String response_state = response.getString("response_state");
//                            if (response_state.equals("1")){
//                                successDialog(c,response.getString("response_message"));
//                            }else {
//                                errorDialog(c,response.getString("response_message"));
//                            }
//                        }catch (Exception e){
//                            errorDialog(c,"خطأ في عملية الإضافة, الرجاء المحاولة مرة اخرى!");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        dismissProgressDialog();
//                        errorDialog(c,"خطأ في عملية الوصول الى الخادم, الرجاء المحاولة مرة اخرى!");
//                    }
//                }));
            }

        }
    }

    public void updateClients(Activity c , ClientModel clientModel){
        if (clientModel.getClientName().equals("")){
            errorDialog(c,"الرجاء ادخال اسم العميل!");
        }else if (clientModel.getClientEmail().equals("")){
            errorDialog(c,"الرجاء ادخال البريد الإلكتروني!");
        }else if (clientModel.getClientPhone().equals("")){
            errorDialog(c,"الرجاء ادخال رقم هاتف العميل!");
        }else if (clientModel.getDateOfRegister().equals("")){
            errorDialog(c,"الرجاء اختيار تاريخ التسجيل!");
        }else if (clientModel.getHolderId().equals("")){
            errorDialog(c,"الرجاء ادخال مندوب النقطة!");
        }else if (clientModel.getConstraint().equals("")){
            errorDialog(c,"الرجاء ادخال نوع القيود للنقطة!");
        }else if (clientModel.getMax_debt().equals("")){
            errorDialog(c,"الرجاء ادخال سقف قيمة الذمم للنقطة!");
        }else if (clientModel.getMax_inv().equals("")){
            errorDialog(c,"الرجاء ادخال سقف عدد الفواتير للنقطة!");
        }else {
            setProgressDialog(c);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Datatype" ,"1" );
                jsonObject.put("ClientName" , clientModel.getClientName());
                jsonObject.put("ClientId" , clientModel.getClientId());
                jsonObject.put("ClientPhone" , clientModel.getClientPhone());
                jsonObject.put("ClientEmail" , clientModel.getClientEmail());
                jsonObject.put("HolderId" , clientModel.getHolderId());
                jsonObject.put("MaxDebt" , clientModel.getMax_debt());
                jsonObject.put("MaxInv" , clientModel.getMax_inv());
                jsonObject.put("constraint" , clientModel.getConstraint());
                jsonObject.put("State" , clientModel.getState());
                jsonObject.put("DateOfRegister" , clientModel.getDateOfRegister());

            }catch (Exception e){}

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.UpdateClients, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        String response_state = response.getString("response_state");
                        if (response_state.equals("1")){
                            successDialog(c,response.getString("response_message"));
                        }else {
                            errorDialog(c,response.getString("response_message"));
                        }
                    }catch (Exception e){
                        errorDialog(c,"خطأ في عملية الإضافة, الرجاء المحاولة مرة اخرى!"+"\n"+e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,"خطأ في عملية الوصول الى الخادم, الرجاء المحاولة مرة اخرى!"+"\n"+error.toString());
                }
            }));
        }
    }


    public void getAllClients(Activity c){
        listOfClient = new MutableLiveData<>();
        ArrayList<ClientModel> temp = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "1" );
            jsonObject.put("Key" , "" );
        }catch (Exception e){}


        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetClients, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0 ; i<jsonArray.length() ; i++){
                        temp.add(new ClientModel(
                                jsonArray.getJSONObject(i).getString("client_name"),
                                jsonArray.getJSONObject(i).getString("client_id"),
                                jsonArray.getJSONObject(i).getString("client_phone"),
                                jsonArray.getJSONObject(i).getString("client_email"),
                                jsonArray.getJSONObject(i).getString("holder_id"),
                                jsonArray.getJSONObject(i).getString("holder_name"),
                                jsonArray.getJSONObject(i).getString("max_debt"),
                                jsonArray.getJSONObject(i).getString("max_inv"),
                                jsonArray.getJSONObject(i).getString("constraint"),
                                jsonArray.getJSONObject(i).getString("state"),
                                jsonArray.getJSONObject(i).getString("date_of_register"),
                                jsonArray.getJSONObject(i).getString("crm_id")
                        ));

                    }
                    listOfClient.setValue(temp);
                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع, "+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في عملية الوصول الى الخادم, الرجاء المحاولة مرة اخرى!"+"\n"+error.toString());
            }
        }));
    }


    public void getSingleClientById(Activity c,String clientId){
        listOfClient = new MutableLiveData<>();
        ArrayList<ClientModel> temp = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientId" , "" );
        }catch (Exception e){}


        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetClients, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0 ; i<jsonArray.length() ; i++){
                        temp.add(new ClientModel(
                                jsonArray.getJSONObject(i).getString("client_name"),
                                jsonArray.getJSONObject(i).getString("client_id"),
                                jsonArray.getJSONObject(i).getString("client_phone"),
                                jsonArray.getJSONObject(i).getString("client_email"),
                                jsonArray.getJSONObject(i).getString("holder_id"),
                                jsonArray.getJSONObject(i).getString("holder_name"),
                                jsonArray.getJSONObject(i).getString("max_debt"),
                                jsonArray.getJSONObject(i).getString("max_inv"),
                                jsonArray.getJSONObject(i).getString("constraint"),
                                jsonArray.getJSONObject(i).getString("state"),
                                jsonArray.getJSONObject(i).getString("date_of_register"),
                                jsonArray.getJSONObject(i).getString("crm_id")
                        ));
                    }
                    listOfClient.setValue(temp);
                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع, "+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في عملية الوصول الى الخادم, الرجاء المحاولة مرة اخرى!");
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
        try {
            progressDialog.dismiss();
        }catch (Exception e){}

    }



    String phoneCorrection(Activity c,String ph){
        long a= Long.parseLong(ph);
        String phone = a+"";
        if (phone.length()<9||phone.length()>14){
            errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
            return "0";
        }else {
            if (phone.startsWith("+96279")
                    ||phone.startsWith("+96278")||phone.startsWith("+96277")){
                if (phone.length()!=13){
                    errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
                    return "0";
                }else {
                    String temp3="";
                    for (int i=1 ; i<phone.length();i++){
                        temp3+=phone.charAt(i);
                    }
                    if (temp3.startsWith("96279")||temp3.startsWith("96277")||temp3.startsWith("96278")){
                        return temp3;
                    }else {
                        errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
                        return "0";
                    }
                }
            }else if (phone.startsWith("0096279")
                    ||phone.startsWith("0096278")||phone.startsWith("0096277")){
                if (phone.length()!=14){
                    errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
                    return "0";
                }else {
                    String temp3="";
                    for (int i=2 ; i<phone.length();i++){
                        temp3+=phone.charAt(i);
                    }
                    if (temp3.startsWith("96279")||temp3.startsWith("96277")||temp3.startsWith("96278")){
                        return temp3;
                    }else {
                        errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
                        return "0";
                    }
                }
            }else {
                String ss = phone;
                long temp=Long.parseLong(ss);
                String temp2=temp+"";
                String temp3="0";
                if(temp2.substring(0,2).equals("62")){
                    temp3="9"+temp2;
                }else if(temp2.substring(0,2).equals("96")){
                    temp3=temp2;
                }else if(temp2.substring(0,2).equals("78")||temp2.substring(0,2).equals("79")||temp2.substring(0,2).equals("77")){
                    temp3="962"+temp2;
                }else if(temp2.substring(0,3).equals("078")||temp2.substring(0,3).equals("079")||temp2.substring(0,3).equals("077")){
                    temp3="962";
                    for (int i=1 ; i<temp2.length();i++){
                        temp3+=temp2.charAt(i);
                    }
                }else if (temp2.substring(0,1).equals("8")||temp2.substring(0,1).equals("9")||temp2.substring(0,1).equals("7")){
                    temp3="9627"+temp2;
                }

                if ((temp3.startsWith("96279")||temp3.startsWith("96277")||temp3.startsWith("96278"))&&temp3.length()==12){
                    return temp3;
                }else {
                    errorDialog(c,"رقم الهاتف خاطئ, الرجاء ادخال رقم صحيح!");
                    return "0";
                }

            }
        }

    }
}
