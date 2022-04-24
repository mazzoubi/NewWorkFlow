package mazzoubi.ldjobs.com.newworkflow.Activities.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class AddNewClientActivity extends AppCompatActivity {

    Spinner spinnerUsers ;
    EditText edtCName , edtCEmail , edtCPhone ;
    ArrayList<UserModel> users ;
    ArrayList<String> strUsers ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        init();

    }

    void init(){
        edtCName = findViewById(R.id.edtCName);
        edtCEmail = findViewById(R.id.edtCEmail);
        edtCPhone = findViewById(R.id.edtCPhone);
        spinnerUsers = findViewById(R.id.spinnerUsers);
        getUsers();
    }
    void getUsers(){
        users = new ArrayList<>();
        strUsers = new ArrayList<>();

        users.add(new UserModel());
        strUsers.add("اختر المستخدم");
        UserViewModel vm = ViewModelProviders.of(this).get(UserViewModel.class);
        vm.getUsers(AddNewClientActivity.this);
        vm.listUsers.observe(this, new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                for (UserModel d:userModels){
                    users.add(d);
                    strUsers.add(d.getName());
                }
                if (getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("1")
                        ||getSharedPreferences("Users",MODE_PRIVATE).getString("Type","").equals("2")){

                }else {
                    for (UserModel d:userModels){
                        if (d.getId()
                                .equals(UserInfo.getUser(AddNewClientActivity.this).getId())){
                            users = new ArrayList<>();
                            strUsers = new ArrayList<>();
                            users.add(d);
                            strUsers.add(d.getName());
                        }
                    }
                }
                ArrayAdapter<String>adapter = new ArrayAdapter<>(AddNewClientActivity.this, android.R.layout.simple_list_item_1,strUsers);
                spinnerUsers.setAdapter(adapter);
            }
        });
    }



    public void onClickSave(View view) {
        ClientModel clientModel = new ClientModel();
        clientModel.setClientEmail(edtCEmail.getText().toString());
        clientModel.setClientName(edtCName.getText().toString());
        clientModel.setClientPhone(edtCPhone.getText().toString());
        clientModel.setHolderId(users.get(spinnerUsers.getSelectedItemPosition()).getId());

        ClientsViewModel clientsViewModel = ViewModelProviders.of(this).get(ClientsViewModel.class);
        clientsViewModel.insertNewClients(AddNewClientActivity.this,clientModel);
    }
}