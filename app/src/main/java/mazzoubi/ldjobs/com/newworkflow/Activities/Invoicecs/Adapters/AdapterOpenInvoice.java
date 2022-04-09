package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.OpenInvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.R;

public class AdapterOpenInvoice extends ArrayAdapter<OpenInvoiceModel> {

    OpenInvoiceModel a ;

    public AdapterOpenInvoice(Context context, int view, ArrayList<OpenInvoiceModel> arrayList){
        super(context,view,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_open_invoices,parent,false);

        TextView txvInvoiceInfo =(TextView)myView.findViewById(R.id.textView12);

        a=getItem(position);

        String ss="طلب شحن بواسطة "+a.getUserName()+" للنقطة "+a.getClientNam()+" لفاتوره رقم "+a.getInvoiceNo()+" \n"+
                "بتاريخ "+a.getDate() +" الساعه "+a.getTime()+"\n"
                +"وحالة الشحن "+a.getChargeState()+"\n"+"الملاحظات "+a.getNote();

        txvInvoiceInfo.setText(ss);
        if (a.getState().equals("1")){
            txvInvoiceInfo.setBackgroundColor(Color.GREEN);
        }

        return myView ;
    }
}