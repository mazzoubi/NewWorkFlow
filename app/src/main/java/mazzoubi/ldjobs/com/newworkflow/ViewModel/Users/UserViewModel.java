package mazzoubi.ldjobs.com.newworkflow.ViewModel.Users;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Dashboard3Activity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Main.DashboardActivity;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class UserViewModel extends ViewModel {

    private static final String collectionUsers="Users";
    public MutableLiveData<ArrayList<UserModel>> listUsers = new MutableLiveData<>();
    public MutableLiveData<UserModel> login = new MutableLiveData<>();
    public MutableLiveData<UserModel> userObject = new MutableLiveData<>();

    public void InsertUser(Activity c , UserModel userModel){
        if (userModel.getName().isEmpty()){
            errorDialog(c,"الرجاء ادخال الإسم!");
        }else if (userModel.getPhone().isEmpty()){
            errorDialog(c,"الرجاء إدخال رقم الهاتف!");
        }else if (userModel.getUsername().isEmpty()){
            errorDialog(c,"الرجاء إدخال إسم المستخدم!");
        }else if (userModel.getPassword().isEmpty()){
            errorDialog(c,"الرجاء ادخال كلمة المرور!");
        }else if (userModel.getType().isEmpty()){
            errorDialog(c,"الرجاء ادخال نوع المستخدم!");
        }else {

            setProgressDialog(c);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", userModel.getName() );
                jsonObject.put("Username", userModel.getUsername() );
                jsonObject.put("Phone", userModel.getPhone() );
                jsonObject.put("Password", userModel.getPassword() );
                jsonObject.put("Type", userModel.getType() );
            }catch (Exception e ){}

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.InsertUser, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        if (response.getString("response_state").equals("1")){
                            successDialog(c,response.getString("response_message"));
                        }else {
                            errorDialog(c,response.getString("response_message"));
                        }
                    }catch (Exception e){
                        errorDialog(c,"خطأ في عملية الإضافة الرجاء المحاولة مرة اخرى!");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,"تعذر الوصول الى الخادم الرجاء المحاولة مرة اخرى!");
                }
            }));


        }
    }

    public void getUsers(Activity c ){
        listUsers = new MutableLiveData<>();
        ArrayList<UserModel> temp = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","1");
            jsonObject.put("Key","");
            jsonObject.put("Username","");
            jsonObject.put("Password","");
        }catch (Exception e){}
        setProgressDialog(c);
        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST, ClassAPIs.GetUsers,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0 ; i< jsonArray.length() ; i++){
                        UserModel a = new UserModel();

                        try { a.setDebt(jsonArray.getJSONObject(i).getString("debt")) ;
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setId(jsonArray.getJSONObject(i).getString("id"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setName(jsonArray.getJSONObject(i).getString("name"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPassword(jsonArray.getJSONObject(i).getString("password"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPhone(jsonArray.getJSONObject(i).getString("phone"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setToken(jsonArray.getJSONObject(i).getString("token"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setUsername(jsonArray.getJSONObject(i).getString("username"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setAID(jsonArray.getJSONObject(i).getString("aid"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }
                        temp.add(a);
                    }
                    dismissProgressDialog();
                    listUsers.setValue(temp);

                }catch (Exception e){
                    errorDialog(c,"خطأ في عملية جلب المستخدمين!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في الوصول للخادم!"+"\n"+error.toString());

            }
        }));
    }

    public void getUserByUsername(Activity c ,String username){
        userObject = new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","3");
            jsonObject.put("Username",username);
            jsonObject.put("Password","");
        }catch (Exception e){}
        setProgressDialog(c);
        Volley.newRequestQueue(c).add(new JsonObjectRequest(
                Request.Method.POST, ClassAPIs.GetUsers, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getString("response_state").equals("1")){
                        // login

                        UserModel a = new UserModel();
                        JSONArray data = response.getJSONArray("data");

                        try { a.setDebt(Double.parseDouble(data.getJSONObject(0).getString("debt"))+"") ;
                        }catch (Exception e){
                            a.setDebt("0");
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setId(data.getJSONObject(0).getString("id"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setName(data.getJSONObject(0).getString("name"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPassword(data.getJSONObject(0).getString("password"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPhone(data.getJSONObject(0).getString("phone"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setToken(data.getJSONObject(0).getString("token"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setUsername(data.getJSONObject(0).getString("username"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setAID(data.getJSONObject(0).getString("aid"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setType(data.getJSONObject(0).getString("type"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        userObject.setValue(a);
                    }else {
                        errorDialog(c,response.getString("response_message"));
                    }
                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع الرجاء إعادة المحاولة!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في الوصول للخادم!"+"\n"+error.toString());
            }
        }
        ));
    }

    public void login(Activity c , String username , String password){
        login = new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("Datatype","2");
            jsonObject.put("Username",username);
            jsonObject.put("Password",password);
        }catch (Exception e){}
        setProgressDialog(c);
        Volley.newRequestQueue(c).add(new JsonObjectRequest(
                Request.Method.POST, ClassAPIs.GetUsers, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    if (response.getString("response_state").equals("1")){
                        // login

                        UserModel a = new UserModel();
                        JSONArray data = response.getJSONArray("data");

                        try { a.setDebt(data.getJSONObject(0).getString("debt")) ;
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setId(data.getJSONObject(0).getString("id"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setName(data.getJSONObject(0).getString("name"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPassword(data.getJSONObject(0).getString("password"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setPhone(data.getJSONObject(0).getString("phone"));
                        }catch (Exception e){
                            //errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setToken(data.getJSONObject(0).getString("token"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setUsername(data.getJSONObject(0).getString("username"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setAID(data.getJSONObject(0).getString("aid"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        try { a.setAID(data.getJSONObject(0).getString("type"));
                        }catch (Exception e){
                            // errorDialog(c,""+"\n"+e.toString());
                        }

                        updateUser(c,a);
                        login.setValue(a);
                        c.startActivity(new Intent(c, Dashboard3Activity.class));
                        SharedPreferences.Editor editor= c.getSharedPreferences("User", Context.MODE_PRIVATE).edit();
                        editor.putString("AID" ,a.getAID());
                        editor.putString("Name" ,a.getName());
                        editor.putString("Phone" ,a.getPhone());
                        editor.putString("Id" ,a.getId());
                        editor.putString("Token" ,a.getToken());
                        editor.putString("Username" ,a.getUsername());
                        editor.putString("Debt" ,a.getDebt());
                        editor.putString("Type" ,a.getType());
                        editor.apply();

                    }else {
                        errorDialog(c,response.getString("response_message"));
                    }
                }catch (Exception e){
                    errorDialog(c,"خطأ غير متوقع الرجاء إعادة المحاولة!"+"\n"+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                errorDialog(c,"خطأ في الوصول للخادم!"+"\n"+error.toString());
            }
        }
        ));
    }

    public void updateUser(Activity c , UserModel userModel){

            setProgressDialog(c);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", userModel.getName() );
                jsonObject.put("username", userModel.getUsername() );
                jsonObject.put("phone", userModel.getPhone() );
                jsonObject.put("password", userModel.getPassword() );
                jsonObject.put("debt", userModel.getDebt() );
                jsonObject.put("token", userModel.getToken() );
                jsonObject.put("AID", userModel.getAID() );
            }catch (Exception e ){}

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.InsertUser, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {

                    }catch (Exception e){
                        errorDialog(c,"خطأ في عملية الإضافة الرجاء المحاولة مرة اخرى!");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(c,"تعذر الوصول الى الخادم الرجاء المحاولة مرة اخرى!");
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
