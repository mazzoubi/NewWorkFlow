package mazzoubi.ldjobs.com.newworkflow.Activities.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class AddNewUserActivity extends AppCompatActivity {
    Activity activity ;
    EditText edtName , edtPhone , edtPassword1 , edtUsername1 ;
    Spinner spinnerUserType ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        init();

    }

    void init(){
        activity=AddNewUserActivity.this;
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword1 = findViewById(R.id.edtPassword1);
        edtUsername1 = findViewById(R.id.edtUsername1);
        spinnerUserType = findViewById(R.id.spinnerUserType);
    }

    public void onClickSave(View view) {

        String name  = edtName.getText().toString();
        String phone  = edtPhone.getText().toString();
        String password  = edtPassword1.getText().toString();
        String username = edtUsername1.getText().toString();
        String userType = "" ;
        if (spinnerUserType.getSelectedItemPosition()!=0){
           userType=spinnerUserType.getSelectedItemPosition()+"";
        }
        UserModel userModel = new UserModel();
        UserViewModel vm =ViewModelProviders.of((FragmentActivity) activity).get(UserViewModel.class);

        userModel.setName(name);
        userModel.setPhone(phone);
        userModel.setPassword(password);
        userModel.setUsername(username);
        userModel.setType(userType);
        vm.InsertUser(activity,userModel);

    }
}