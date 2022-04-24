package mazzoubi.ldjobs.com.newworkflow.Activities.Users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.Adapters.UsersAdapter;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class ShowUsersActivity extends AppCompatActivity {

    Button btnAddEmp ;
    ListView listView ;
    ArrayList<UserModel> employees ;
    ArrayList<UserModel> filter ;
    FirebaseFirestore db ;
    Spinner sp;
    ArrayAdapter<UserModel> adapter;
    TextView txvTotal, financetxvTotal;
    Double sumnum, sumnum2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_users);
        CircleImageView logoBack = findViewById(R.id.logo3);
        logoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddNewUserActivity.class));
            }
        });
        ApplyNewTheme();
    }

    private void ApplyNewTheme() {

        try{
            String sys_c = getSharedPreferences("colors", MODE_PRIVATE)
                    .getString("system_color2", "#ff8400");
            findViewById(R.id.tool).setBackgroundColor(Color.parseColor(sys_c));
            findViewById(R.id.btnAddEmp).setBackgroundColor(Color.parseColor(sys_c)); }
        catch (Exception ex){}

    }

    void init(){
        sharedPreferences = getSharedPreferences("emp",MODE_PRIVATE);
        db=FirebaseFirestore.getInstance();
        btnAddEmp=findViewById(R.id.btnAddEmp);
        listView=findViewById(R.id.listView);
        sp = findViewById(R.id.spinner);
        txvTotal = findViewById(R.id.txvTotal);
        financetxvTotal = findViewById(R.id.financetxvTotal);
        sumnum = 0.0;
        sumnum2 = 0.0;

        if(sharedPreferences.getString("type", "-").equals("1")){
            btnAddEmp.setVisibility(View.INVISIBLE);
        }
        getAllEmp();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                filter.clear();

                if(i!=0){
                    for(int j=0; j<employees.size(); j++)
                        if((Integer.parseInt(employees.get(j).getType())) == (i-1))
                            filter.add(employees.get(j));
                    adapter = new UsersAdapter(getApplicationContext(),R.layout.row_users,filter);
                    listView.setAdapter(adapter); }
                else {
                    adapter = new UsersAdapter(getApplicationContext(),R.layout.row_users,employees);
                    listView.setAdapter(adapter); }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder((ShowUsersActivity.this));
                LayoutInflater inflater = (ShowUsersActivity.this).getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.profile_dialog, null));
                final AlertDialog dialog = builder.create();
                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.show();
                dialog.getWindow().setAttributes(lp);
                dialog.setCanceledOnTouchOutside(false);

                TextView name, username, id, mobile, type, type1, btn;
                name = dialog.findViewById(R.id.name);
                username = dialog.findViewById(R.id.username);
                id = dialog.findViewById(R.id.identity);
                mobile = dialog.findViewById(R.id.mobile);
                type = dialog.findViewById(R.id.type);
                type1 = dialog.findViewById(R.id.type1);
                btn = dialog.findViewById(R.id.btnclose);

                try{
                    String sys_c = getSharedPreferences("colors", MODE_PRIVATE)
                            .getString("system_color2", "#ff8400");
                    findViewById(R.id.tool).setBackgroundColor(Color.parseColor(sys_c));
                    btn.setBackgroundColor(Color.parseColor(sys_c)); }
                catch (Exception ex){}

                if(sp.getSelectedItemPosition() == 0){
                    String userType = "";
                    if (employees.get(i).getType().equals("1")){
                        userType = "مدير" ;
                    }else if (employees.get(i).getType().equals("2")){
                        userType = "مالية" ;
                    }else if (employees.get(i).getType().equals("3")){
                        userType = "مندوب" ;
                    }else if (employees.get(i).getType().equals("4")){
                        userType = "موظف خدمة عملاء" ;
                    }else if (employees.get(i).getType().equals("5")){
                        userType = "" ;
                    }
                    else if (employees.get(i).getType().equals("6")){
                        userType = "موظف شحن" ;
                    }else if (employees.get(i).getType().equals("7")){
                        userType = "موظف" ;
                    }

                    name.setText(employees.get(i).getName());
                    username.setText(employees.get(i).getUsername());
                    id.setText(employees.get(i).getId());
                    mobile.setText(employees.get(i).getPhone());
                    type.setText(userType);
                    type1.setText(employees.get(i).getDebt()); }
                else{
                    String userType = "";
                    if (employees.get(i).getType().equals("1")){
                        userType = "مدير" ;
                    }else if (employees.get(i).getType().equals("2")){
                        userType = "مالية" ;
                    }else if (employees.get(i).getType().equals("3")){
                        userType = "مندوب" ;
                    }else if (employees.get(i).getType().equals("4")){
                        userType = "موظف خدمة عملاء" ;
                    }else if (employees.get(i).getType().equals("5")){
                        userType = "" ;
                    }
                    else if (employees.get(i).getType().equals("6")){
                        userType = "موظف شحن" ;
                    }else if (employees.get(i).getType().equals("7")){
                        userType = "موظف" ;
                    }

                    name.setText(filter.get(i).getName());
                    username.setText(filter.get(i).getUsername());
                    id.setText(filter.get(i).getId());
                    mobile.setText(filter.get(i).getPhone());
                    type.setText(userType);
                    type1.setText(filter.get(i).getDebt());
                }


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    void getAllEmp(){
        final ArrayList<UserModel> emps2 ;
        emps2 = new ArrayList<>();
        employees=new ArrayList<>() ;
        filter=new ArrayList<>() ;


        UserViewModel vm = ViewModelProviders.of(ShowUsersActivity.this).get(UserViewModel.class);
        vm.getUsers(ShowUsersActivity.this);
        vm.listUsers.observe(ShowUsersActivity.this, new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                for (UserModel d: userModels){
                    employees.add(d);
                }
                for(int i=0; i<employees.size(); i++){
                    sumnum+=Double.parseDouble(employees.get(i).getDebt());
                    if(employees.get(i).getType().equals("2"))
                        sumnum2+=Double.parseDouble(employees.get(i).getDebt()); }

                txvTotal.setText("مجموع الذمم للموظفين : "+sumnum+" دينار");
                financetxvTotal.setText("مجموع التحصيل لقسم المالية : "+sumnum2+" دينار");

                adapter = new UsersAdapter(getApplicationContext(),R.layout.row_users,employees);
                listView.setAdapter(adapter);
            }
        });

    }

}