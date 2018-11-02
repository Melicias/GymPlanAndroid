package com.example.melic.gymplan;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistoActivity extends AppCompatActivity {

    EditText etData,etPrimeiroNome,etUltimoNome,etEmail,etAltura,etPeso,etPassword,etPasswordRepetida;
    RadioButton rbMasculino,rbFeminino;
    Button btRegistar;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.etData = (EditText) findViewById(R.id.etData);
        this.etPrimeiroNome = (EditText) findViewById(R.id.etPrimeiroNome);
        this.etUltimoNome = (EditText) findViewById(R.id.etUltimoNome);
        this.etEmail = (EditText) findViewById(R.id.etEmail);
        this.etAltura = (EditText) findViewById(R.id.etAltura);
        this.etPeso = (EditText) findViewById(R.id.etPeso);
        this.etPassword = (EditText) findViewById(R.id.etPasswordRegisto);
        this.etPasswordRepetida = (EditText) findViewById(R.id.etRepetirPasswordRegisto);
        this.rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        this.rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        this.btRegistar = (Button) findViewById(R.id.btRegistar);

        //calendario com a data de nascimento
        myCalendar = Calendar.getInstance();



        this.btRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValues()){
                    if(!emailExists()){
                        //verificacao de data
                        Toast.makeText(RegistoActivity.this, "Yh Deu", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

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


    private boolean checkValues(){
        if(this.etPrimeiroNome.getText().toString().length() >= 3){
            if(this.etUltimoNome.getText().toString().length() >= 3){
                if(this.etEmail.getText().toString() != null && android.util.Patterns.EMAIL_ADDRESS.matcher(this.etEmail.getText().toString()).matches()){
                    if(this.etData.getText().toString().length() != 0){
                        if(this.etAltura.getText().toString().length() != 0){
                            if(this.etPeso.getText().toString().length() != 0){
                                if(this.etPassword.getText().toString().length() >= 3){
                                    if(getAge() >= 12){
                                        if(this.etPassword.getText().toString().equals(this.etPasswordRepetida.getText().toString())){
                                            try{
                                                Double altura = Double.parseDouble(this.etAltura.getText().toString());
                                                Double peso = Double.parseDouble(this.etPeso.getText().toString());

                                                String[] splitterAltura = altura.toString().split("\\.");
                                                if(splitterAltura.length>=2){
                                                    if(splitterAltura[0].length() >= 1 && splitterAltura[1].length() >= 1 && splitterAltura[0].length() <= 2 && splitterAltura[1].length() <= 2){
                                                        String[] splitterPeso = peso.toString().split("\\.");
                                                        if(splitterPeso.length>=2){
                                                            if(splitterPeso[0].length() >= 1 && splitterPeso[1].length() >= 1 && splitterPeso[0].length() <= 3 && splitterPeso[1].length() <= 3){
                                                                return true;
                                                            }else{
                                                                //numeros errados no peso
                                                            }
                                                        }else{
                                                            //peso so com 1 ou 2
                                                        }
                                                    }else{
                                                        //numeros errados na altura
                                                    }
                                                }else{
                                                    //altura so com 1 ou 2
                                                }
                                            }catch(IllegalArgumentException ex){
                                                //erro na conversao
                                            }
                                        }else{
                                            //password errada
                                        }
                                    }else{
                                        //pessoa demasiado nova DATA
                                    }
                                }else{
                                    //password menor que 3
                                }
                            }else{
                                //peso null
                            }
                        }else{
                            //altura null
                        }
                    }else{
                        //data null
                    }
                }else{
                    //email null ou invalido
                }
            }else{
                //ultimo nome null ou menor que 3
            }
        }else{
            //primerio nome null ou menor que 3
        }
        return false;
    }

    private boolean emailExists(){

        //codigo para verificar a existencia do email
        return false;
    }

    private int getAge(){
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - this.myCalendar.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < this.myCalendar.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }

}
