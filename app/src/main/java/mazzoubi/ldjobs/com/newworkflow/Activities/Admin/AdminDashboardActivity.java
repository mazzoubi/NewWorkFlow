package mazzoubi.ldjobs.com.newworkflow.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mazzoubi.ldjobs.com.newworkflow.Activities.Permissions.UserPermissionActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.AddNewUserActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.ShowUsersActivity;
import mazzoubi.ldjobs.com.newworkflow.R;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void onClickShowUsers(View view) {
        startActivity(new Intent(getApplicationContext(), ShowUsersActivity.class));
    }

    public void onClickAddNewUser(View view) {
        startActivity(new Intent(getApplicationContext(), AddNewUserActivity.class));
    }

    public void onClickUserPermissions(View view) {
        startActivity(new Intent(getApplicationContext(), UserPermissionActivity.class));
    }
}