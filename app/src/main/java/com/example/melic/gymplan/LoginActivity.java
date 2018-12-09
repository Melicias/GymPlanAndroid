package com.example.melic.gymplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private static String URL = "https://gymplanyii.000webhostapp.com/GymPlanYii/api/web/user/login";

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
                    efetuarLogin(etEmail.getText().toString(),etPassword.getText().toString());
                }
            }
        });
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

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObject = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            //save num file do user
                            if(!response.isNull("primeiroNome")){
                                try {
                                    SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    User user = new User(response.getInt("id"),response.getString("primeiroNome"),response.getString("ultimoNome"),
                                            in.parse(response.getString("dataNascimento")), response.getDouble("altura"),response.getDouble("peso"),
                                            response.getInt("sexo"),response.getString("auth_key"));
                                    user.saveUserInFile(getApplicationContext());
                                    Intent Index = new Intent(LoginActivity.this,IndexActivity.class);
                                    startActivity(Index);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Algo não esta bem", Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            Toast.makeText(LoginActivity.this, "Email/Password errado", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    //algum erro, por exemplo cena
                    Toast.makeText(LoginActivity.this, "Email/Password errado", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObject);
        } catch (JSONException e) {
            Toast.makeText(LoginActivity.this, "Ocurreu algum erro, tente mais tarde de novo", Toast.LENGTH_SHORT).show();
        }
    }
}
