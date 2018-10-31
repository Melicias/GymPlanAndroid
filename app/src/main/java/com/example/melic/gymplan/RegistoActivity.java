package com.example.melic.gymplan;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistoActivity extends AppCompatActivity {

    EditText etData;
    RadioButton rbMasculino,rbFeminino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.etData = (EditText) findViewById(R.id.etData);
        this.rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        this.rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);



        this.rbMasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbFeminino.setChecked(false);
                rbMasculino.setChecked(true);
            }
        });

        this.rbFeminino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbFeminino.setChecked(true);
                rbMasculino.setChecked(false);
            }
        });



        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };
        this.etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        this.etData.setText(sdf.format(myCalendar.getTime()));
    }

}
