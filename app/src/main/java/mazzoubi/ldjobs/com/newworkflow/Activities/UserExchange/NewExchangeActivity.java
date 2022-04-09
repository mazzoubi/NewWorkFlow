package mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.UserExchange.UserExchangeViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class NewExchangeActivity extends AppCompatActivity {

    Spinner spinnerUser ;

    Activity c;
    ArrayList<OpenInvoiceModel> openInvoices ;
    ArrayList<String> strOpenInvoices ;

    TextView txvInfo;
    EditText edtExchangeAmount , edtNotes ;
    double dept =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exchange);
        init();


    }

    void init(){
        c= NewExchangeActivity.this;
        spinnerUser = findViewById(R.id.spinner);
        txvInfo = findViewById(R.id.txvUserInfo);
        edtExchangeAmount = findViewById(R.id.edtExchaneAmount);
        edtNotes = findViewById(R.id.edtNotes);

        getUsers();


        UserViewModel vm = ViewModelProviders.of((FragmentActivity) c).get(UserViewModel.class);
        vm.getUserByUsername(c,c.getSharedPreferences("Users", Context.MODE_PRIVATE).getString("username",""));
        vm.userObject.observe((LifecycleOwner) c, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                dept = Double.parseDouble(userModel.getDebt());
                String ss ="المستخدم الحالي: "+ getSharedPreferences("Users",MODE_PRIVATE).getString("Name","");
                ss+="\n"+"ذمم المستخدم: "+dept ;
                txvInfo.setText(ss);
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
        vm.getUsers(NewExchangeActivity.this);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewExchangeActivity.this, android.R.layout.simple_list_item_1,strUsers);
                spinnerUser.setAdapter(adapter);
            }
        });
    }


    public void onClickExchange(View view) {
        String toUser = users.get(spinnerUser.getSelectedItemPosition()).getId();
        String amount = edtExchangeAmount.getText().toString();
        if (Double.parseDouble(amount)<=0){
            amount = "" ;
        }
        if (spinnerUser.getSelectedItemPosition()==0){
            toUser = "" ;
        }
        UserExchangeViewModel vm = ViewModelProviders.of(this).get(UserExchangeViewModel.class);
        vm.newExchange(c,getSharedPreferences("Users",MODE_PRIVATE).getString("Id",""),toUser ,amount );
    }
}