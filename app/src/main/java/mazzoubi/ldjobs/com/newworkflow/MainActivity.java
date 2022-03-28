package mazzoubi.ldjobs.com.newworkflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import mazzoubi.ldjobs.com.newworkflow.Activities.Users.LoginActivity;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassDate;

public class MainActivity extends AppCompatActivity {
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView2);

        try {
            Glide.with(getApplicationContext()).load(R.drawable.splash).into(imageView);
        }catch (Exception e){}

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }catch (Exception e){}
            }
        }.start();

    }

}