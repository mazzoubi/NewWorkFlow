package mazzoubi.ldjobs.com.newworkflow.Activities.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Adapters.DashboardAdapter;
import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Model.ActivityModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.Clients.ClientsViewModel;

public class DashboardActivity extends AppCompatActivity {

    GridView gridView ;
    public static ArrayList<ActivityModel> activities ;
    TextView txvUserInfo ;
    public static ArrayList<ClientModel> clientsList ;
    public static ArrayList<String> strClientsList ;
    public static ClientModel clientObj ;
    Activity ThisActivity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ThisActivity = DashboardActivity.this;
        init();
        startActivity(new Intent(getApplicationContext(),Dashboard2Activity.class));
        finish();
    }

    void init(){
        gridView = findViewById(R.id.gridView);
        txvUserInfo = findViewById(R.id.txvUserInfo);
        activities = new ArrayList<>();
        activities = ActivityModel.listActivity(DashboardActivity.this);
        ArrayAdapter<ActivityModel> adapter = new DashboardAdapter(getApplicationContext(),
                R.layout.row_dashboard,activities);
        gridView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if ( activities.get(position).name.equals("دفع فاتورة عميل") ){
                    ChooseClientDialog a = new ChooseClientDialog(activities.get(position).name,
                            activities.get(position).intent);
                    a.show();
                }else if (activities.get(position).name.equals("إنشاء فاتورة عميل")){
                    ChooseClientDialog a = new ChooseClientDialog(activities.get(position).name,
                            activities.get(position).intent);
                    a.show();
                }else {
                    startActivity(activities.get(position).intent);
                }

            }
        });

        getUserInfo();
        getAllClient();
    }

    void getUserInfo(){
        txvUserInfo.setText(
             "الاسم: "+   getSharedPreferences("User",MODE_PRIVATE).getString("Name","")+"\n"+
             "الذمم: "+   getSharedPreferences("User",MODE_PRIVATE).getString("Debt","")
        );
    }

    void getAllClient(){
        clientsList = new ArrayList<>();
        strClientsList = new ArrayList<>();
        ClientsViewModel clientsViewModel = ViewModelProviders.of((FragmentActivity) ThisActivity).get(ClientsViewModel.class);
        clientsViewModel.getAllClients(ThisActivity);
        clientsViewModel.listOfClient.observe((LifecycleOwner) ThisActivity, new Observer<ArrayList<ClientModel>>() {
            @Override
            public void onChanged(ArrayList<ClientModel> clientModels) {
                for (ClientModel d: clientModels){
                    clientsList.add(d);
                    strClientsList.add(d.getClientName());
                }
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }


        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (totalHeight + (listView.getHeight() * (listAdapter.getCount() - 1)))/3 +listItem.getMeasuredHeight();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private class ChooseClientDialog extends Dialog{
        String title ;
        Intent intent ;
        public ChooseClientDialog(String title,Intent intent ) {
            super(DashboardActivity.this);
            this.title = title;
            this.intent = intent ;
        }

        TextView txvTitle ;
        Button btnNext ;
        AutoCompleteTextView edtClientName ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_choose_clients);
            init();

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int flag=-1;
                    for (ClientModel d : clientsList){
                        if (d.getClientName().equals(edtClientName.getText().toString())){
                            flag =1;
                            clientObj = d;
                            break;
                        }
                    }
                    if (flag==1){
                        startActivity(intent);
                    }else {
                        Toast.makeText(ThisActivity, "لم يتم العثور على العميل!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void init(){
            txvTitle = findViewById(R.id.textView4);
            btnNext = findViewById(R.id.btnNext);
            edtClientName = findViewById(R.id.edtClientName);
            txvTitle.setText(title);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ThisActivity, android.R.layout.simple_list_item_1,strClientsList);
            edtClientName.setAdapter(adapter);
        }
    }
}