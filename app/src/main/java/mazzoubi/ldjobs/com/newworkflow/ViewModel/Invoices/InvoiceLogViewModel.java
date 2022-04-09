package mazzoubi.ldjobs.com.newworkflow.ViewModel.Invoices;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mazzoubi.ldjobs.com.newworkflow.Data.Invoices.InvoiceLogModel;

public class InvoiceLogViewModel extends ViewModel {
    public MutableLiveData<ArrayList<InvoiceLogModel>> listOfLog = new MutableLiveData<>();


    public void getLogs(Activity c, String invoiceId){

    }
}
