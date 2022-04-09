package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class OpenInvoiceViewModel extends ViewModel {
    public MutableLiveData<ArrayList<OpenInvoiceModel>> listOfOpenInvoice = new MutableLiveData<>();


    public void getPaymentsByFilter(Activity c, String dateFrom , String dateTo,
                                    String userId , String clientId ){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dateFrom",dateFrom);
            jsonObject.put("dateTo",dateTo);
            if (userId.isEmpty()&&clientId.isEmpty()){

            }else if (!userId.isEmpty()&&clientId.isEmpty()){
                jsonObject.put("userId",userId);
            }if (userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("clientId",clientId);
            }if (!userId.isEmpty()&&!clientId.isEmpty()){
                jsonObject.put("userId",userId);
                jsonObject.put("clientId",userId);
            }

        }catch (Exception e){}

    }


    public void acceptOpenInv(Activity c,OpenInvoiceModel openInvoice){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id" ,openInvoice.getId() );
            jsonObject.put("state" ,"1");

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
