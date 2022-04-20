package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceData;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;

public class InvoiceViewModel extends ViewModel {
    private static final String collectionInvoices = "Invoices" ;
    public MutableLiveData<ArrayList<InvoiceModel>> listOfInvoices=new MutableLiveData<>();

    public void addInvoice(Activity c , InvoiceModel invoiceData,String sharedBody){
        if (invoiceData.getInvoiceAmount().isEmpty()){
            errorDialog(c,"الرجاء ادخال قيمة الفاتورة!");
        }else if (invoiceData.getCreatedByUserId().isEmpty()){
            errorDialog(c,"الرجاء ادخال المستخدم الذي انشأ الفاتورة!");
        }else if (invoiceData.getInvType().isEmpty()){
            errorDialog(c,"الرجاء ادخال نوع الفاتورة!");
        }else if (invoiceData.getClientId().isEmpty()){
            errorDialog(c,"الرجاء ادخال العميل الذي انشأت له هذه الفاتورة!");
        }else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("CreatedByUserId", invoiceData.getCreatedByUserId());
                jsonObject.put("InvType", invoiceData.getInvType());
                jsonObject.put("InvoiceAmount", invoiceData.getInvoiceAmount());
                jsonObject.put("InvoiceNo", "");
                jsonObject.put("PointId", invoiceData.getClientId());
                jsonObject.put("Note", "");
                jsonObject.put("SharedBody", sharedBody);
            }catch (Exception e){}



            try {
                Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                        ClassAPIs.InsertInvoices, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("response_state").equals("1")){
                                getInvoicesByClintId(c,invoiceData.getClientId());
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

            /*
            1- انشاء الفاتورة
            2- انشاء openinvoice
            3- ارسال chat2desk كالاتي:
            ////////////////////////////
            ////////////////////////////
            إسم العميل : محمص الرايه Y
            رقم الفاتورة : 106694
            قيمة الفاتورة : 200 دينار
            تاريخ فتح الفاتورة : 30-03-2022
            وقت فتح الفاتورة : 14:15:45
            قيمة الذمم : 400.0 دينار

            الفواتير السابقة الغير مسددة:
            1- الفاتورة رقم 106694 والمتبقي منها 200 دينار
            2- الفاتورة رقم 105623 والمتبقي منها 200 دينار


            عدد الفواتير الغير مسددة : 2
            إسم منشئ الفاتورة : يوسف الوقفي
            /////////////////////////////////////
            /////////////////////////////////////



            4-انشاء inv log كالاتي:
            String id ="";
            String userId ="";
            String userName = "" ;
            String date ="";
            String time ="";
            String clientId ="";
            String clientName ="";
            String invoiceId ="";
            String description ="";
            String type ="1";        (1- created)    2-add payment   3-delete payment   4-deleted   5- updated
            */


        }
    }

    public void getInvoiceByFilter(Activity c,String invNo, String _dateFrom, String _dateTo, String pointId, String empName
            , boolean unpaid,  boolean paid,  boolean partiallyPaid
            ,  boolean all,boolean allUnpaid , boolean isDeleted){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);

        String query =" and Convert(date, inv.created_date, 23) " +
                "between Convert(date, '"+_dateFrom+"', 23) " +
                "and Convert(date, '"+_dateTo+"', 23)";
        try {
            if (!invNo.isEmpty()) {
                query=" and inv.invoice_no="+invNo;
            } else if (pointId.isEmpty() && empName.isEmpty() && all) {

            } else if (!pointId.isEmpty() && empName.isEmpty() && all) {
                query+=" and cli.client_id="+pointId;
            } else if (pointId.isEmpty() && !empName.isEmpty() && all) {
                query+=" and u.id="+empName;
            } else if (!pointId.isEmpty() && !empName.isEmpty() && all) {
                query+=" and cli.client_id="+pointId;
                query+=" and u.id="+empName;
            } else if (pointId.isEmpty() && empName.isEmpty() && unpaid) {
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =1";
            } else if (!pointId.isEmpty() && empName.isEmpty() && unpaid) {
                query+=" and cli.client_id="+pointId;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =1";
            } else if (pointId.isEmpty() && !empName.isEmpty() && unpaid) {
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =1";
            } else if (!pointId.isEmpty() && !empName.isEmpty() && unpaid) {
                query+=" and cli.client_id="+pointId;
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =1";
            } else if (pointId.isEmpty() && empName.isEmpty() && paid) {
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =3";
            } else if (!pointId.isEmpty() && empName.isEmpty() && paid) {
                query+=" and cli.client_id="+pointId;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =3";
            } else if (pointId.isEmpty() && !empName.isEmpty() && paid) {
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =3";
            } else if (!pointId.isEmpty() && !empName.isEmpty() && paid) {
                query+=" and cli.client_id="+pointId;
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =3";
            } else if (pointId.isEmpty() && empName.isEmpty() && partiallyPaid) {
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =2";
            } else if (!pointId.isEmpty() && empName.isEmpty() && partiallyPaid) {
                query+=" and cli.client_id="+pointId;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =2";
            } else if (pointId.isEmpty() && !empName.isEmpty() && partiallyPaid) {
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =2";
            } else if (!pointId.isEmpty() && !empName.isEmpty() && partiallyPaid) {
                query+=" and cli.client_id="+pointId;
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) =2";
            }


            else if (pointId.isEmpty() && empName.isEmpty() && allUnpaid) {
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) !=3";
            } else if (!pointId.isEmpty() && empName.isEmpty() && allUnpaid) {
                query+=" and cli.client_id="+pointId;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) !=3";
            } else if (pointId.isEmpty() && !empName.isEmpty() && allUnpaid) {
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) !=3";
            } else if (!pointId.isEmpty() && !empName.isEmpty() && allUnpaid) {
                query+=" and cli.client_id="+pointId;
                query+=" and u.id="+empName;
                query += " and (isnull(dbo.InvoiceDetailsValues(inv.invoice_id, 3), 1)) !=3";
            }
        }catch (Exception e){ }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","6");
            jsonObject.put("Key",query);
        }catch (Exception e){
            String s = e.toString();
            errorDialog(c,"خطأ غير متوقع الرجاء اعادة المحاولة!"+"\n"+e.toString());
        }

        Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                ClassAPIs.GetInvoices, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dismissProgressDialog();
                try {
                    JSONArray jsonArray  = response.getJSONArray("data");
                    for (int i=0 ; i< jsonArray.length() ; i++){
                        InvoiceModel aa = new InvoiceModel();
                        try {
                            aa.setCreatedDate(jsonArray.getJSONObject(i).getString("created_date"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedTime(jsonArray.getJSONObject(i).getString("created_time"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString("created_by_user_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvType(jsonArray.getJSONObject(i).getString("inv_type"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceId(jsonArray.getJSONObject(i).getString("invoice_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString("invoice_no"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("point_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCRM_ID(jsonArray.getJSONObject(i).getString("crm_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceState(jsonArray.getJSONObject(i).getString("invoice_state"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoicePaid(jsonArray.getJSONObject(i).getString("invoice_payed"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("client_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientName(jsonArray.getJSONObject(i).getString("client_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientPhone(jsonArray.getJSONObject(i).getString("client_phone"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientHolderName(jsonArray.getJSONObject(i).getString("holder_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }


                        try {
                            aa.setModifiedDate(jsonArray.getJSONObject(i).getString("modifiedDate"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedTime(jsonArray.getJSONObject(i).getString("modifiedTime"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedByUserName(jsonArray.getJSONObject(i).getString("modifiedByUserName"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }

                        temp.add(0,aa);

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

    public void getInvoicesByClintId(Activity c , String clientId){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "6");
            jsonObject.put("Key" , " and cli.client_id="+clientId);
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
                        try {
                            aa.setCreatedDate(jsonArray.getJSONObject(i).getString("created_date"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedTime(jsonArray.getJSONObject(i).getString("created_time"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString("created_by_user_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvType(jsonArray.getJSONObject(i).getString("inv_type"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceId(jsonArray.getJSONObject(i).getString("invoice_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString("invoice_no"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("point_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCRM_ID(jsonArray.getJSONObject(i).getString("crm_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceState(jsonArray.getJSONObject(i).getString("invoice_state"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoicePaid(jsonArray.getJSONObject(i).getString("invoice_payed"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("client_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientName(jsonArray.getJSONObject(i).getString("client_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientPhone(jsonArray.getJSONObject(i).getString("client_phone"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientHolderName(jsonArray.getJSONObject(i).getString("holder_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }


                        try {
                            aa.setModifiedDate(jsonArray.getJSONObject(i).getString("modifiedDate"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedTime(jsonArray.getJSONObject(i).getString("modifiedTime"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedByUserName(jsonArray.getJSONObject(i).getString("modifiedByUserName"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }

                        if (!aa.getInvoiceState().equals("3")){
                            temp.add(0,aa);
                        }


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

    public void getInvoicesByClintId2(Activity c , String clientId){
        listOfInvoices = new MutableLiveData<>();
        ArrayList<InvoiceModel> temp = new ArrayList<>();
        setProgressDialog(c);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype" , "6");
            jsonObject.put("Key" , " and cli.client_id="+clientId);
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
                        try {
                            aa.setCreatedDate(jsonArray.getJSONObject(i).getString("created_date"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedTime(jsonArray.getJSONObject(i).getString("created_time"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCreatedByUserId(jsonArray.getJSONObject(i).getString("created_by_user_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvType(jsonArray.getJSONObject(i).getString("inv_type"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceAmount(jsonArray.getJSONObject(i).getString("invoice_amount"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceId(jsonArray.getJSONObject(i).getString("invoice_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceNumber(jsonArray.getJSONObject(i).getString("invoice_no"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("point_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setCRM_ID(jsonArray.getJSONObject(i).getString("crm_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceState(jsonArray.getJSONObject(i).getString("invoice_state"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoicePaid(jsonArray.getJSONObject(i).getString("invoice_payed"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setInvoiceUnpaid(jsonArray.getJSONObject(i).getString("invoice_unpaid"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientId(jsonArray.getJSONObject(i).getString("client_id"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientName(jsonArray.getJSONObject(i).getString("client_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientPhone(jsonArray.getJSONObject(i).getString("client_phone"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setClientHolderName(jsonArray.getJSONObject(i).getString("holder_name"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }


                        try {
                            aa.setModifiedDate(jsonArray.getJSONObject(i).getString("modifiedDate"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedTime(jsonArray.getJSONObject(i).getString("modifiedTime"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }
                        try {
                            aa.setModifiedByUserName(jsonArray.getJSONObject(i).getString("modifiedByUserName"));
                        }catch (Exception e){
                            String s = e.toString();
                            errorDialog(c,e.toString());
                        }

                        temp.add(0,aa);


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

    public void deleteInvoice(Activity c , String invoiceId,String userId ){

    }

    public void updateInv(Activity c , InvoiceModel invoiceModel , String invAmount,String userId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Datatype","1" );
            jsonObject.put("InvoiceId", invoiceModel.getInvoiceId() );
            jsonObject.put("CreatedDate", invoiceModel.getCreatedDate() );
            jsonObject.put("CreatedTime", invoiceModel.getCreatedTime() );
            jsonObject.put("CreatedByUserId", invoiceModel.getCreatedByUserId() );
            jsonObject.put("InvType", invoiceModel.getInvType() );
            jsonObject.put("InvoiceAmount", invoiceModel.getInvoiceAmount() );
            jsonObject.put("InvoiceNo", invoiceModel.getInvoiceNumber() );
            jsonObject.put("IsDeleted", invoiceModel.getIsDeleted() );
            jsonObject.put("PointId", invoiceModel.getClientId() );
            jsonObject.put("Note", invoiceModel.getNote() );

            Volley.newRequestQueue(c).add(new JsonObjectRequest(Request.Method.POST,
                    ClassAPIs.UpdateInvoices, jsonObject, new Response.Listener<JSONObject>() {
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

        }catch (Exception e){
            errorDialog(c,e.toString());
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
        progressDialog.dismiss();
    }
}
