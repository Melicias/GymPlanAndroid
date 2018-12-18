package com.example.melic.gymplan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    TextView tvRegistar;
    EditText etEmail, etPassword;

    private static String URL = "user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.tvRegistar = (TextView) findViewById(R.id.tvRegistar);
        this.btLogin = (Button) findViewById(R.id.btLogin);
        this.etEmail = (EditText) findViewById(R.id.etEmail);
        this.etPassword = (EditText) findViewById(R.id.etPassword);

        this.tvRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegistoActivity.class);
                startActivity(i);
            }
        });

        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValues()) {
                    NetError();
                    efetuarLogin(etEmail.getText().toString(),etPassword.getText().toString());
                }
                InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplication().INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        checkForLogin();
    }

    public boolean checkValues() {
        //check if email is nto null and if it is an email
        if (this.etEmail.getText().toString() != null && android.util.Patterns.EMAIL_ADDRESS.matcher(this.etEmail.getText().toString()).matches()) {
            if (this.etPassword.getText().toString() != null) {
                //dados inseridos
                return true;
            } else {
                Toast.makeText(this, "A password está vazia", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "O email está vazio ou é inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void efetuarLogin(String email, String password) {

        final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLogin);
        final ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.clLogin);

        try {
            pb.setVisibility(View.VISIBLE);
            cl.setEnabled(false);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            JsonObjectRequest jsonObject = new JsonObjectRequest(
                    Request.Method.POST,
                    getResources().getString(R.string.url) + URL,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            //save num file do user
                            if(!response.isNull("primeiroNome")){
                                login(response);
                            }else{
                                Toast.makeText(LoginActivity.this, "Email/Password errado", Toast.LENGTH_SHORT).show();
                            }
                            pb.setVisibility(View.GONE);
                            cl.setEnabled(true);
                        }
                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    //algum erro, por exemplo cena
                    pb.setVisibility(View.GONE);
                    cl.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Ocorreu algum erro, visita o nosso website para mais detalhes.", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObject);
        } catch (JSONException e) {
            pb.setVisibility(View.GONE);
            cl.setEnabled(true);
            Toast.makeText(LoginActivity.this, "Ocurreu algum erro, tente mais tarde de novo", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkForLogin(){
        User user = new User();
        user = user.getUserFromFile(this);
        if(user.getAuth_key() != null){
            this.etEmail.setText(user.getEmail());
            //pedido a bd e login
            final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLogin);
            final ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.clLogin);
            pb.setVisibility(View.VISIBLE);
            cl.setEnabled(false);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            String url = getResources().getString(R.string.url) + "userupdate/status?access-token=" + user.getAuth_key();
            JsonObjectRequest jsonObject = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            //save num file do user
                            if(!response.isNull("primeiroNome")){
                                login(response);
                            }else{
                                Toast.makeText(LoginActivity.this, "Login timeout", Toast.LENGTH_SHORT).show();
                            }
                            pb.setVisibility(View.GONE);
                            cl.setEnabled(true);
                        }
                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    //algum erro, por exemplo cena
                    pb.setVisibility(View.GONE);
                    cl.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Login timeout", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObject);
        }
    }

    public void login(JSONObject response){
        try {
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(response.getInt("status") != 10){
                Toast.makeText(LoginActivity.this, "A sua conta esta bloqueada por algum motivo!", Toast.LENGTH_SHORT).show();
            }else{
                User user = new User(response.getInt("id"),response.getString("primeiroNome"),response.getString("ultimoNome"),
                        in.parse(response.getString("dataNascimento")), response.getDouble("altura"),response.getDouble("peso"),
                        response.getInt("sexo"),response.getString("auth_key"),response.getString("email"));
                user.saveUserInFile(getApplicationContext());
                Intent Index = new Intent(LoginActivity.this,IndexActivity.class);
                startActivity(Index);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Algo não esta bem", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void NetError() {
        if (com.androidstudy.checknetworkconnection.NetStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(), "Login feito com sucesso", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getBaseContext(), IndexActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Sem internet, por favor conecte-se a uma rede", Toast.LENGTH_SHORT).show();
        }
    }
}
