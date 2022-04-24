package mazzoubi.ldjobs.com.newworkflow.Activities.Main.Model;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Activities.Banks.AddBankActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Clients.AddNewClientActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Clients.ManageClientsActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.AddNewInvoicePaymentActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.CollectionReportActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.ManageInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.OpenInvoiceActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Main.Adapters.DashboardAdapter;
import mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.AcceptExchangeActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.UserExchange.NewExchangeActivity;
import mazzoubi.ldjobs.com.newworkflow.Activities.Users.AddNewUserActivity;
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
        activities.add(new ActivityModel(new Intent(c, OpenInvoiceActivity.class),"الفواتير المفتوحة",R.drawable.openinvicon));
        activities.add(new ActivityModel(new Intent(c, CollectionReportActivity.class),"سجل التحصيل",R.drawable.aa23));
        activities.add(new ActivityModel(new Intent(c, ManageClientsActivity.class),"عرض العملاء",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, AddNewUserActivity.class),"اضافة مستخدم",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, AddBankActivity.class),"اضافة بنك",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, ManageInvoiceActivity.class),"ادارة الفواتير",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, NewExchangeActivity.class),"انشاء حركة مالية",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, AcceptExchangeActivity.class),"الحركات المالية المعلقة",R.drawable.new_client));
        activities.add(new ActivityModel(new Intent(c, AddNewClientActivity.class),"كاش فلو",R.drawable.new_client));
        activities.add(new ActivityModel(null,"تسجيل خروج",R.drawable.new_client));
        return activities;
    }
}
