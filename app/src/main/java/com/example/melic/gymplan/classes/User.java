package com.example.melic.gymplan.classes;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.melic.gymplan.LoginActivity;
import com.example.melic.gymplan.R;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id,sexo;
    private String primeiroNome,ultimoNome,email,auth_key;
    private double altura,peso;
    private Date dataNascimento;

    private static String FILE_NAME = "user";

    public User(int id, String primeiroNome, String ultimoNome, Date dataNascimento, double altura, double peso, int sexo, String auth_key){
        this.id = id;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.dataNascimento = dataNascimento;
        this.altura = altura;
        this.peso = peso;
        this.sexo = sexo;
        this.auth_key = auth_key;
    }

    public User(){

    }

    public String getAuth_key(){
        return this.auth_key;
    }

    public User getUserFromFile(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            User user = (User) is.readObject();
            is.close();
            fis.close();
            return user;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new User();
    }

    public void saveUserInFile(Context context){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserFile(Context context){
        context.deleteFile(FILE_NAME);
    }

    public String getPrimeiroNome(){
        return  this.primeiroNome;
    }

    public String getUltimoNome(){
        return  this.ultimoNome;
    }

    public Double getAltura(){
        return this.altura;
    }

    public Double getPeso(){
        return this.peso;
    }

    public int getId(){
        return this.id;
    }
}
