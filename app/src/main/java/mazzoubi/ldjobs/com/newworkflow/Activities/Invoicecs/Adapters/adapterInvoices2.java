package mazzoubi.ldjobs.com.newworkflow.Activities.Invoicecs.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Clients.ClientModel;
import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceModel;
import mazzoubi.ldjobs.com.newworkflow.R;

public class adapterInvoices2 extends ArrayAdapter<InvoiceModel> {

    InvoiceModel optionItem ;

    public adapterInvoices2(Context context, int view, ArrayList<InvoiceModel> arrayList){
        super(context,view,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_invoices,parent,false);

        TextView txvInvoiceNo =(TextView)myView.findViewById(R.id.txvChargeRequest);
        TextView txvDate =(TextView)myView.findViewById(R.id.txvDate);
        TextView txvStat =(TextView)myView.findViewById(R.id.txvStat);
        TextView txvDateLastPayment =(TextView)myView.findViewById(R.id.txvDate1);
        TextView txvInvoiceAmount =(TextView)myView.findViewById(R.id.txvChargeAmount);
        TextView txvLastPayment =(TextView)myView.findViewById(R.id.txvLastPayment);
        TextView txvAmount =(TextView)myView.findViewById(R.id.txvAmount);
        TextView txvStatType =(TextView)myView.findViewById(R.id.txvName4);
        TextView txvEmpName =(TextView)myView.findViewById(R.id.txvEmpName);
        TextView txvPointName =(TextView)myView.findViewById(R.id.txvPointName);

        optionItem=getItem(position);

        txvInvoiceNo.setText("رقم الفاتورة: "+optionItem.getInvoiceNumber());
        txvDate.setText("التاريخ: "+optionItem.getCreatedDate());
        if(optionItem.getInvoiceState().equals("3")){
            txvStat.setText("مدفوعه");
            txvStatType.setText("دفعة");
            txvStat.setBackgroundColor(Color.GREEN);
        }else if (optionItem.getInvoiceState().equals("1")){
            txvStat.setText("غير مدفوعه");
            txvStatType.setText("انشأت");
            txvStat.setBackgroundColor(Color.RED);
        }else if (optionItem.getInvoiceState().equals("2")){
            txvStat.setText("مدفوعه جزئياً");
            txvStatType.setText("دفعة");
            txvStat.setBackgroundColor(Color.YELLOW);
        }
        txvEmpName.setText(optionItem.getCreatedByUserName());
        txvPointName.setText(optionItem.getClientName());
        txvDateLastPayment.setText("تاريخ اخر اجراء : "+optionItem.getModifiedDate());
        txvInvoiceAmount.setText("اجمالي الفاتورة: "+optionItem.getInvoiceAmount());
        txvLastPayment.setText( "احمالي الدفعات: "+optionItem.getInvoicePaid());
        txvAmount.setText("المبلغ المستحق: "+optionItem.getInvoiceUnpaid() );

        return myView ;
    }
}