package mazzoubi.ldjobs.com.newworkflow.Activities.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;

public class ManageClientsActivity extends AppCompatActivity {

    AutoCompleteTextView edtClientName ;
    Activity activity;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clients);
        init();

    }

    void init(){
        edtClientName = findViewById(R.id.edtClientName);
        listView=findViewById(R.id.listView);
        getClients();
    }

    ArrayList<ClientModel> clients ;
    ArrayList<String> strClients ;
    void getClients(){
        clients = new ArrayList<>();
        strClients = new ArrayList<>();
        ClientsViewModel vm = ViewModelProviders.of(this).get(ClientsViewModel.class);
        vm.getAllClients(activity);
        vm.listOfClient.observe(this, new Observer<ArrayList<ClientModel>>() {
            @Override
            public void onChanged(ArrayList<ClientModel> clientModels) {
                for (ClientModel d:clientModels){
                    clients.add(d);
                    strClients.add(d.getClientName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strClients);
                edtClientName.setAdapter(adapter);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,strClients);
                listView.setAdapter(adapter2);
            }
        });
    }

    public void onClickSearch(View view) {
        if (!edtClientName.getText().toString().isEmpty()){
            String clientName = edtClientName.getText().toString();
            for (ClientModel d : clients){
                if (d.getClientName().equals(clientName)){
                    showClientDetail(d);
                    break;
                }
            }
        }
    }

    void showClientDetail(ClientModel clientModel){

    }


    CustomProgressDialog progressDialog;
    void successDialog(Activity c , String message){
        CustomSuccessDialog a = new  CustomSuccessDialog(c,message);
        try {
            a.show();
        }catch (Exception e){

        }
    }
    void errorDialog(Activity c , String message) {
        CustomErrorDialog a = new CustomErrorDialog(c, message);
        try {
            a.show();
        }catch (Exception e){

        }
    }
    void setProgressDialog(Activity c){
        progressDialog=new CustomProgressDialog(c);
        try {
            progressDialog.show();
        }catch (Exception e){

        }
    }
    void dismissProgressDialog(){
        try {
            progressDialog.dismiss();
        }catch (Exception e){}

    }

}