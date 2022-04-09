package mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters.AdapterOpenInvoice;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.ManageInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.OpenInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.Adapters.UserExchAdapter;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.Data.UsersExchange.UserExchangeModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices.OpenInvoiceViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.UserExchange.UserExchangeViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class AcceptExchangeActivity extends AppCompatActivity {

    TextView txvDateFrom , txvDateTo ;
    AutoCompleteTextView edtClientName ;
    Spinner spinnerUser ;
    ListView listView;
    Activity activity ;

    ToggleButton toggleButton ;

    ArrayList<UserExchangeModel> userExchanges ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_exchange);

        init();


    }


    void init(){
        activity = AcceptExchangeActivity.this;
        txvDateFrom = findViewById(R.id.txvDateFrom);
        txvDateTo = findViewById(R.id.txvDateTo);
        toggleButton = findViewById(R.id.toggleButton);

        spinnerUser = findViewById(R.id.spinnerUser);
        edtClientName = findViewById(R.id.edtClientName);

        listView = findViewById(R.id.listView);

        txvDateTo.setText(ClassDate.date());
        txvDateFrom.setText(ClassDate.date());

        getUsers();


    }



    ArrayList<UserModel> users ;
    ArrayList<String> strUsers ;
    void getUsers(){
        users = new ArrayList<>();
        strUsers = new ArrayList<>();

        users.add(new UserModel());
        strUsers.add("اختر المستخدم");
        UserViewModel vm = ViewModelProviders.of(this).get(UserViewModel.class);
        vm.getUsers(AcceptExchangeActivity.this);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AcceptExchangeActivity.this, android.R.layout.simple_list_item_1,strUsers);
                spinnerUser.setAdapter(adapter);
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
        userExchanges = new ArrayList<>();
        String userId = "" ;
        String exType ="2" ;
        if (toggleButton.isChecked()){
            exType = "2";
        }else {
            exType="1";
        }
        if (spinnerUser.getSelectedItemPosition()==0){

        }else {
            userId = users.get(spinnerUser.getSelectedItemPosition()).getId();
        }
        UserExchangeViewModel userExchangeViewModel = ViewModelProviders.of(this).get(UserExchangeViewModel.class);
        userExchangeViewModel.getExchanged(activity ,txvDateFrom.getText().toString()
        ,txvDateTo.getText().toString(),userId,exType);

        userExchangeViewModel.listOfExchanges.observe(this, new Observer<ArrayList<UserExchangeModel>>() {
            @Override
            public void onChanged(ArrayList<UserExchangeModel> userExchangeModels) {
                for (UserExchangeModel d: userExchangeModels){
                    userExchanges.add(d);
                }
                ArrayAdapter<UserExchangeModel> adapter = new UserExchAdapter(getApplicationContext()
                ,activity,R.layout.row_payments,userExchanges);
                listView.setAdapter(adapter);
            }
        });
    }

}