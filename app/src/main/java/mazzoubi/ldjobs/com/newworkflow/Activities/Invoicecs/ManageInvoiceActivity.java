package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters.adapterInvoices2;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.InvoiceViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class ManageInvoiceActivity extends AppCompatActivity {

    ListView listView ;
    FirebaseFirestore db;
    ArrayList<InvoiceModel> invoices ;
    String myFilePath = "";
    TextView exportExcel;
    public static ArrayList<UserModel> employees ;
    public static ArrayList<ClientModel> clients ;
    ArrayList<String> strClients;
    TextView txvSumUnpaid,txvDateFrom , txvDateTo ,txvShowOrCloseFilter , dept;
    String dateFrom="",dateTo="" ;

    AutoCompleteTextView aCInvNo,aCPoint,aCEmpName;
    RadioButton rbPaid ,rbPartiallyPaid,rbCreated,rbAll,rbAllUnpaid;
    Button btnSearch ;

    CardView cardView ;

    CheckBox checkBox ;

    boolean filterIsClosed = true;

    public static InvoiceModel invObject ;

    InvoiceViewModel invoicesViewModel ;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_invoice);

        init();

        exportExcel = findViewById(R.id.textView12);
        exportExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveExcelFile(invoiceLogActivity.this);
            }
        });

        invoicesViewModel.listOfInvoices.observe(this, new Observer<ArrayList<InvoiceModel>>() {
            @Override
            public void onChanged(ArrayList<InvoiceModel> classInvoice2s) {
                invoices=classInvoice2s;

                double sumUnpaid = 0 ;
                double sumPaid = 0;
                double sumTotal = 0 ;
                int invCount = 0 ;
                for(InvoiceModel d : classInvoice2s){
                    sumUnpaid += Double.parseDouble(d.getInvoiceUnpaid());
                    sumPaid += Double.parseDouble(d.getInvoicePaid());
                    sumTotal += Double.parseDouble(d.getInvoiceAmount());
                    invCount++;
                }
                ArrayAdapter<InvoiceModel> adapter = new adapterInvoices2(getApplicationContext(),R.layout.row_invoices,classInvoice2s);
                listView.setAdapter(adapter);
                txvSumUnpaid.setText("عدد الفواتير: "+invCount+"\n"+"المجموع الاجمالي: "+sumTotal+"\n"+"مجموع الغير مدفوع: "+sumUnpaid+"\n"+"مجموع المدفوعات: "+sumPaid);
                progressDialog.dismiss();


            }
        });
        txvShowOrCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterIsClosed){
                    txvShowOrCloseFilter.setText("اغلاق الفلتر ...");
                    cardView.setVisibility(View.VISIBLE);
                    filterIsClosed=false;
                }else {
                    txvShowOrCloseFilter.setText("عرض الفلتر ...");
                    cardView.setVisibility(View.GONE);
                    filterIsClosed=true;
                }
            }
        });

        txvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateFrom();
            }
        });

        txvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTo();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String pointId = "" ;
                for (int i =0 ; i<clients.size();i++){
                    if (aCPoint.getText().toString().equals(clients.get(i).getClientName())){
                        pointId = clients.get(i).getClientId();
                        break;
                    }
                }

                if (checkBox.isChecked()&& !aCPoint.getText().toString().isEmpty()){
                    invoicesViewModel.getInvoicesByClintId(ManageInvoiceActivity.this,pointId+"");
                }else {
                    invoicesViewModel.getInvoiceByFilter(ManageInvoiceActivity.this ,aCInvNo.getText().toString(),dateFrom,dateTo,
                            pointId,aCEmpName.getText().toString(),rbCreated.isChecked(),rbPaid.isChecked(),
                            rbPartiallyPaid.isChecked(),rbAll.isChecked(),rbAllUnpaid.isChecked(),false);
                }

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                invObject = new InvoiceModel();
                invObject = invoices.get(position);
                if (!getSharedPreferences("User",MODE_PRIVATE).getString("Type", "-").equals("0")
                ||!getSharedPreferences("User",MODE_PRIVATE).getString("Type", "-").equals("1")){

                }else {
                    startActivity(new Intent(getApplicationContext(),ManageInvoiceActivity.class));
                }

            }
        });
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), empsActivity2.class));
            }
        });


    }




    void init(){
        db=FirebaseFirestore.getInstance();
        invoicesViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
        listView = findViewById(R.id.listView);
        cardView =findViewById(R.id.cardView3);
        txvShowOrCloseFilter = findViewById(R.id.txvShowOrCloseFilter);
        txvDateFrom = findViewById(R.id.txvDateFrom);
        txvDateTo = findViewById(R.id.txvDateTo);
        txvSumUnpaid=findViewById(R.id.txvSumUnbaid);
        dateFrom= ClassDate.date();
        dateTo=ClassDate.date();
        txvDateFrom.setText("التاريخ من: "+dateFrom);
        txvDateTo.setText("التاريخ الى: "+dateTo);
        dept=findViewById(R.id.txvShowOrCloseFilter3);
        checkBox=findViewById(R.id.checkBox6);

        aCInvNo = findViewById(R.id.aCInvNo);
        aCPoint = findViewById(R.id.aCPoint);
        aCEmpName = findViewById(R.id.aCEmpName);

        rbPaid = findViewById(R.id.rbPaid);
        rbPartiallyPaid = findViewById(R.id.rbPartiallyPaid);
        rbCreated = findViewById(R.id.rbCreated);
        rbAll = findViewById(R.id.rbAll);
        rbAllUnpaid = findViewById(R.id.rbAllUnpaid);

        progressDialog = new ProgressDialog(ManageInvoiceActivity.this);
        progressDialog.setTitle("الرجاء الانتظار...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        btnSearch = findViewById(R.id.btnSearch);

        if (getSharedPreferences("emp",MODE_PRIVATE).getString("type", "-").equals("1")){
            dept.setVisibility(View.INVISIBLE);
            txvShowOrCloseFilter.setText("اغلاق الفلتر ...");
            cardView.setVisibility(View.VISIBLE);
            filterIsClosed=false;
        }
        getAllClient();
        getAllEmp();

//        getInvoiceByFilter();



    }

    void getAllEmp(){
     employees = new ArrayList<>();
     ArrayList<String>strEmployees = new ArrayList<>();
     UserViewModel vm = ViewModelProviders.of(this).get(UserViewModel.class);
     vm.getUsers(ManageInvoiceActivity.this);
        vm.listUsers.observe(this, new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                for (UserModel d: userModels){
                    employees.add(d);
                    strEmployees.add(d.getName());
                }
            }
        });
    }

    void showDateFrom(){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date_ = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "d-M-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateFrom=sdf.format(myCalendar.getTime());
                txvDateFrom.setText("التاريخ من: "+dateFrom);

            } };
        new DatePickerDialog(ManageInvoiceActivity.this, date_, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    void showDateTo(){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date_ = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "d-M-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateTo=sdf.format(myCalendar.getTime());
                txvDateTo.setText("الى: "+dateTo);

            } };
        new DatePickerDialog(ManageInvoiceActivity.this, date_, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    void getAllClient(){
        clients=new ArrayList<>();
        strClients = new ArrayList<>();
        ClientsViewModel vm = ViewModelProviders.of(this).get(ClientsViewModel.class);
        vm.getAllClients(ManageInvoiceActivity.this);
        vm.listOfClient.observe(this, new Observer<ArrayList<ClientModel>>() {
            @Override
            public void onChanged(ArrayList<ClientModel> clientModels) {
                for (ClientModel d: clientModels){
                    clients.add(d);
                    strClients.add(d.getClientName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,strClients);
                aCPoint.setAdapter(adapter);

            }
        });

        invoicesViewModel.getInvoiceByFilter(ManageInvoiceActivity.this ,aCInvNo.getText().toString(),dateFrom,dateTo,
                aCPoint.getText().toString(),aCEmpName.getText().toString(),rbCreated.isChecked(),rbPaid.isChecked(),
                rbPartiallyPaid.isChecked(),rbAll.isChecked(),rbAllUnpaid.isChecked(),false);
    }

    public class CustomDialogVisitInfo extends Dialog {
        public Activity c;

        public CustomDialogVisitInfo(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_invoice_log);
            init();

        }

    }


}