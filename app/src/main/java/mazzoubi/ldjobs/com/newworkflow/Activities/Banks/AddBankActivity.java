package mazzoubi.ldjobs.com.newworkflow.Activities.Banks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mazzoubi.ldjobs.com.newworkflow.Data.Banks.BanksModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Banks.BanksViewModel;

public class AddBankActivity extends AppCompatActivity {

    EditText edtBankName ;
    Activity activity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        init();
    }

    void init(){
        activity = AddBankActivity.this;
        edtBankName = findViewById(R.id.edtCBankName);
    }


    public void onClickSave(View view) {
        BanksViewModel vm = ViewModelProviders.of((FragmentActivity) activity).get(BanksViewModel.class);
        BanksModel banksModel = new BanksModel();
        banksModel.setName(edtBankName.getText().toString());
        vm.addBank(activity,banksModel);
    }
}