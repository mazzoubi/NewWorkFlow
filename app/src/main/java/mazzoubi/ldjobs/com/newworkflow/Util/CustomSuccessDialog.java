package mazzoubi.ldjobs.com.newworkflow.Util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mazzoubi.ldjobs.com.newworkflow.R;


public class CustomSuccessDialog extends Dialog {

    String message ;
    public CustomSuccessDialog(Activity c , String message){
        super(c);
        this.message=message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.succes_dialog);
        TextView dismiss = findViewById(R.id.textView2);
        TextView textView= findViewById(R.id.textView);
        textView.setText(message+"");

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
