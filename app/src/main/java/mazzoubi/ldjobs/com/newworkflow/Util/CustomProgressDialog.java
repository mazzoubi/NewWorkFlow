package mazzoubi.ldjobs.com.newworkflow.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import mazzoubi.ldjobs.com.newworkflow.R;


public class CustomProgressDialog extends ProgressDialog {
    Context context ;
    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        this.context =context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        ImageView imageView = findViewById(R.id.imageView);
        try {
            Glide.with(getContext()).load(R.drawable.waiting).into(imageView);
        }catch (Exception e){}

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        // wlp.gravity = Gravity.BOTTOM;
        wlp.height = 300;
        wlp.width = 800;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        // gravity cart method mood
    }
}
