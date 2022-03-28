package mazzoubi.ldjobs.com.newworkflow.Activities.Main.Model;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Clients.AddNewClientActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoicePaymentActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Adapters.DashboardAdapter;
import mazzoubi.ldjobs.com.newworkflow.R;

public class ActivityModel {
    public String name ;
    public Intent intent ;
    public int image ;


    public ActivityModel(Intent intent, String name, int image) {
        this.name = name;
        this.intent = intent;
        this.image = image;
    }
    public ActivityModel(){}

    public static ArrayList<ActivityModel> listActivity(Activity c){
        ArrayList<ActivityModel>activities = new ArrayList<>();
        activities.add(new ActivityModel(new Intent(c, AddNewInvoicePaymentActivity.class),"دفع فاتورة عميل",R.drawable.invpayment));
        activities.add(new ActivityModel(new Intent(c, AddNewInvoiceActivity.class),"إنشاء فاتورة عميل",R.drawable.newinv));
        activities.add(new ActivityModel(new Intent(c, AddNewClientActivity.class),"إنشاء عميل جديد",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, AddNewClientActivity.class),"الفواتير المفتوحة",R.drawable.openinvicon));
        activities.add(new ActivityModel(new Intent(c, AddNewClientActivity.class),"سجل النحصيل",R.drawable.aa23));
        activities.add(new ActivityModel(new Intent(c, AddNewClientActivity.class),"كاش فلو",R.drawable.new_client));
        return activities;
    }
}
