package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceLogModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.InvoiceLogViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.InvoiceViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.PaymentViewModel;

public class InvoiceInfoActivity extends AppCompatActivity {

    TextView txvInvState ,txvInvoiceNo,txvCreatedBy,
            txvPointName,txvCreatedDate,txvModifiedDate,txvChangeInfo;

    EditText edtInvAmount,edtInvPayment,edtInvUnpaid;

    Button btnDeleteInv , btnPayLog ,btnInvLog , btnMoreDetailInvLog;

    InvoiceModel invoice ;

    InvoiceViewModel invoicesViewModel ;

    ArrayList<PaymentModel> invoicePayments ;
    ArrayList<InvoiceLogModel> eventLogModels ;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_invoice_info);


        progressDialog=new ProgressDialog(InvoiceInfoActivity.this);
        progressDialog.setTitle("الرجاء الانتظار...");
        progressDialog.show();

        init();

        btnDeleteInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(InvoiceInfoActivity.this);
                builder.setTitle("النظام...");
                builder.setMessage("هل تريد حذف هذه الفاتورة؟");
                builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invoicesViewModel.deleteInvoice(InvoiceInfoActivity.this,invoice.getInvoiceId()
                                , getSharedPreferences("User",MODE_PRIVATE).getString("Id","") );
                    }
                });
                builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });



        btnPayLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventLogModels= new ArrayList<>();
                CustomDialogInvoiceLog aa = new CustomDialogInvoiceLog(InvoiceInfoActivity.this);
                aa.show();
            }
        });

        btnMoreDetailInvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogMoreDetailInvLog d = new CustomDialogMoreDetailInvLog(InvoiceInfoActivity.this);
                d.show();
            }
        });

        btnInvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoicePayments = new ArrayList<>();
                CustomDialogPaymentLog aa = new CustomDialogPaymentLog(InvoiceInfoActivity.this);
                aa.show();
            }
        });

        txvChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                double afterChangeInvAm = Double.parseDouble(edtInvAmount.getText().toString());
                double invPay = Double.parseDouble(invoice.getInvoicePaid());
                if (afterChangeInvAm<=0){
                    Toast.makeText(InvoiceInfoActivity.this, "لايجوز ان يكون مبلغ الفاتورة اقل او يساوي صفر!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    if (Double.parseDouble(invoice.getInvoicePaid())>afterChangeInvAm){
                        Toast.makeText(InvoiceInfoActivity.this, "لايجوز ان تكون قيمة الفاتورة اقل من قيمة الدفعات عليها!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        if(invPay>0){
                            Toast.makeText(InvoiceInfoActivity.this, "لايجوز تعديل فاتورة عليها عمليات دفع!", Toast.LENGTH_SHORT).show();
                        }else {
                            invoicesViewModel.updateInv(InvoiceInfoActivity.this,invoice.getInvoiceId(),
                                    afterChangeInvAm+"",
                                    getSharedPreferences("emp",MODE_PRIVATE).getString("id",""));
                        }


                    }
                }

            }
        });

    }

    void init(){
        invoice = ManageInvoiceActivity.invObject;
        invoicesViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
        btnDeleteInv = findViewById(R.id.btnDeleteInv);
        btnPayLog = findViewById(R.id.btnInvLog2);
        btnInvLog = findViewById(R.id.btnInvLog);
        btnMoreDetailInvLog = findViewById(R.id.btnInvLog3);

        txvInvState = findViewById(R.id.txvInvState);
        txvInvoiceNo = findViewById(R.id.txvChargeRequest);
        txvCreatedBy = findViewById(R.id.txvCreatedBy);
        txvPointName = findViewById(R.id.txvPointName);
        txvCreatedDate = findViewById(R.id.txvCreatedDate);
        txvModifiedDate = findViewById(R.id.txvModifiedDate);
        txvChangeInfo = findViewById(R.id.txvChangeInfo);

        edtInvAmount = findViewById(R.id.edtInvAmount);
        edtInvPayment = findViewById(R.id.edtInvPayment);
        edtInvUnpaid = findViewById(R.id.edtInvUnpaid);

        progressDialog.dismiss();
        setContent();

    }

    void setContent(){
        txvInvoiceNo.setText("رقم الفاتورة: "+invoice.getInvoiceNumber());
        txvCreatedBy.setText("انشأت بواسطة: "+invoice.getCreatedByUserName());
        txvPointName.setText("اسم النقطة: "+invoice.getClientName());
        txvCreatedDate.setText("تاريخ الانشاء: "+invoice.getCreatedDate()+" "+invoice.getCreatedTime());
        txvModifiedDate.setText("تاريخ التعديل: "+invoice.getModifiedDate()+" "+invoice.getModifiedTime());

        edtInvAmount.setText(invoice.getInvoiceAmount());
        edtInvPayment.setText(invoice.getInvoicePaid());
        edtInvUnpaid.setText(invoice.getInvoiceUnpaid());



        if (invoice.getInvoiceState().equals("1")){
            txvInvState.setText("انشأت");
            txvInvState.setBackgroundColor(Color.RED);
        }else if (invoice.getInvoiceState().equals("2")){
            txvInvState.setText("مدفوعة جزئياً");
            txvInvState.setBackgroundColor(Color.YELLOW);
        }else if (invoice.getInvoiceState().equals("3")){
            txvInvState.setText("مدفوعة");
            txvInvState.setBackgroundColor(Color.GREEN);
        }else if (invoice.getInvoiceState().equals("4")){
            txvInvState.setText("مدفوعة");
            txvInvState.setBackgroundColor(Color.BLUE);
        }
    }

    public class CustomDialogInvoiceLog extends Dialog {
        public Activity c;

        public CustomDialogInvoiceLog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_invoice_log);

            ListView listView =findViewById(R.id.listView);
            ArrayList<String> log = new ArrayList<>();
            InvoiceLogViewModel aa = ViewModelProviders.of(InvoiceInfoActivity.this).get(InvoiceLogViewModel.class);
            aa.getLogs(InvoiceInfoActivity.this,invoice.getInvoiceId());
            aa.listOfLog.observe(InvoiceInfoActivity.this, new Observer<ArrayList<InvoiceLogModel>>() {
                @Override
                public void onChanged(ArrayList<InvoiceLogModel> invoiceLogModels) {
                    for(InvoiceLogModel d: invoiceLogModels){
                        log.add(d.getDescription());
                    }
                }
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1,log);
            listView.setAdapter(adapter);

        }

    }

    public class CustomDialogMoreDetailInvLog extends Dialog {
        public Activity c;
        ListView listView;
        public CustomDialogMoreDetailInvLog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_invoice_log);

//            listView =findViewById(R.id.listView);
//            final ArrayList<String> log = new ArrayList<>();
//
//            FirebaseFirestore.getInstance().collection("eventLog")
//                    .whereEqualTo("invoiceNo",invoice.invoiceNo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
//                        log.add(d.getString("title"));
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1,log);
//                    listView.setAdapter(adapter);
//                }
//            });




        }

    }

    public class CustomDialogPaymentLog extends Dialog {
        public Activity c;
        int deletePosition ;
        ListView listView =findViewById(R.id.listView);
        ArrayList<String> strInvoicePayment;
        public CustomDialogPaymentLog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_invoice_log);

            final ListView listView1 =findViewById(R.id.listView);

            PaymentViewModel paymentViewModel = ViewModelProviders.of(InvoiceInfoActivity.this).get(PaymentViewModel.class);
            paymentViewModel.getPaymentByInvoiceId(InvoiceInfoActivity.this,invoice.getInvoiceId());
            paymentViewModel.listOfPayments.observe(InvoiceInfoActivity.this, new Observer<ArrayList<PaymentModel>>() {
                @Override
                public void onChanged(ArrayList<PaymentModel> paymentModels) {
                    progressDialog.dismiss();
                    strInvoicePayment = new ArrayList<>();
                    for (PaymentModel d:paymentModels){
                        invoicePayments.add(d);
                        strInvoicePayment.add(
                                "اضيفت عملية بواسطة "+d.getUser_name()+" للفاتورة رقم "+d.getInvoice_no()+" يقيمة "+d.getAmount()+" دينار "
                                        +" بتاريخ "+d.getCreated_date()+" "+d.getCreated_time()+" رقم عملية الدفع "+d.getPayment_id()
                        );
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(c , android.R.layout.simple_list_item_1,strInvoicePayment);
                    listView1.setAdapter(adapter);
                }
            });



            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                    dialog.setTitle("هل تريد حذف الدفعة؟");
                    dialog.setPositiveButton("نعم", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePosition=position;
                            paymentViewModel.deletePayment(c,invoicePayments.get(position).getPayment_id(),
                                    getSharedPreferences("User",MODE_PRIVATE).getString("Id",""));
                        }
                    });
                    dialog.setNegativeButton("لا", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

        }

    }


}