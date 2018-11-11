package com.example.melic.gymplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    TextView tvRegistar;
    EditText etEmail, etPassword;

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
                //if (checkValues()) {
                    //if (emailExists()) {
                        //if (login()) {
                            asd();
                        //}
                    //}
                //}
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
                //password e null
            }
        } else {
            //email invalido ou null
        }
        return false;
    }

    public boolean emailExists() {
        //check on db if email exists
        return true;
    }
    public void asd() {
        Intent Index = new Intent(LoginActivity.this,IndexActivity.class);
        startActivity(Index);
    }

    public boolean login() {

        return true;
    }
}
