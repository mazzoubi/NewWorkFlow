package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Banks.BanksModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.PaymentModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Banks.BanksViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.PaymentViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class CollectionReportActivity extends AppCompatActivity {

    TextView txvDateFrom , txvDateTo ,txvSum;
    AutoCompleteTextView edtClientName ;
    Spinner spinnerBankName , spinnerUser ;
    CheckBox checkBoxIsBank;
    ListView listView;
    Activity activity ;

    ArrayList<PaymentModel> payments ;
    ArrayList<String> strPayments ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_report);
        init();


    }

    void init(){
        activity = CollectionReportActivity.this;
        txvDateFrom = findViewById(R.id.txvDateFrom);
        txvDateTo = findViewById(R.id.txvDateTo);
        txvSum = findViewById(R.id.txvSum);
        spinnerBankName = findViewById(R.id.edtBankName);
        spinnerUser = findViewById(R.id.spinnerUser);
        edtClientName = findViewById(R.id.edtClientName);
        checkBoxIsBank = findViewById(R.id.checkBoxIsBank);
        listView = findViewById(R.id.listView);

        txvDateTo.setText(ClassDate.date());
        txvDateFrom.setText(ClassDate.date());

        getUsers();
        getClients();
        getBanks();


    }




    ArrayList<BanksModel> banks;
    ArrayList<String> strBanks;
    void getBanks(){
        banks = new ArrayList<>();
        strBanks = new ArrayList<>();
        banks.add(new BanksModel());
        strBanks.add("اختر البنك");
        BanksViewModel vm = ViewModelProviders.of(this).get(BanksViewModel.class);
        vm.getBanks(activity);
        vm.listOfBanks.observe(this, new Observer<ArrayList<BanksModel>>() {
            @Override
            public void onChanged(ArrayList<BanksModel> banksModels) {
                for (BanksModel d: banksModels){
                    banks.add(d);
                    strBanks.add(d.getName());
                }
                ArrayAdapter<String>adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strBanks);
                spinnerBankName.setAdapter(adapter);
            }
        });
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
                ArrayAdapter<String>adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strClients);
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
        payments=new ArrayList<>();
        strPayments=new ArrayList<>();
        PaymentViewModel vm = ViewModelProviders.of((FragmentActivity) activity).get(PaymentViewModel.class);
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
        String bb = "";
        if (spinnerBankName.getSelectedItemPosition()==0){

        }else {
            bb=clients.get(spinnerUser.getSelectedItemPosition()).getClientId();
        }

        if(checkBoxIsBank.isChecked()){
            vm.getBankPaymentsByFilter(activity,txvDateFrom.getText().toString(),
                    txvDateTo.getText().toString(),ss,cc,bb);
        }else {
            vm.getPaymentsByFilter(activity,txvDateFrom.getText().toString(),
                    txvDateTo.getText().toString(),ss,cc);
        }

        vm.listOfPayments.observe(this, new Observer<ArrayList<PaymentModel>>() {
            @Override
            public void onChanged(ArrayList<PaymentModel> paymentModels) {
                double sum = 0 ;
                for (PaymentModel d: paymentModels){
                    payments.add(d);
                    strPayments.add("" +
                            "اسم النقطة: " +d.getClientName()+"\n"+
                            "اسم المستخدم: " +d.getUser_name()+"\n"+
                            "تاريخ الحركة: " +d.getCreated_date()+"\n"+
                            "وقت الحركة: " +d.getCreated_time()+"\n"+
                            "مرجع الحركة: " +d.getPayment_id()+"\n"+
                            "رقم الفاتورة المسددة: " +d.getInvoice_no()+"\n"+
                            "اجمالي الفاتورة: " +d.getAmount()+"\n"+
                            "المتبقي من الفاتورة: " +d.getInvoiceUnpaid()+"\n"+
                            "الدفعة الحالية: " +d.getAmount()+"\n"+
                            "");
                    sum+=Double.parseDouble(d.getAmount());
                }
                txvSum.setText("مجموع الحركات: "+sum);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strPayments);
                listView.setAdapter(adapter);
            }
        });
    }
}