package mazzoubi.ldjobs.com.newworkflow.ViewModel.UserExchange;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.Data.UsersExchange.UserExchangeModel;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class UserExchangeViewModel extends ViewModel {

    public MutableLiveData<ArrayList<UserExchangeModel>> listOfExchanges=new MutableLiveData<>();

    public void newExchange(Activity c , String from , String to , String amount){
        if (from.isEmpty()){
            errorDialog(c,"");
        }else if (to.isEmpty()){
            errorDialog(c,"الرجاء اختيار المستخدم الذي تريد التحويل له!");
        }else if (amount.isEmpty()){
            errorDialog(c,"الرجاء ادخال قيمة التحويل!");
        }else {
            UserViewModel vm = ViewModelProviders.of((FragmentActivity) c).get(UserViewModel.class);
            vm.getUserByUsername(c,c.getSharedPreferences("Users", Context.MODE_PRIVATE).getString("username",""));
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
                            jsonObject.put("from",from);
                            jsonObject.put("to",to);
                            jsonObject.put("amount",amount);
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

    public void acceptExchange(Activity d, UserExchangeModel exchangeModel ){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exId",exchangeModel.getId());
            jsonObject.put("state","1");
        }catch (Exception e){}
    }


    public void rejectExchange(Activity d, UserExchangeModel exchangeModel){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exId",exchangeModel.getId());
            jsonObject.put("state","-1");
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
