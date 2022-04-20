package mazzoubi.ldjobs.com.newworkflow.Activities.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;

public class Dashboard2Activity extends AppCompatActivity {

    Activity ThisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        ThisActivity = Dashboard2Activity.this;
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
        UserInfo.logout(Dashboard2Activity.this);
    }

    public void onClickOpenInvoice(View view) {
        startActivity(new Intent(getApplicationContext(), OpenInvoiceActivity.class));
    }


    private class ChooseClientDialog extends Dialog {
        String title ;
        Intent intent ;
        public ChooseClientDialog(String title,Intent intent ) {
            super(Dashboard2Activity.this);
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


}