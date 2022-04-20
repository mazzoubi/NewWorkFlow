package mazzoubi.ldjobs.com.newworkflow.ViewModel.UserExchange;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.Data.UsersExchange.UserExchangeModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class UserExchangeViewModel extends ViewModel {

    public MutableLiveData<ArrayList<UserExchangeModel>> listOfExchanges=new MutableLiveData<>();

    public void newExchange(Activity c , String from , String to , String amount ,String note){
        if (from.isEmpty()){
            errorDialog(c,"");
        }else if (to.isEmpty()){
            errorDialog(c,"الرجاء اختيار المستخدم الذي تريد التحويل له!");
        }else if (amount.isEmpty()){
            errorDialog(c,"الرجاء ادخال قيمة التحويل!");
        }else {

            UserViewModel vm = ViewModelProviders.of((FragmentActivity) c).get(UserViewModel.class);
            vm.getUserByUsername(c,UserInfo.getUser(c).getUsername());
            vm.userObject.observe((LifecycleOwner) c, new Observer<UserModel>() {
                @Override
                public void onChanged(UserModel userModel) {
                    double fromDept = Double.parseDouble(userModel.getDebt());
                    double amou = Double.parseDouble(amount);
                    if ((fromDept-amou)<0) {
                        errorDialog(c, "لايجوز تحويل مبلغ اكبر من قيمة الذمم؟");
                    }else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("FromUserId",from);
                            jsonObject.put("ToUserId",to);
                            jsonObject.put("Amount",amount);
                            jsonObject.put("Note",note);

                            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                                    ClassAPIs.InsertCommissionTransfer, jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("response_state").equals("1")){
                                            successDialog(c,response.getString("response_message"));
                                        }else {
                                            errorDialog(c,response.getString("response_state"));
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
                        }catch (Exception e){}
                    }
                }
            });
        }
    }

    public void getExchanged(Activity c, String dateFrom , String dateTo , String userId , String exType){
        // exType if 1:-> from   else 2:-> to

        listOfExchanges = new MutableLiveData<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dateFrom",dateFrom);
            jsonObject.put("dateFrom",dateTo);

            if (!userId.isEmpty()){
                if (exType.equals("1")){
                    jsonObject.put("fromUserId",userId);
                }else {
                    jsonObject.put("toUserId",userId);
                }
            }

        }catch (Exception e){}

    }

    public void acceptExchange(Activity c, UserExchangeModel exchangeModel ){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("DataType","" );
            jsonObject.put("Id",exchangeModel.getId() );
            jsonObject.put("FromUserId",exchangeModel.getFromUserId() );
            jsonObject.put("ToUserId",exchangeModel.getToUserId() );
            jsonObject.put("Amount",exchangeModel.getAmount() );
            jsonObject.put("Note",exchangeModel.getNotes() );

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.UpdateCommissionTransfer, jsonObject, new Response.Listener<JSONObject>() {
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

        }catch (Exception e){}
    }

    public void rejectExchange(Activity c, UserExchangeModel exchangeModel){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("DataType","" );
            jsonObject.put("Id",exchangeModel.getId() );
            jsonObject.put("FromUserId",exchangeModel.getFromUserId() );
            jsonObject.put("ToUserId",exchangeModel.getToUserId() );
            jsonObject.put("Amount",exchangeModel.getAmount() );
            jsonObject.put("Note",exchangeModel.getNotes() );

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.UpdateCommissionTransfer, jsonObject, new Response.Listener<JSONObject>() {
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
