package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters.AdapterOpenInvoice;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.OpenInvoiceViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class OpenInvoiceActivity extends AppCompatActivity {

    TextView txvDateFrom , txvDateTo ,txvSum;
    AutoCompleteTextView edtClientName ;
    Spinner spinnerUser ;
    ListView listView;
    Activity activity ;

    ArrayList<OpenInvoiceModel> openInvoices ;
    ArrayList<String> strOpenInvoices ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_invoice);
        init();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int x = i;
                new AlertDialog.Builder(activity)
                        .setMessage(openInvoices.get(i).getInvoiceNo()+" هل ترغب بمشاركة معلومات هذه الفاتورة ؟ ")
                        .setPositiveButton("مشاركة", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                try {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                                    intent.putExtra(Intent.EXTRA_TEXT, openInvoices.get(x).getSharedBody());

                                    if(!openInvoices.get(x).getSharedBody().equals("")){
                                        startActivity(Intent.createChooser(intent, "Share")); }
                                    else{
                                        intent = new Intent(Intent.ACTION_SEND);
                                        String shareBody = "";
                                        shareBody+= "إسم العميل : "+openInvoices.get(x).getClientNam()+"\n";
                                        shareBody+= "رقم الفاتورة : "+openInvoices.get(x).getInvoiceNo()+"\n";
                                        shareBody+= "قيمة الفاتورة : "+openInvoices.get(x).getInvoiceAmount()+" دينار"+"\n";
                                        shareBody+= "رقم النقطة التعريفي : "+openInvoices.get(x).getClientId()+"\n";
                                        shareBody+= "تاريخ فتح الفاتورة : "+openInvoices.get(x).getDate()+"\n";
                                        shareBody+= "وقت فتح الفاتورة : "+openInvoices.get(x).getTime()+"\n";
                                        shareBody+= "قيمة الذمم للنقطة : "+openInvoices.get(x).getPointDept()+" دينار"+"\n";
//                                        shareBody+= "رقم تعريفي (دفترة) : "+openInvoices.get(x).resp+"\n";
                                        shareBody+= "إسم منشئ الفاتورة : "+openInvoices.get(x).getUserName()+"\n";

                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                                        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                        startActivity(Intent.createChooser(intent, "Share"));
                                    }
                                }
                                catch (Exception e){ }

                            }
                        }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!UserInfo.getUser(OpenInvoiceActivity.this).getType().equals("3")){
                    AcceptCharge a = new AcceptCharge(OpenInvoiceActivity.this,
                            openInvoices,i);
                    a.show();
                }
            }
        });

    }

    void init(){
        activity = OpenInvoiceActivity.this;
        txvDateFrom = findViewById(R.id.txvDateFrom);
        txvDateTo = findViewById(R.id.txvDateTo);
        txvSum = findViewById(R.id.txvSum);

        spinnerUser = findViewById(R.id.spinnerUser);
        edtClientName = findViewById(R.id.edtClientName);

        listView = findViewById(R.id.listView);

        txvDateTo.setText(ClassDate.date());
        txvDateFrom.setText(ClassDate.date());

        getUsers();
        getClients();

    }



    ArrayList<UserModel> users ;
    ArrayList<String> strUsers ;
    void getUsers(){
        users = new ArrayList<>();
        strUsers = new ArrayList<>();

        users.add(new UserModel());
        strUsers.add("اختر المستخدم");
        UserViewModel vm = ViewModelProviders.of(this).get(UserViewModel.class);
        vm.getUsers(activity);
        vm.listUsers.observe(this, new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                for (UserModel d:userModels){
                    users.add(d);
                    strUsers.add(d.getName());
                }
                if (getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("0")
                        ||getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("1")){

                }else {
                    for (UserModel d:userModels){
                        if (d.getId()
                                .equals(getSharedPreferences("Users",MODE_PRIVATE).getString("Id",""))){
                            users = new ArrayList<>();
                            strUsers = new ArrayList<>();
                            users.add(d);
                            strUsers.add(d.getName());
                        }
                    }
                }
                ArrayAdapter<String>adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strUsers);
                spinnerUser.setAdapter(adapter);
            }
        });
    }


    ArrayList<ClientModel> clients ;
    ArrayList<String> strClients ;
    void getClients(){
        clients = new ArrayList<>();
        strClients = new ArrayList<>();
        ClientsViewModel vm = ViewModelProviders.of(this).get(ClientsViewModel.class);
        vm.getAllClients(activity);
        vm.listOfClient.observe(this, new Observer<ArrayList<ClientModel>>() {
            @Override
            public void onChanged(ArrayList<ClientModel> clientModels) {
                for (ClientModel d:clientModels){
                    clients.add(d);
                    strClients.add(d.getClientName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strClients);
                edtClientName.setAdapter(adapter);
            }
        });
    }

    public void onClickDateFrom(View view) {
        ClassDate d = ViewModelProviders.of((FragmentActivity) activity).get(ClassDate.class);
        d.showDatePicker(activity);
        d.datePicker.observe((LifecycleOwner) activity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txvDateFrom.setText(s);
            }
        });
    }

    public void onClickDateTo(View view) {
        ClassDate d = ViewModelProviders.of((FragmentActivity) activity).get(ClassDate.class);
        d.showDatePicker(activity);
        d.datePicker.observe((LifecycleOwner) activity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txvDateTo.setText(s);
            }
        });
    }

    public void onClickSearch(View view) {
        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
        openInvoices = new ArrayList<>();
        strOpenInvoices = new ArrayList<>();
        OpenInvoiceViewModel vm = ViewModelProviders.of((FragmentActivity) activity).get(OpenInvoiceViewModel.class);

        String ss = "";
        if (getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("0")
                ||getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("1")){
            if (spinnerUser.getSelectedItemPosition()==0){
                ss="";
            }else {
                ss= users.get(spinnerUser.getSelectedItemPosition()).getId();
            }

        }else {
            ss= users.get(spinnerUser.getSelectedItemPosition()).getId();
        }

        String cc = "";
        if (edtClientName.getText().toString().isEmpty()){

        }else {
            for (ClientModel d:clients){
                if (edtClientName.getText().toString().equals(d.getClientName())){
                    cc = d.getClientId();
                    break;
                }
            }
        }

        vm.getOpenInvoiceByFilter(activity,txvDateFrom.getText().toString(),txvDateTo.getText().toString(),ss, cc);
        vm.listOfOpenInvoice.observe(this, new Observer<ArrayList<OpenInvoiceModel>>() {
            @Override
            public void onChanged(ArrayList<OpenInvoiceModel> openInvoiceModels) {
                for (OpenInvoiceModel d : openInvoiceModels){
                    openInvoices.add(d);
                }

                ArrayAdapter<OpenInvoiceModel> adapter = new AdapterOpenInvoice(activity,
                        R.layout.row_open_invoices,openInvoices);
                listView.setAdapter(adapter);
            }
        });
    }



    public class AcceptCharge extends Dialog {
        public Activity c;
        public Dialog d;

        ArrayList<OpenInvoiceModel> list;

        Button btnok;

        TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
        int pos = 0;
        EditText et;

        public AcceptCharge(Activity a, ArrayList<OpenInvoiceModel> list, int pos) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.list = list;
            this.pos = pos;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_open_invoice);
            init();


        }


        void init() {
            btnok = findViewById(R.id.btnAdd);
            t1 = findViewById(R.id.txvPointName);
            t2 = findViewById(R.id.txvPhone);
            t3 = findViewById(R.id.txvSumUnpaid);
            t4 = findViewById(R.id.txvClosedInvoice);
            t5 = findViewById(R.id.txvOpenInvoice);
            t6 = findViewById(R.id.txvOpenInvoice7);
            t7 = findViewById(R.id.txvOpenInvoice8);
            t8 = findViewById(R.id.txvOpenInvoice81);
            t9 = findViewById(R.id.txvOpenInvoice82);
            t10 = findViewById(R.id.txvOpenInvoice83);

            et = findViewById(R.id.acc_note);

            t1.setText(list.get(pos).getClientNam());
            t2.setText(list.get(pos).getClientId());
            t3.setText(list.get(pos).getDate());
            t4.setText(list.get(pos).getTime());
            t5.setText(list.get(pos).getInvoiceAmount());
            t6.setText(list.get(pos).getPointDept());
            t7.setText(list.get(pos).getId());
            t8.setText(list.get(pos).getId());
            t9.setText(list.get(pos).getUserName());
            t10.setText(list.get(pos).getInvoiceNo());

            if(!list.get(pos).getState().equals("-1")){
                et.setText(list.get(pos).getChargeState());
                btnok.setText("تعديل");
            }



            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!et.getText().toString().equals("")){
                        list.get(pos).setChargeState(et.getText().toString());

                        OpenInvoiceViewModel a = ViewModelProviders.of(OpenInvoiceActivity.this).get(OpenInvoiceViewModel.class);
                        a.acceptOpenInv(OpenInvoiceActivity.this,list.get(pos));
                    }
                    else{
                        Toast.makeText(c, "الرجاء عدم ترك حقول فارغة", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }
}