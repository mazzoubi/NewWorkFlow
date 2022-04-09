package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters.adapterInvoices2;
import mazzoubi.ldjobs.com.newworkflow.Activities.Main.DashboardActivity;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.InvoiceViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.PaymentViewModel;

public class AddNewInvoicePaymentActivity extends AppCompatActivity {

    TextView txvpointName, txvPointId , txvSumUnpaid, txvClosedInvoice , txvOpenInvoice,txvDeptInfo;

    ArrayList<InvoiceModel> invoices ;
    ArrayList<InvoiceModel> unPaidInvoices ;
    ListView listView ;
    String clientId = "";
    String clientName = "";

    String type ="1";
    String paymentType = "1" ;
    String bankId ="";
    double dept =0 ;
    int openInvCount = 0 ;
    int closeInvCount =0 ;

    ClientModel client ;
    Activity activity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_invoice_payment);
        init();
    }


    void init() {
        activity=AddNewInvoicePaymentActivity.this;
        txvOpenInvoice = findViewById(R.id.txvOpenInvoice);
        txvClosedInvoice = findViewById(R.id.txvClosedInvoice);
        txvSumUnpaid = findViewById(R.id.txvSumUnpaid);
        txvPointId = findViewById(R.id.txvPointId);
        txvpointName = findViewById(R.id.txvpointName);
        txvDeptInfo = findViewById(R.id.txvDeptInfo);
        listView = findViewById(R.id.listView);
        client = DashboardActivity.clientObj;
        clientName = client.getClientName();
        clientId = client.getClientId();
        txvpointName.setText(clientName);
        txvPointId.setText(clientId);

        txvDeptInfo.setText("قيمة الذمة المسموحه: "+client.getMax_debt() + "  " + "عدد الفواتير المسموح: "+
                client.getMax_inv());

        getInvoice();

    }

    void getInvoice(){
        invoices = new ArrayList<>();
        unPaidInvoices = new ArrayList<>();
        InvoiceViewModel invoiceViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
        invoiceViewModel.getInvoicesByClintId(activity,client.getClientId());
        invoiceViewModel.listOfInvoices.observe(this, new Observer<ArrayList<InvoiceModel>>() {
            @Override
            public void onChanged(ArrayList<InvoiceModel> invoiceModels) {
                for (InvoiceModel d : invoiceModels){
                    invoices.add(d);
                    dept = Double.parseDouble(d.getInvoiceUnpaid());
                    if (!d.getInvoiceState().equals("3")){
                        openInvCount++;
                    }else {
                        closeInvCount++;
                    }
                }
                txvOpenInvoice.setText(openInvCount+"");
                txvClosedInvoice.setText(closeInvCount+"");
                txvSumUnpaid.setText(dept+"");
                ArrayAdapter<InvoiceModel> adapter = new adapterInvoices2(getApplicationContext()
                        ,R.layout.row_invoices,invoices);
                listView.setAdapter(adapter);
            }
        });
    }

    public void onClickAddInv(View view) {
        AddPayment a = new AddPayment();
        a.show();
    }


    class AddPayment extends Dialog {
        public AddPayment(){
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_insert_num);
            EditText edtValue = findViewById(R.id.edtValue);
            Button btnAdd = findViewById(R.id.btnAdd);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("النظام...");
                    builder.setMessage("هل تريد تأكيد اضافة دفعة للعميل "+clientName+" بقيمة "+
                            edtValue.getText().toString()+" دينار؟ ");
                    builder.setPositiveButton("تأكيد", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addPayment(clientId,edtValue.getText().toString(),paymentType,type,bankId);
                        }
                    });
                    builder.setNegativeButton("الغاء", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                }
            });


        }
    }

    void addPayment(String clientID,String amount,String paymentType, String type,String bankId){
        PaymentViewModel vm =ViewModelProviders.of((FragmentActivity) activity).get(PaymentViewModel.class);
        vm.addNewInvoicesPayments(activity,amount,amount,paymentType,type,bankId);
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