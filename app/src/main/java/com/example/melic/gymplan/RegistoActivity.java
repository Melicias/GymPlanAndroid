package com.example.melic.gymplan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.melic.gymplan.classes.User;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistoActivity extends AppCompatActivity {

    EditText etData,etPrimeiroNome,etUltimoNome,etEmail,etAltura,etPeso,etPassword,etPasswordRepetida;
    RadioButton rbMasculino,rbFeminino;
    Button btRegistar;
    Calendar myCalendar;

    private static String URL = "user/signup";

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
                    final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarRegisto);
                    final ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.clRegisto);
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("primeiroNome", etPrimeiroNome.getText());
                        jsonBody.put("ultimoNome", etUltimoNome.getText());
                        jsonBody.put("email", etEmail.getText());
                        jsonBody.put("data", etData.getText());
                        jsonBody.put("peso", Double.parseDouble(etPeso.getText().toString()));
                        jsonBody.put("altura", Double.parseDouble(etAltura.getText().toString()));
                        jsonBody.put("sexo", getSexo());
                        jsonBody.put("password", etPassword.getText());

                        pb.setVisibility(View.VISIBLE);
                        cl.setEnabled(false);
                        JsonObjectRequest jsonObject = new JsonObjectRequest(
                                Request.Method.POST,
                                getResources().getString(R.string.url) + URL,
                                jsonBody,
                                new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    User user = new User(response.getInt("id"),response.getString("primeiroNome"),response.getString("ultimoNome"),
                                            in.parse(response.getString("dataNascimento")), response.getDouble("altura"),response.getDouble("peso"),
                                            response.getInt("sexo"),response.getString("auth_key"));
                                    user.saveUserInFile(getApplicationContext());
                                    Intent Index = new Intent(RegistoActivity.this,IndexActivity.class);
                                    startActivity(Index);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(RegistoActivity.this, "Algo não esta bem", Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                /*Intent login = new Intent(RegistoActivity.this,LoginActivity.class);
                                login.putExtra("email",etEmail.getText().toString());
                                login.putExtra("password",etPassword.getText().toString());
                                startActivity(login);*/
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                //algum erro, por exemplo cena
                                pb.setVisibility(View.GONE);
                                cl.setEnabled(true);
                                Toast.makeText(RegistoActivity.this, "Email já usado", Toast.LENGTH_SHORT).show();
                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(jsonObject);
                    } catch (JSONException e) {
                        pb.setVisibility(View.GONE);
                        cl.setEnabled(true);
                        Toast.makeText(RegistoActivity.this, "Ocurreu algum erro, tente mais tarde de novo", Toast.LENGTH_SHORT).show();
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
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                new DatePickerDialog(RegistoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
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
                                if(this.etPassword.getText().toString().length() >= 6){
                                    if(getAge() >= 12){
                                        if(this.etPassword.getText().toString().equals(this.etPasswordRepetida.getText().toString())){
                                            try{
                                                Double altura = Double.parseDouble(this.etAltura.getText().toString());
                                                Double peso = Double.parseDouble(this.etPeso.getText().toString());

                                                String[] splitterAltura = altura.toString().split("\\.");
                                                if(splitterAltura.length==2){
                                                    if(splitterAltura[0].length() >= 1 && splitterAltura[1].length() >= 1 && splitterAltura[0].length() <= 2 && splitterAltura[1].length() <= 2){
                                                        String[] splitterPeso = peso.toString().split("\\.");
                                                        if(splitterPeso.length==2){
                                                            if(splitterPeso[0].length() >= 1 && splitterPeso[1].length() >= 1 && splitterPeso[0].length() <= 3 && splitterPeso[1].length() <= 3){
                                                                if(altura >= 1 && altura <= 2.50){
                                                                    if(peso >= 25 && peso <= 300){
                                                                        return true;
                                                                    }else{
                                                                        Toast.makeText(getApplicationContext(), "O peso deverá ser superior a 25 kg e inferior a 300 kg", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }else{
                                                                    Toast.makeText(getApplicationContext(), "A altura deverá ser superior a 1 metro e inferior a 2.50 metros", Toast.LENGTH_LONG).show();
                                                                }
                                                            }else{
                                                                Toast.makeText(getApplicationContext(), "O formato da altura deve ser como o seguinte exemplo: 70.80 ou 71", Toast.LENGTH_LONG).show();
                                                            }
                                                        }else{
                                                            Toast.makeText(getApplicationContext(), "Peso inválido", Toast.LENGTH_LONG).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(getApplicationContext(), "O formato da altura deve ser como o seguinte exemplo: 1.80", Toast.LENGTH_LONG).show();
                                                    }
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Altura inválida", Toast.LENGTH_LONG).show();
                                                }
                                            }catch(IllegalArgumentException ex){
                                                Toast.makeText(getApplicationContext(), "Erro a converter", Toast.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(), "As duas passwords não coincidem", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Idade insuficiente. Têm de ter no minimo 12 anos", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Password demasiado fraca, insira uma password com mais de 6 carateres", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Último nome demasiado pequeno", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Nome demasiado pequeno", Toast.LENGTH_LONG).show();
        }
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
