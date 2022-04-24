package mazzoubi.ldjobs.com.newworkflow.Activities.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Dashboard3Activity;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserInfo;
import mazzoubi.ldjobs.com.newworkflow.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void onClickLogout(View view) {
        UserInfo.logout(ProfileActivity.this);
        Dashboard3Activity.ThisActivity.finish();
    }
}