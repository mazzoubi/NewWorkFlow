package mazzoubi.ldjobs.com.newworkflow.Activities.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class AddNewClientActivity extends AppCompatActivity {

    Spinner emp;
    EditText edtCName , edtCEmail , edtCPhone, password, location, code ;
    ArrayList<UserModel> users ;
    ArrayList<String> strUsers ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client2);
        init();

    }

    void init(){
        edtCName = findViewById(R.id.name);
        edtCEmail = findViewById(R.id.email);
        edtCPhone = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        location = findViewById(R.id.locat);
        code = findViewById(R.id.passcode);
        emp = findViewById(R.id.email2);
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
                String ss = UserInfo.getUser(AddNewClientActivity.this).getType();
                if (UserInfo.getUser(AddNewClientActivity.this).getType().equals("1")
                        ||UserInfo.getUser(AddNewClientActivity.this).getType().equals("2")){


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
                emp.setAdapter(adapter);
            }
        });
    }



    public void onClickSave(View view) {

        if(!edtCName.getText().toString().equals("")){
            if(edtCName.getText().toString().length() > 3){
                if(!edtCPhone.getText().toString().equals("")){
                    if(edtCPhone.getText().toString().length() == 12
                            && (edtCPhone.getText().toString().startsWith("96279") ||
                            edtCPhone.getText().toString().startsWith("96278") ||
                            edtCPhone.getText().toString().startsWith("96277"))){
                        if(!edtCEmail.getText().toString().equals("")){
                            if(edtCEmail.getText().toString().length() > 7
                                    && edtCEmail.getText().toString().contains("@")
                                    && edtCEmail.getText().toString().contains(".com")){

                                ClientModel clientModel = new ClientModel();
                                clientModel.setClientEmail(edtCEmail.getText().toString());
                                clientModel.setClientName(edtCName.getText().toString());
                                clientModel.setClientPhone(edtCPhone.getText().toString());
                                clientModel.setHolderId(users.get(emp.getSelectedItemPosition()).getId());
                                clientModel.setPassword(password.getText().toString());
                                clientModel.setLocation(location.getText().toString());
                                clientModel.setPasscode(code.getText().toString());

                                ClientsViewModel clientsViewModel = ViewModelProviders.of(this).get(ClientsViewModel.class);
                                clientsViewModel.insertNewClients(AddNewClientActivity.this,clientModel);

                            }
                            else
                                Toast.makeText(AddNewClientActivity.this, "يرجى ادخال ايميل صحيح", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(AddNewClientActivity.this, "يرجى عدمم ترك حقل الايميل فارغا", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(AddNewClientActivity.this, "يرجى إدخال رقم هاتف صحيح مع تأكيد ادخال رمز الدولة 962", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AddNewClientActivity.this, "يرجى عدم ترك حقل الهاتف فارغا", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(AddNewClientActivity.this, "يرجى إدخال إسم اكبر من 3 حروف", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(AddNewClientActivity.this, "يرجى عدم ترك حقل الاسم فارغا", Toast.LENGTH_LONG).show();
    }
}