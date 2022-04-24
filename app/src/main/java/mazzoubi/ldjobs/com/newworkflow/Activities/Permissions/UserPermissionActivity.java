package mazzoubi.ldjobs.com.newworkflow.Activities.Permissions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Users.Adapters.UsersAdapter;
import mazzoubi.ldjobs.com.newworkflow.Data.Permissions.classPermissions;
import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.Util.ClassAPIs;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomErrorDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomProgressDialog;
import mazzoubi.ldjobs.com.newworkflow.Util.CustomSuccessDialog;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Permissions.PermissionViewModel;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Users.UserViewModel;

public class UserPermissionActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<UserModel> employees ;
    ArrayList<UserModel> filter ;
    FirebaseFirestore db ;
    Spinner sp;
    ArrayAdapter<UserModel> adapter;
    SharedPreferences sharedPreferences;
    classPermissions permessions ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_permission);

        init();

        ApplyNewTheme(); }


    private void ApplyNewTheme() {

        try{
            String sys_c = getSharedPreferences("colors", MODE_PRIVATE)
                    .getString("system_color2", "#ff8400");
            findViewById(R.id.tool).setBackgroundColor(Color.parseColor(sys_c));
        }
        catch (Exception ex){}

    }

    void init(){
        sharedPreferences = getSharedPreferences("emp",MODE_PRIVATE);
        db=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.listView);
        sp = findViewById(R.id.spinner);

        getAllEmp();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                filter.clear();

                if(i!=0){
                    for(int j=0; j<employees.size(); j++)
                        if((Integer.parseInt(employees.get(j).getType())) == (i))
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

                final int index = i;

                final AlertDialog.Builder builder = new AlertDialog.Builder((UserPermissionActivity.this));
                LayoutInflater inflater = (UserPermissionActivity.this).getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.profile_dialog2, null));
                final AlertDialog dialog = builder.create();
                ((FrameLayout) dialog.getWindow().getDecorView().findViewById(android.R.id.content))
                        .setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

                    name.setText(employees.get(i).getName());
                    username.setText(employees.get(i).getUsername());
                    id.setText(employees.get(i).getId());
                    mobile.setText(employees.get(i).getPhone());
                    type.setText(userType);
                    type1.setText(employees.get(i).getDebt());
                }


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder((UserPermissionActivity.this));
                        LayoutInflater inflater = (UserPermissionActivity.this).getLayoutInflater();
                        builder.setView(inflater.inflate(R.layout.profile_dialog3, null));
                        final AlertDialog dialog2 = builder.create();
                        ((FrameLayout) dialog2.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog2.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        dialog2.show();
                        dialog2.getWindow().setAttributes(lp);
                        dialog2.setCanceledOnTouchOutside(false);

                        if(sp.getSelectedItemPosition() == 0)
                            getAllPermessions(employees.get(index).getId(), dialog2);
                        else
                            getAllPermessions(filter.get(index).getId(), dialog2);

                        TextView btnupdate = dialog2.findViewById(R.id.btnupdate);
                        btnupdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(sp.getSelectedItemPosition() == 0)
                                    setAllPermessions(employees.get(index).getId(),employees.get(index).getName(), dialog2);
                                else
                                    setAllPermessions(filter.get(index).getId(),employees.get(index).getName(), dialog2);


                            }
                        });
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


        UserViewModel vm = ViewModelProviders.of(UserPermissionActivity.this).get(UserViewModel.class);
        vm.getUsers(UserPermissionActivity.this);
        vm.listUsers.observe(UserPermissionActivity.this, new Observer<ArrayList<UserModel>>() {
            @Override
            public void onChanged(ArrayList<UserModel> userModels) {
                for (UserModel d: userModels){
                    employees.add(d);
                }

                adapter = new UsersAdapter(getApplicationContext(),R.layout.row_users,employees);
                listView.setAdapter(adapter);
            }
        });

    }

    private void getAllPermessions(String id, final AlertDialog dialog2) {
        permessions = new classPermissions();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id" , id);
        }catch (Exception e){}

        PermissionViewModel vm = ViewModelProviders.of(UserPermissionActivity.this).get(PermissionViewModel.class);
        vm.getPermissionsByUserId(UserPermissionActivity.this,id);
        vm.permission.observe(UserPermissionActivity.this, new Observer<classPermissions>() {
            @Override
            public void onChanged(classPermissions classPermissions) {
                permessions = classPermissions;

                CheckBox ch1 = dialog2.findViewById(R.id.ch1);
                CheckBox ch2 = dialog2.findViewById(R.id.ch2);
                CheckBox ch3 = dialog2.findViewById(R.id.ch3);
                CheckBox ch4 = dialog2.findViewById(R.id.ch4);
                CheckBox ch5 = dialog2.findViewById(R.id.ch5);
                CheckBox ch6 = dialog2.findViewById(R.id.ch6);
                CheckBox ch7 = dialog2.findViewById(R.id.ch7);
                CheckBox ch8 = dialog2.findViewById(R.id.ch8);
                CheckBox ch9 = dialog2.findViewById(R.id.ch9);
                CheckBox ch10 = dialog2.findViewById(R.id.ch10);
                CheckBox ch11 = dialog2.findViewById(R.id.ch11);
                CheckBox ch12 = dialog2.findViewById(R.id.ch12);
                CheckBox ch13 = dialog2.findViewById(R.id.ch13);
                CheckBox ch14 = dialog2.findViewById(R.id.ch14);
                CheckBox ch15 = dialog2.findViewById(R.id.ch15);
                CheckBox ch16 = dialog2.findViewById(R.id.ch16);
                CheckBox ch17 = dialog2.findViewById(R.id.ch17);
                CheckBox ch18 = dialog2.findViewById(R.id.ch18);
                CheckBox ch19 = dialog2.findViewById(R.id.ch19);
                CheckBox ch20 = dialog2.findViewById(R.id.ch20);
                CheckBox ch21 = dialog2.findViewById(R.id.ch21);
                CheckBox ch22 = dialog2.findViewById(R.id.ch22);
                CheckBox ch23 = dialog2.findViewById(R.id.ch23);
                CheckBox ch29 = dialog2.findViewById(R.id.ch29);

                if(permessions.new_visit_qr.equals("1"))
                    ch1.setChecked(true);
                if(permessions.open_invoice.equals("1"))
                    ch2.setChecked(true);
                if(permessions.view_open_invoice.equals("1"))
                    ch3.setChecked(true);
                if(permessions.view_visit.equals("1"))
                    ch4.setChecked(true);
                if(permessions.fingerprint.equals("1"))
                    ch5.setChecked(true);
                if(permessions.new_finance.equals("1"))
                    ch6.setChecked(true);
                if(permessions.view_finance.equals("1"))
                    ch7.setChecked(true);
                if(permessions.view_fingerprint.equals("1"))
                    ch8.setChecked(true);
                if(permessions.manager.equals("1"))
                    ch9.setChecked(true);
                if(permessions.adb.equals("1"))
                    ch10.setChecked(true);
                if(permessions.view_notification.equals("1"))
                    ch11.setChecked(true);
                if(permessions.new_client.equals("1"))
                    ch12.setChecked(true);
                if(permessions.stock.equals("1"))
                    ch13.setChecked(true);
                if(permessions.no_qr.equals("1"))
                    ch14.setChecked(true);
                if(permessions.compare.equals("1"))
                    ch15.setChecked(true);
                if(permessions.stock2.equals("1"))
                    ch16.setChecked(true);
                if(permessions.stock11.equals("1"))
                    ch21.setChecked(true);
                if(permessions.mgmt.equals("1"))
                    ch17.setChecked(true);
                if(permessions.charge.equals("1"))
                    ch18.setChecked(true);
                if(permessions.bank.equals("1"))
                    ch19.setChecked(true);
                if(permessions.expenses.equals("1"))
                    ch20.setChecked(true);
                if(permessions.exchange.equals("1"))
                    ch22.setChecked(true);
                if(permessions.view_exchange.equals("1"))
                    ch23.setChecked(true);
                if(permessions.ezdist.equals("1"))
                    ch29.setChecked(true);

            }
        });


    }

    private void setAllPermessions(String id,String name, final AlertDialog dialog2) {

        CheckBox ch1 = dialog2.findViewById(R.id.ch1);
        CheckBox ch2 = dialog2.findViewById(R.id.ch2);
        CheckBox ch3 = dialog2.findViewById(R.id.ch3);
        CheckBox ch4 = dialog2.findViewById(R.id.ch4);
        CheckBox ch5 = dialog2.findViewById(R.id.ch5);
        CheckBox ch6 = dialog2.findViewById(R.id.ch6);
        CheckBox ch7 = dialog2.findViewById(R.id.ch7);
        CheckBox ch8 = dialog2.findViewById(R.id.ch8);
        CheckBox ch9 = dialog2.findViewById(R.id.ch9);
        CheckBox ch10 = dialog2.findViewById(R.id.ch10);
        CheckBox ch11 = dialog2.findViewById(R.id.ch11);
        CheckBox ch12 = dialog2.findViewById(R.id.ch12);
        CheckBox ch13 = dialog2.findViewById(R.id.ch13);
        CheckBox ch14 = dialog2.findViewById(R.id.ch14);
        CheckBox ch15 = dialog2.findViewById(R.id.ch15);
        CheckBox ch16 = dialog2.findViewById(R.id.ch16);
        CheckBox ch17 = dialog2.findViewById(R.id.ch17);
        CheckBox ch18 = dialog2.findViewById(R.id.ch18);
        CheckBox ch19 = dialog2.findViewById(R.id.ch19);
        CheckBox ch20 = dialog2.findViewById(R.id.ch20);
        CheckBox ch21 = dialog2.findViewById(R.id.ch21);
        CheckBox ch22 = dialog2.findViewById(R.id.ch22);
        CheckBox ch23 = dialog2.findViewById(R.id.ch23);
        CheckBox ch29 = dialog2.findViewById(R.id.ch29);

        JSONObject map = new JSONObject();
        try {
            if(ch1.isChecked()) map.put("new_visit_qr", "1");
            else map.put("new_visit_qr", "0");

            if(ch2.isChecked()) map.put("open_invoice", "1");
            else map.put("open_invoice", "0");

            if(ch3.isChecked()) map.put("view_open_invoice", "1");
            else map.put("view_open_invoice", "0");

            if(ch4.isChecked()) map.put("view_visit", "1");
            else map.put("view_visit", "0");

            if(ch5.isChecked()) map.put("fingerprint", "1");
            else map.put("fingerprint", "0");

            if(ch6.isChecked()) map.put("new_finance", "1");
            else map.put("new_finance", "0");

            if(ch7.isChecked()) map.put("view_finance", "1");
            else map.put("view_finance", "0");

            if(ch8.isChecked()) map.put("view_fingerprint", "1");
            else map.put("view_fingerprint", "0");

            if(ch9.isChecked()) map.put("manager", "1");
            else map.put("manager", "0");

            if(ch10.isChecked()) map.put("adb", "1");
            else map.put("adb", "0");

            if(ch11.isChecked()) map.put("view_notification", "1");
            else map.put("view_notification", "0");

            if(ch12.isChecked()) map.put("new_client", "1");
            else map.put("new_client", "0");

            if(ch13.isChecked()) map.put("open_stock", "1");
            else map.put("open_stock", "0");

            if(ch14.isChecked()) map.put("no_qr", "1");
            else map.put("no_qr", "0");

            if(ch15.isChecked()) map.put("compare", "1");
            else map.put("compare", "0");

            if(ch16.isChecked()) map.put("stock2", "1");
            else map.put("stock2", "0");

            if(ch17.isChecked()) map.put("mgmt", "1");
            else map.put("mgmt", "0");

            if(ch18.isChecked()) map.put("charge", "1");
            else map.put("charge", "0");

            if(ch19.isChecked()) map.put("bank", "1");
            else map.put("bank", "0");

            if(ch20.isChecked()) map.put("expenses", "1");
            else map.put("expenses", "0");

            if(ch21.isChecked()) map.put("stock11", "1");
            else map.put("stock11", "0");

            if(ch22.isChecked()) map.put("exchange", "1");
            else map.put("exchange", "0");

            if(ch23.isChecked()) map.put("view_exchange", "1");
            else map.put("view_exchange", "0");

            if(ch29.isChecked()) map.put("ezdist", "1");
            else map.put("ezdist", "0");

            map.put("emp_id", id);
            map.put("emp_name", name);

//            PermissionViewModel vm = ViewModelProviders.of(UserPermissionActivity.this).get(PermissionViewModel.class);
//            vm.updatePermissions(UserPermissionActivity.this,);
            setProgressDialog(UserPermissionActivity.this);
            Volley.newRequestQueue(this).add(new JsonObjectRequest(Request.Method.POST, ClassAPIs.UpdatePermessions
                    , map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dismissProgressDialog();
                    try {
                        if (response.getString("response_state").equals("1")){
                            successDialog(UserPermissionActivity.this,response.getString("response_state"));
                        }else {
                            errorDialog(UserPermissionActivity.this, response.getString("response_state"));
                        }
                    }catch (Exception e){
                        errorDialog(UserPermissionActivity.this,e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressDialog();
                    errorDialog(UserPermissionActivity.this,error.toString());
                }
            }));

        }catch (Exception e){}


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
        progressDialog.dismiss();
    }
}