package mazzoubi.ldjobs.com.newworkflow.ViewModel.Banks;

import android.app.Activity;
import android.net.Uri;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Data.Banks.BankTransferModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Banks.BanksModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class BanksViewModel extends ViewModel {
    private static final String collectionBanks = "Banks";

    public MutableLiveData<ArrayList<BanksModel>> listOfBanks =new MutableLiveData<>();

    public void addBank(Activity c , BanksModel banksModel){
        if (banksModel.getName().isEmpty()){
            errorDialog(c,"الرجاء ادخال اسم البنك!");
        }else{
            setProgressDialog(c);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("BankName" ,banksModel.getName() );
                jsonObject.put("Commission" ,"0");

                Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                        ClassAPIs.InsertBanks, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissProgressDialog();
                      try {
                          if (response.getString("response_state").equals("0")){
                              errorDialog(c,response.getString("response_message"));
                          }else {
                              successDialog(c,response.getString("response_message"));
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
    }

    public void getBanks(Activity c){
        listOfBanks = new MutableLiveData<>();
        ArrayList<BanksModel> temp = new ArrayList<>();
        setProgressDialog(c);
        String s = " \"bank_id\"\n" +
                "    \"bank_name\"\n" +
                "    \"commission\"";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","1");
            jsonObject.put("Key","");

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST, ClassAPIs.GetBanks,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("response_state").equals("1")){
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i =0 ; i<jsonArray.length();i++){
                                BanksModel banksModel = new BanksModel();
                                try{banksModel.setId(jsonArray.getJSONObject(i).getString("bank_id"));}catch (Exception e){banksModel.setId("");}
                                try{banksModel.setName(jsonArray.getJSONObject(i).getString("bank_name"));}catch (Exception e){banksModel.setName("");}
                                try{banksModel.setCommission(jsonArray.getJSONObject(i).getString("commission"));}catch (Exception e){banksModel.setCommission("0");}
                                temp.add(banksModel);
                            }
                            dismissProgressDialog();
                            listOfBanks.setValue(temp);
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
        }catch (Exception e){
            errorDialog(c,e.toString());
        }
    }

    public void getBankById(Activity c, String id){
        listOfBanks = new MutableLiveData<>();
        ArrayList<BanksModel> temp = new ArrayList<>();
        setProgressDialog(c);
        FirebaseFirestore.getInstance().collection(collectionBanks)
                .whereEqualTo("id",id)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                    BanksModel banksModel = new BanksModel();
                    try{banksModel.setId(d.getString("id"));}catch (Exception e){banksModel.setId("");}
                    try{banksModel.setName(d.getString("name"));}catch (Exception e){banksModel.setName("");}
                    try{banksModel.setCommission(d.getString("commission"));}catch (Exception e){banksModel.setCommission("0");}
                    temp.add(banksModel);
                }
                dismissProgressDialog();
                listOfBanks.setValue(temp);
            }
        });
    }

    public void createBankTransfer(Activity c , BankTransferModel transfer ){
        if (transfer.getBankId().isEmpty()){
            errorDialog(c,"الرجاء اختيار البنك!");
        }else if (transfer.getImage().isEmpty()){
            errorDialog(c,"الرجاء ارفاق صورة!");
        }else {

        }
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

}







