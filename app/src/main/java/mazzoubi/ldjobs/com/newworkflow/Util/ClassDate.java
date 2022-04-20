package mazzoubi.ldjobs.com.newworkflow.Util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClassDate extends ViewModel {

    public static String date(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("M-yyyy",new Locale("EN"));
        int day = new Date().getDate();
        String []date = (day+"-"+dateFormat.format(new Date()).split("-")[0]+"-"+new Date().toString().split(" ")[5]).split("-");
        if (date[0].length()<2){
            date[0]="0"+date[0];
        }
        if(date[1].length()<2){
            date[1]="0"+date[1];
        }
        return date[2]+"-"+date[1]+"-"+date[0];
    }

    public static String time(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss",new Locale("EN"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String currentTimeAtMs(){
        return System.currentTimeMillis()+"";
    }

    public MutableLiveData<String> datePicker = new MutableLiveData<>();
   public void showDatePicker(Activity c){
        datePicker = new MutableLiveData<>();
        final String[] dateTo = new String[1];
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date_ = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateTo[0] =sdf.format(myCalendar.getTime());
                datePicker.setValue(dateTo[0]);

            } };
        new DatePickerDialog(c, date_, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }


}
