package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters.AdapterOpenInvoice;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
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

        vm.getPaymentsByFilter(activity,txvDateFrom.getText().toString(),txvDateTo.getText().toString(),ss, cc);
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
}