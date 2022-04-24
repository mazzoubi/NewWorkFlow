package mazzoubi.ldjobs.com.newworkflow.Activities.Users.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Users.UserModel;
import mazzoubi.ldjobs.com.newworkflow.R;

public class UsersAdapter extends ArrayAdapter<UserModel> {

    UserModel optionItem ;

    public UsersAdapter(Context context, int view, ArrayList<UserModel> arrayList){
        super(context,view,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_users,parent,false);

        TextView textView =(TextView)myView.findViewById(R.id.textView5);
        TextView sumnum3 = (TextView)myView.findViewById(R.id.textView15);
        optionItem=getItem(position);

        String type = "";

        if(optionItem.getType().equals("0"))
            type = "مدير نظام";
        else if(optionItem.getType().equals("1"))
            type = "مندوب مبيعات";
        else if(optionItem.getType().equals("2"))
            type = "مدقق مالي";

        textView.setText(optionItem.getName()+"\n"+type);
        sumnum3.setText(optionItem.getDebt()+"\n"+"دينار");

        try{
            String sys_c = getContext().getSharedPreferences("colors", MODE_PRIVATE)
                    .getString("system_color2", "#ff8400");
            myView.findViewById(R.id.textView5).setBackgroundColor(Color.parseColor(sys_c));


        }
        catch (Exception ex){}

        return myView ;
    }

}