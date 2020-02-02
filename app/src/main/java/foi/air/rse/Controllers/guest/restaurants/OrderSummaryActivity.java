package foi.air.rse.Controllers.guest.restaurants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.type.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import foi.air.rse.Controllers.guest.BottomMenuGuestActivity;

public class OrderSummaryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public String key;
    private Button btnSubmit;
    private EditText numberOfPersons;
    private Button btnDateTime;
    private TextView txtVDateTime;
    private String persons;
    private String finalReservationDate;
    private String finalReservationTime;
    private DatabaseReference databaseReferenceOrder;


    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal = 0;



    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        key=intent.getStringExtra("key");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary2);
        numberOfPersons = findViewById(R.id.numberOfPersons);
        btnSubmit = findViewById(R.id.btnSubmit);
        databaseReferenceOrder = FirebaseDatabase.getInstance().getReference().child("Order");

        btnDateTime = findViewById(R.id.btnDateTime);
        txtVDateTime = findViewById(R.id.txtVDateTime);

        btnDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderSummaryActivity.this, OrderSummaryActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReservation();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1+1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(OrderSummaryActivity.this, OrderSummaryActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minuteFinal = i1;

        txtVDateTime.setText("Year: " +yearFinal + "\n" + "Month: " + monthFinal + "\n" + "Day: " + dayFinal + "\n" + "Hour: " + hourFinal + "\n" + "Minute: " + minuteFinal);
    }

    public void submitReservation(){
        if(dayFinal==0 || monthFinal == 0 || yearFinal ==0 || hourFinal ==0 || minuteFinal ==0){
            Toast.makeText(getApplication(), "Please choose time and date!", Toast.LENGTH_LONG).show();
        }
        else{
            finalReservationDate = dayFinal + "/" + monthFinal +"/" + yearFinal;
            finalReservationTime = hourFinal + ":" + minuteFinal;
            if(numberOfPersons.getText().toString().equals("")){
                Toast.makeText(getApplication(), "Please choose number of persons!", Toast.LENGTH_LONG).show();
            }
            else{
                persons = numberOfPersons.getText().toString();
                databaseReferenceOrder.child(key).child("dateTime").setValue(finalReservationDate);
                databaseReferenceOrder.child(key).child("time").setValue(finalReservationTime);
                databaseReferenceOrder.child(key).child("persons").setValue(persons);
                Intent intent = new Intent(getApplication(), BottomMenuGuestActivity.class);
                Toast.makeText(getApplication(), "Submitted successfully!", Toast.LENGTH_LONG).show();
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                startActivity(intent);
            }
        }
    }
}

