package mazzoubi.ldjobs.com.newworkflow.Activities.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mazzoubi.ldjobs.com.newworkflow.Activities.Main.DashboardActivity;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername , edtPassword ;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            if (getSharedPreferences("User",MODE_PRIVATE).getString("Name","").isEmpty()||
                    getSharedPreferences("User",MODE_PRIVATE).getString("Name","")==null){
                init();
            }else {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                this.finish();
            }
        }catch (Exception e){
            init();
        }

    }

    void init(){
        activity = LoginActivity.this;
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);


    }

    public void onClickLogin(View view) {

        UserViewModel userViewModel = ViewModelProviders.of((FragmentActivity) activity).get(UserViewModel.class);
        userViewModel.login(activity,edtUsername.getText().toString() , edtPassword.getText().toString());



    }
}