package mazzoubi.ldjobs.com.newworkflow.Activities.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import mazzoubi.ldjobs.com.newworkflow.Activities.Admin.AdminDashboardActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Banks.AddBankActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Clients.AddNewClientActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoicePaymentActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.CollectionReportActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.ManageInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.OpenInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.AcceptExchangeActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.NewExchangeActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.AddNewUserActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.ProfileActivity;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Permissions.classPermissions;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Permissions.PermissionViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class Dashboard3Activity extends AppCompatActivity {
    CircleImageView cvNewVisit, cvVisits, cvAdmin,
            cvfingerprint, cvFinance, cvnewfinnance,
            profile, close, hr, open, view, noti,
            adb, addNewClient, stock, no_qr, compare,
            expenses, mgmt, charge, bank, exchange,
            view_exchange, cash, requestChange,
            changeReport, ezdist;

    TextView txt1, txt2, txt3, txt4, txt5, txt7, txt8,
            txt9, txt10, app_ver, app_ver2, txt11,
            txt12, txt13, txt14, desc12, compare_titel,
            txt15, txt16, txt17, txt18, txt19, txt20,
            txt21, txvUserName, txvUserMobile, txvUserType, txvUserCommission, head3;

    classPermissions permessions;

    public static Activity ThisActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard3);
        ThisActivity = Dashboard3Activity.this;
//        UserInfo.logout(ThisActivity);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckPermession();
        getUserInfo();
    }

    void init() {

        cvNewVisit = findViewById(R.id.newtask);
        cvVisits = findViewById(R.id.viewtask_new);
        cvAdmin = findViewById(R.id.admin);
        cvfingerprint = findViewById(R.id.fingerprint);
        cvFinance = findViewById(R.id.payments);
        cvnewfinnance = findViewById(R.id.newpayments);
        hr = findViewById(R.id.finger);
        profile = findViewById(R.id.logo4);
        open = findViewById(R.id.openinvoice);
        view = findViewById(R.id.task_invoice);
        noti = findViewById(R.id.noti);
        adb = findViewById(R.id.dev);

        no_qr = findViewById(R.id.btnVisitWithoutQr);
        compare = findViewById(R.id.compare);

        addNewClient = findViewById(R.id.new_client);
        stock = findViewById(R.id.stock);
        expenses = findViewById(R.id.stock2);
        mgmt = findViewById(R.id.mgmtinvoices);
        charge = findViewById(R.id.email);
        bank = findViewById(R.id.bank);
        ezdist = findViewById(R.id.ezdist);
        exchange = findViewById(R.id.exchange);
        view_exchange = findViewById(R.id.view_exchange);
        cash = findViewById(R.id.cash);
        requestChange = findViewById(R.id.cash11);
        changeReport = findViewById(R.id.cash22);

        txt1 = findViewById(R.id.text1);
        txt2 = findViewById(R.id.txt23);
        txt3 = findViewById(R.id.task_txt23);
        txt4 = findViewById(R.id.txt2);
        txt5 = findViewById(R.id.fingerprint_title);
        txt7 = findViewById(R.id.newpayments_title);
        txt8 = findViewById(R.id.payments_title);
        txt9 = findViewById(R.id.finger_title);
        txt10 = findViewById(R.id.txvadmin);
        app_ver = findViewById(R.id.ver);
        txt11 = findViewById(R.id.dev_title);
        txt12 = findViewById(R.id.title_new_client);
        txt13 = findViewById(R.id.stock_title);
        txt14 = findViewById(R.id.title_btnVisitWithoutQr);
        desc12 = findViewById(R.id.desc12);
        compare_titel = findViewById(R.id.compare_title);
        txt15 = findViewById(R.id.stock2_title);
        txt16 = findViewById(R.id.mgmtinvoices_title);
        txt17 = findViewById(R.id.txt2email);
        txt18 = findViewById(R.id.bank_title);
        txt19 = findViewById(R.id.exchange_title);
        txt20 = findViewById(R.id.view_exchange_title);
        txt21 = findViewById(R.id.ezdist_title);


        txvUserName = findViewById(R.id.name);
        txvUserMobile = findViewById(R.id.mobile);
        txvUserType = findViewById(R.id.type);
        txvUserCommission = findViewById(R.id.commission);


        getAllClient();
    }

    public void onClickAddUser(View view) {
        startActivity(new Intent(getApplicationContext(), AddNewUserActivity.class));
    }

    public void onClickAddClient(View view) {
        startActivity(new Intent(getApplicationContext(), AddNewClientActivity.class));
    }

    public void onClickAddInvoice(View view) {
        ChooseClientDialog a = new ChooseClientDialog("انشاء فاتورة عميل",
                new Intent(ThisActivity, AddNewInvoiceActivity.class));
        a.show();
    }

    public void onClickAddPayment(View view) {
        ChooseClientDialog a = new ChooseClientDialog("دفع فاتورة عميل",
                new Intent(ThisActivity, AddNewInvoicePaymentActivity.class));
        a.show();
    }

    public void onClickCollectionReport(View view) {
        startActivity(new Intent(getApplicationContext(), CollectionReportActivity.class));
    }


    void getAllClient() {
        DashboardActivity.clientsList = new ArrayList<>();
        DashboardActivity.strClientsList = new ArrayList<>();
        ClientsViewModel clientsViewModel = ViewModelProviders.of((FragmentActivity) ThisActivity).get(ClientsViewModel.class);
        clientsViewModel.getAllClients(ThisActivity);
        clientsViewModel.listOfClient.observe((LifecycleOwner) ThisActivity, new Observer<ArrayList<ClientModel>>() {
            @Override
            public void onChanged(ArrayList<ClientModel> clientModels) {
                for (ClientModel d : clientModels) {
                    DashboardActivity.clientsList.add(d);
                    DashboardActivity.strClientsList.add(d.getClientName());
                }
            }
        });
    }

    public void onClickManageInvoice(View view) {
        startActivity(new Intent(getApplicationContext(), ManageInvoiceActivity.class));
    }

    public void onClickAddBank(View view) {
        startActivity(new Intent(getApplicationContext(), AddBankActivity.class));
    }

    public void onClickCreateUserExchange(View view) {
        startActivity(new Intent(getApplicationContext(), NewExchangeActivity.class));
    }

    public void onClickAcceptUserExchange(View view) {
        startActivity(new Intent(getApplicationContext(), AcceptExchangeActivity.class));
    }

    public void onClickManageClients(View view) {
        startActivity(new Intent(getApplicationContext(), AcceptExchangeActivity.class));
    }

    public void onClickLogout(View view) {
        UserInfo.logout(ThisActivity);
    }

    public void onClickOpenInvoice(View view) {
        startActivity(new Intent(getApplicationContext(), OpenInvoiceActivity.class));
    }

    public void onClickAdmin(View view) {
        startActivity(new Intent(getApplicationContext(), AdminDashboardActivity.class));
    }

    public void onClickProfile(View view) {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }


    private class ChooseClientDialog extends Dialog {
        String title ;
        Intent intent ;
        public ChooseClientDialog(String title,Intent intent ) {
            super(ThisActivity);
            this.title = title;
            this.intent = intent ;
        }

        TextView txvTitle ;
        Button btnNext ;
        AutoCompleteTextView edtClientName ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_choose_clients);
            init();

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int flag=-1;
                    for (ClientModel d : DashboardActivity.clientsList){
                        if (d.getClientName().equals(edtClientName.getText().toString())){
                            flag =1;
                            DashboardActivity.clientObj = d;
                            break;
                        }
                    }
                    if (flag==1){
                        startActivity(intent);
                    }else {
                        Toast.makeText(ThisActivity, "لم يتم العثور على العميل!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void init(){
            txvTitle = findViewById(R.id.textView4);
            btnNext = findViewById(R.id.btnNext);
            edtClientName = findViewById(R.id.edtClientName);
            txvTitle.setText(title);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ThisActivity, android.R.layout.simple_list_item_1,DashboardActivity.strClientsList);
            edtClientName.setAdapter(adapter);
        }
    }


    public void CheckPermession() {
        permessions = new classPermissions();
        PermissionViewModel permissionViewModel = ViewModelProviders.of(this).get(PermissionViewModel.class);
        permissionViewModel.getPermissionsByUserId(Dashboard3Activity.this,UserInfo.getUser(Dashboard3Activity.this).getId());
        permissionViewModel.permission.observe(this, new Observer<classPermissions>() {
            @Override
            public void onChanged(classPermissions classPermissions) {
                permessions = classPermissions;


                try {
                    if(permessions.new_visit_qr.equals("1")){
                        txt1.setVisibility(View.VISIBLE);
                        cvNewVisit.setVisibility(View.VISIBLE); }
                    if(permessions.open_invoice.equals("1")){
                        txt2.setVisibility(View.VISIBLE);
                        open.setVisibility(View.VISIBLE); }
                    if(permessions.view_open_invoice.equals("1")){
                        txt3.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE); }
                    if(permessions.view_visit.equals("1")){
                        txt4.setVisibility(View.VISIBLE);
                        cvVisits.setVisibility(View.VISIBLE); }
                    if(permessions.fingerprint.equals("1")){
                        txt5.setVisibility(View.VISIBLE);
                        cvfingerprint.setVisibility(View.VISIBLE); }
                    if(permessions.view_notification.equals("1")){
                        desc12.setVisibility(View.VISIBLE);
                        noti.setVisibility(View.VISIBLE); }
                    if(permessions.new_finance.equals("1")){
                        txt7.setVisibility(View.VISIBLE);
                        cvnewfinnance.setVisibility(View.VISIBLE); }
                    if(permessions.view_finance.equals("1")){
                        txt8.setVisibility(View.VISIBLE);
                        cvFinance.setVisibility(View.VISIBLE); }
                    if(permessions.view_fingerprint.equals("1")){
                        txt9.setVisibility(View.VISIBLE);
                        hr.setVisibility(View.VISIBLE); }
                    if(permessions.manager.equals("1")){
                        txt10.setVisibility(View.VISIBLE);
                        cvAdmin.setVisibility(View.VISIBLE); }
                    if(permessions.adb.equals("1")){
                        txt11.setVisibility(View.VISIBLE);
                        adb.setVisibility(View.VISIBLE); }
                    if(permessions.new_client.equals("1")){
                        txt12.setVisibility(View.VISIBLE);
                        addNewClient.setVisibility(View.VISIBLE); }
                    if(permessions.stock.equals("1")){
                        txt13.setVisibility(View.VISIBLE);
                        stock.setVisibility(View.VISIBLE); }
                    if(permessions.no_qr.equals("1")){
                        txt14.setVisibility(View.VISIBLE);
                        no_qr.setVisibility(View.VISIBLE); }
                    if(permessions.compare.equals("1")){
                        compare_titel.setVisibility(View.VISIBLE);
                        compare.setVisibility(View.VISIBLE); }
                    if(permessions.expenses.equals("1")){
                        txt15.setVisibility(View.VISIBLE);
                        expenses.setVisibility(View.VISIBLE); }
                    if(permessions.mgmt.equals("1")){
                        txt16.setVisibility(View.VISIBLE);
                        mgmt.setVisibility(View.VISIBLE); }
                    if(permessions.charge.equals("1")){
                        txt17.setVisibility(View.VISIBLE);
                        charge.setVisibility(View.VISIBLE); }
                    if(permessions.bank.equals("1")){
                        txt18.setVisibility(View.VISIBLE);
                        bank.setVisibility(View.VISIBLE); }
                    if(permessions.exchange.equals("1")){
                        txt19.setVisibility(View.VISIBLE);
                        exchange.setVisibility(View.VISIBLE);}
                    if(permessions.view_exchange.equals("1")){
                        txt20.setVisibility(View.VISIBLE);
                        view_exchange.setVisibility(View.VISIBLE);}
                    if(permessions.ezdist.equals("1")){
                        txt21.setVisibility(View.VISIBLE);
                        ezdist.setVisibility(View.VISIBLE);}

                    CategorySort();
                }catch (Exception e ){
                    Toast.makeText(getApplicationContext(), "خطأ في عملية جلب الصلاحيات!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void CategorySort() {

        CardView card1, card2, card3, card4, card5, card6;
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);

        if(permessions.new_visit_qr.equals("0") && permessions.no_qr.equals("0") &&
                permessions.open_invoice.equals("0") && permessions.view_visit.equals("0"))
            card1.setVisibility(View.GONE);
        else if(permessions.new_visit_qr.equals("1") && permessions.no_qr.equals("1") &&
                permessions.open_invoice.equals("1") && permessions.view_visit.equals("1"))
            card1.setVisibility(View.VISIBLE);

        if(permessions.view_open_invoice.equals("0") && permessions.new_client.equals("0") &&
                permessions.fingerprint.equals("0") && permessions.view_fingerprint.equals("0"))
            card2.setVisibility(View.GONE);
        else if(permessions.view_open_invoice.equals("1") && permessions.new_client.equals("1") &&
                permessions.fingerprint.equals("1") && permessions.view_fingerprint.equals("1"))
            card2.setVisibility(View.VISIBLE);

        if(permessions.new_finance.equals("0") && permessions.view_finance.equals("0") &&
                permessions.stock.equals("0") && permessions.expenses.equals("0"))
            card3.setVisibility(View.GONE);
        else if(permessions.new_finance.equals("1") && permessions.view_finance.equals("1") &&
                permessions.stock.equals("1") && permessions.expenses.equals("1"))
            card3.setVisibility(View.VISIBLE);

        if(permessions.manager.equals("0") && permessions.adb.equals("0") &&
                permessions.compare.equals("0") && permessions.mgmt.equals("0"))
            card5.setVisibility(View.GONE);
        else if(permessions.manager.equals("1") && permessions.adb.equals("1") &&
                permessions.compare.equals("1") && permessions.mgmt.equals("1"))
            card5.setVisibility(View.VISIBLE);

        if(permessions.exchange.equals("0") && permessions.view_exchange.equals("0")
                && permessions.bank.equals("0"))
            card6.setVisibility(View.GONE);
        else if(permessions.exchange.equals("1") && permessions.view_exchange.equals("1")
                && permessions.bank.equals("1"))
            card6.setVisibility(View.VISIBLE);


    }


    void getUserInfo(){
        UserViewModel vm = ViewModelProviders.of((FragmentActivity) ThisActivity).get(UserViewModel.class);
        vm.getUserByUsername(ThisActivity,UserInfo.getUser(ThisActivity).getUsername());
        vm.userObject.observe((LifecycleOwner) ThisActivity, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                txvUserName.setText("اسم المستخدم: "+userModel.getName());
                txvUserCommission.setText("ذمم المستخدم: "+userModel.getDebt());
                txvUserMobile.setText("رقم الهاتف: "+userModel.getPhone());
                String userType = "" ;
                if (userModel.getType().equals("1")){
                    userType = "مدير" ;
                }else if (userModel.getType().equals("2")){
                    userType = "مالية" ;
                }else if (userModel.getType().equals("3")){
                    userType = "مندوب" ;
                }else if (userModel.getType().equals("4")){
                    userType = "موظف خدمة عملاء" ;
                }else if (userModel.getType().equals("5")){
                    userType = "" ;
                }
                else if (userModel.getType().equals("6")){
                    userType = "موظف شحن" ;
                }else if (userModel.getType().equals("7")){
                    userType = "موظف" ;
                }
                txvUserType.setText("نوع المستخدم: "+userType);
            }
        });
    }
}