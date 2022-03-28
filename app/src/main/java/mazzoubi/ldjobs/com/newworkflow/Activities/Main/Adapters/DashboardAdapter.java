package mazzoubi.ldjobs.com.newworkflow.Activities.Main.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Model.ActivityModel;
import mazzoubi.ldjobs.com.newworkflow.R;


public class DashboardAdapter extends ArrayAdapter<ActivityModel> {

    ActivityModel a ;

    public DashboardAdapter(Context context, int view, ArrayList<ActivityModel> arrayList){
        super(context,view,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_dashboard,parent,false);

        TextView txvTitle=myView.findViewById(R.id.aaa1);
        ImageView imageView=myView.findViewById(R.id.imageView);

        a=getItem(position);

        txvTitle.setText(a.name);
        imageView.setImageResource(a.image);

        return myView ;
    }
}