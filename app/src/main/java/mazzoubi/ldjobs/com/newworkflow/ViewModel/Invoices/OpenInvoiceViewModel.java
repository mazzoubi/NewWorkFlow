package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;
import android.widget.Toast;

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

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.OpenInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class OpenInvoiceViewModel extends ViewModel {
    public MutableLiveData<ArrayList<OpenInvoiceModel>> listOfOpenInvoice = new MutableLiveData<>();


    public void getOpenInvoiceByFilter(Activity c, String dateFrom , String dateTo,
                                    String userId , String clientId ){
        listOfOpenInvoice = new MutableLiveData<>();
        ArrayList<OpenInvoiceModel> temp = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("Datatype","3");
            String query = " and Convert(date, ci.create_date, 23) " +
                    "between Convert(date, '"+dateFrom+"', 23) " +
                    "and Convert(date, '"+dateTo+"', 23) ";
            if (!userId.isEmpty()){
                query+=" and ci.user_id= "+userId;
            }
            if (!clientId.isEmpty()){
                query+=" and ci.client_id= "+clientId;
            }

            jsonObject.put("Key",query);

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.GetChargeInvoice, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i=0 ; i< jsonArray.length();i++){
                            OpenInvoiceModel a = new OpenInvoiceModel();
                            try {a.setId(jsonArray.getJSONObject(i).getString("charge_id"));}catch (Exception e){a.setId("");}
                            try {a.setClientId(jsonArray.getJSONObject(i).getString("client_id"));}catch (Exception e){a.setClientId("");}
                            try {a.setUserId(jsonArray.getJSONObject(i).getString("user_id"));}catch (Exception e){a.setUserId("");}
                            try {a.setInvoiceId(jsonArray.getJSONObject(i).getString("invoice_id"));}catch (Exception e){a.setInvoiceId("");}
                            try {a.setDate(jsonArray.getJSONObject(i).getString("create_date"));}catch (Exception e){a.setDate("");}
                            try {a.setTime(jsonArray.getJSONObject(i).getString("create_time"));}catch (Exception e){a.setTime("");}
                            try {a.setState(jsonArray.getJSONObject(i).getString("state"));}catch (Exception e){a.setState("");}
                            try {a.setChargeState(jsonArray.getJSONObject(i).getString("charge_state"));}catch (Exception e){a.setChargeState("");}
                            try {a.setNote(jsonArray.getJSONObject(i).getString("note"));}catch (Exception e){a.setNote("");}
                            try {a.setPointDept(jsonArray.getJSONObject(i).getString("point_debt"));}catch (Exception e){a.setPointDept("");}
                            try {a.setInvUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));}catch (Exception e){a.setInvUnpaid("");}
                            try {a.setClientNam(jsonArray.getJSONObject(i).getString("client_name"));}catch (Exception e){a.setClientNam("");}
                            try {a.setUserName(jsonArray.getJSONObject(i).getString("name"));}catch (Exception e){a.setUserName("");}
                            try {a.setInvoiceNo(jsonArray.getJSONObject(i).getString("invoice_no"));}catch (Exception e){a.setInvoiceNo("");}
                            try {a.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));}catch (Exception e){a.setInvoiceAmount("");}


                            // set Share whatsUp message
                            try {
                                JSONObject jj = new JSONObject(jsonArray.getJSONObject(i).getString("shared_body"));
                                jj.put("invNo",a.getInvoiceNo());

                                String z = "" ;
                                for (int ii = 0; ii<jj.getJSONArray("lastInv").length();ii++){
                                    z+="\n"+(ii+1)+"- "+jj.getJSONArray("lastInv").getJSONObject(ii).getString("unpaid")+"\n";
                                }

                                String shared = "إسم العميل : "+jj.getString("clientName")+"\n" +
                                        "رقم الفاتورة : "+a.getInvoiceNo()+"\n" +
                                        "قيمة الفاتورة : "+jj.getString("invAmount")+"\n" +
                                        "تاريخ فتح الفاتورة : "+a.getDate()+"\n" +
                                        "وقت فتح الفاتورة : "+a.getTime()+"\n" +
                                        "قيمة الذمم : "+a.getPointDept()+" دينار\n" +
                                        "\n" +
                                        " الفواتير السابقة الغير مسددة: \n" +
                                        z +
                                        "\n" +
                                        "\n" +
                                        "\n" +
                                        "عدد الفواتير الغير مسددة : "+(jj.getJSONArray("lastInv").length()+1)+"\n" +
                                        "إسم منشئ الفاتورة : "+a.getUserName()+"" ;

                                a.setSharedBody(shared);
                            }catch (Exception e){
                                a.setSharedBody("");
                            }

                            temp.add(a);
                        }
                        listOfOpenInvoice.setValue(temp);
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


    public void acceptOpenInv(Activity c,OpenInvoiceModel openInvoice){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id" ,openInvoice.getInvoiceId() );
            jsonObject.put("state" ,"1");
            jsonObject.put("chargeState" ,openInvoice.getChargeState());



//            new sendWhatsAppMessage(invoiceStoreActivity.this,a.phone
//                    ,2,"تم شحن حسابك  رقم عملية الشحن الخاصة بك هي "+et.getText().toString());
//            Toast.makeText(c, "تم قبول العملية", Toast.LENGTH_LONG).show();
//            OpenInvoiceActivity.CustomDialogVisitInfo.this.dismiss();

            c.recreate();
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
