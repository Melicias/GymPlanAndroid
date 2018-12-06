package com.example.melic.gymplan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
                        try {
                            String URL = "";
                            JSONObject jsonBody = new JSONObject();

                            jsonBody.put("primeiroNome", etPrimeiroNome);
                            jsonBody.put("ultimoNome", etUltimoNome);
                            jsonBody.put("email", etEmail);
                            jsonBody.put("data", etData);
                            jsonBody.put("peso", etPeso);
                            jsonBody.put("altura", etAltura);
                            jsonBody.put("password", etPassword);
                            jsonBody.put("repeatPassword", etPasswordRepetida);
                            jsonBody.put("sexo", getSexo());



                            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                                public void onResponse(JSONObject response) {

                                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {

                                public void onErrorResponse(VolleyError error) {

                                    onBackPressed();

                                }
                            }) {
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    final Map<String, String> headers = new HashMap<>();
                                    headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                                    return headers;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(this.context);
                            requestQueue.add(context);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

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

    private int getSexo(){
        if(rbFeminino.isChecked()){
            return 1;
        }
        return 0;
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
                                                                Toast.makeText(getApplicationContext(), "peso insuficiente", Toast.LENGTH_LONG).show();

                                                            }
                                                        }else{
                                                            Toast.makeText(getApplicationContext(), "peso só com 1 ou 2", Toast.LENGTH_LONG).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(getApplicationContext(), "numeros errados na altura", Toast.LENGTH_LONG).show();
                                                    }
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "altura só com 1 ou 2", Toast.LENGTH_LONG).show();
                                                }
                                            }catch(IllegalArgumentException ex){
                                                Toast.makeText(getApplicationContext(), "Erro de conversão", Toast.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Password errada", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Idade insuficiente", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Password demasiado fraca, insira uma password com mais de 3 carateres", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Insira o seu peso", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Insira a sua altura", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Insira a sua data de Nascimento", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor insira um e-mail válido", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Apelido demasiado pequeno", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Nome demasiado pequeno", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private boolean emailExists(){
        
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
