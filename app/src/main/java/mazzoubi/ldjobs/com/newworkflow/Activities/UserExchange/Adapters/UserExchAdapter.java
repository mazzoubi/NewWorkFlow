package mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mazzoubi.ldjobs.com.newworkflow.Data.UsersExchange.UserExchangeModel;
import mazzoubi.ldjobs.com.newworkflow.R;
import mazzoubi.ldjobs.com.newworkflow.ViewModel.UserExchange.UserExchangeViewModel;

public class UserExchAdapter extends ArrayAdapter<UserExchangeModel> {

    ArrayList<UserExchangeModel> paymentsarray;
    Context context;
    Activity activity;
    TextView status;
    Button accept ;
    Button reject;
    public UserExchAdapter(Context context, Activity activity, int view, ArrayList<UserExchangeModel> arrayList){
        super(context,view,arrayList);
        paymentsarray = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_payments,parent,false);

        TextView from =(TextView)myView.findViewById(R.id.txvChargeRequest);
        TextView to =(TextView)myView.findViewById(R.id.txvChargeAmount);
        TextView pushed_date =(TextView)myView.findViewById(R.id.txvDate1);
        TextView pushed_time =(TextView)myView.findViewById(R.id.txvEmp);
        TextView resp_date =(TextView)myView.findViewById(R.id.txvName4);
        TextView type =(TextView)myView.findViewById(R.id.txvName45);
        TextView value =(TextView)myView.findViewById(R.id.txvName46);
        TextView notes =(TextView)myView.findViewById(R.id.txvName47);
        status =(TextView)myView.findViewById(R.id.txvName48);
        accept =(Button)myView.findViewById(R.id.btnAddInvoice4);
        reject =(Button)myView.findViewById(R.id.btnEndVisit);
        LinearLayout lay = (LinearLayout) myView.findViewById(R.id.lay9);

        from.setText(paymentsarray.get(position).getFromUserName());
        to.setText(paymentsarray.get(position).getToUserName());
        pushed_date.setText(paymentsarray.get(position).getDate());
        pushed_time.setText(paymentsarray.get(position).getTime());
        resp_date.setText(paymentsarray.get(position).getAcceptDate());
//        type.setText(paymentsarray.get(position).type);
        value.setText(paymentsarray.get(position).getAmount());
        notes.setText(paymentsarray.get(position).getNotes());
        status.setText(paymentsarray.get(position).getState());


        if(!paymentsarray.get(position).getState().equals("0"))
            lay.setVisibility(View.INVISIBLE);
        else
            lay.setVisibility(View.VISIBLE);

        if(paymentsarray.get(position).getState().equals("0"))
            status.setBackgroundColor(Color.GRAY);
        else if(paymentsarray.get(position).getState().equals("1"))
            status.setBackgroundColor(Color.GREEN);
        else if(paymentsarray.get(position).getState().equals("-1"))
            status.setBackgroundColor(Color.RED);
        final int x = position;
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rejectExchange(paymentsarray.get(position));

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                accept.setEnabled(false);
                acceptExchange(paymentsarray.get(position));

            }
        });

        return myView ;
    }

    private void acceptExchange(UserExchangeModel userExchangeModel ){
        UserExchangeViewModel userExchangeViewModel = ViewModelProviders.of((FragmentActivity) activity).get(UserExchangeViewModel.class);
        userExchangeViewModel.acceptExchange(activity, userExchangeModel);

    }

    private void rejectExchange(UserExchangeModel userExchangeModel){
        UserExchangeViewModel userExchangeViewModel = ViewModelProviders.of((FragmentActivity) activity).get(UserExchangeViewModel.class);
        userExchangeViewModel.rejectExchange(activity, userExchangeModel);

    }

}