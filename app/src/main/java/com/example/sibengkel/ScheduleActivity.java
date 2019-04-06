package com.example.sibengkel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sibengkel.controllers.BookController;
import com.example.sibengkel.utils.Database;
import com.example.sibengkel.utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {
    private LoginActivity.UserLoginTask mAuthTask = null;

    TextView tgl1,email, jam1;
    private int tahun, hari, bulan, jam, menit, detik, namaKend, namaServis ;

    public final static String TAG_TANGGALBOOK = "tanggalbook";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initiateCoreApp();

        email = (TextView)findViewById(R.id.emailTxt);
        email.setText(getIntent().getExtras().get("email").toString());

        tgl1 = findViewById(R.id.inTanggal);
        tgl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                tahun = c.get(Calendar.YEAR);
                bulan = c.get(Calendar.MONTH);
                hari = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DatePicker = new DatePickerDialog(ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        tgl1.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                },tahun,bulan,hari);
                DatePicker.show();


            }
        });

        jam1 = findViewById(R.id.inJam);
        jam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                jam = c.get(Calendar.HOUR_OF_DAY);
                menit = c.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jam1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, jam, menit, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        {
            final Calendar c = Calendar.getInstance();

        }

    }

    private void initiateCoreApp() {
        Database database = new DatabaseHelper(this);
        BookController.setDatabase(database);
    }

    public void bookNow(View view){
        if (mAuthTask != null) {
            return;
        }


        String tgl = tgl1.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(tgl)) {
            tgl1.setError(getString(R.string.error_field_required));
            focusView = tgl1;
            cancel = true;
        }

        if (cancel) {focusView.requestFocus();
        } else {
            ContentValues adminData = BookController.getInstance().getDataByTanggal(tgl);
            if (adminData != null) {
                Toast.makeText(ScheduleActivity.this,
                        "Tanggal tidak kosong", Toast.LENGTH_LONG).show();
            }else {
                ContentValues content = new ContentValues();
                content.put("email", getIntent().getExtras().get("email").toString());
                content.put("tanggal", tgl);
                content.put("date_added", getCurrentTime());


                int id = BookController.getInstance().Book(content);
                if (id > 0) {
                    Toast.makeText(ScheduleActivity.this,
                            "Data sudah tersimpan", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }


}

