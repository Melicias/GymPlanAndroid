package com.example.melic.gymplan.gestores;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.melic.gymplan.IndexActivity;
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.SingletonData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorDificuldade {

    /*
     * DificuldadeTreino
     * int id, int dificuldade
     */
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private static final String URL = "https://gymplanyii.000webhostapp.com/GymPlanYii/api/web/dificuldade?access-token=u3JAFUo5Mk2KuYg8yOgKpJ6snvgpKi7L";
    public String accessToken = "";

    private Context context;

    private ArrayList<DificuldadeTreino> dificuldades;

    public GestorDificuldade(Context context, int escolha){
        this.context = context;

        dificuldades = new ArrayList<>();
        if(escolha == ONLINE){
            getDataFromAPI();
        }else{
            //buscar a base de dados
        }
        if(escolha == 0){
            this.dificuldades = new ArrayList<>();
            adicionarDados();
        }
    }

    private void adicionarDados(){
        this.dificuldades.add(new DificuldadeTreino(1,1));
        this.dificuldades.add(new DificuldadeTreino(2,2));
        this.dificuldades.add(new DificuldadeTreino(3,3));
        this.dificuldades.add(new DificuldadeTreino(4,4));
        this.dificuldades.add(new DificuldadeTreino(5,5));
        this.dificuldades.add(new DificuldadeTreino(6,6));
        this.dificuldades.add(new DificuldadeTreino(7,7));
        this.dificuldades.add(new DificuldadeTreino(8,8));
        this.dificuldades.add(new DificuldadeTreino(9,9));
        this.dificuldades.add(new DificuldadeTreino(10,10));
    }

    public ArrayList<DificuldadeTreino> getDificuldades(){
        return this.dificuldades;
    }

    public DificuldadeTreino getDificuldade(int index){
        return this.dificuldades.get(index);
    }

    public ArrayList<String> getDificuldadesString(){
        ArrayList<String> sdificuldades = new ArrayList<>();
        sdificuldades.add("Dificuldade");
        sdificuldades.add("Todas");
        for (int i = 0;i<this.dificuldades.size();i++){
            sdificuldades.add(this.dificuldades.get(i).getDificuldade() + "");
        }
        return sdificuldades;
    }

    private void getDataFromAPI(){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject dif = response.getJSONObject(i);

                                int id = dif.getInt("id_dificuldade");
                                int dificuldade = dif.getInt("dificuldade");
                                DificuldadeTreino d = new DificuldadeTreino(id,dificuldade);
                                dificuldades.add(d);
                                Log.d("dificuldade", d.getId() + "");
                            }
                            SingletonData.getInstance(context, GestorCategoria.ONLINE).setDificuldades(dificuldades);
                        }catch (JSONException e){
                            e.printStackTrace();
                            ((IndexActivity)context).progressBar(false);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("dificuldade", error.toString());
                        ((IndexActivity)context).progressBar(false);
                    }
                }
        );

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("dificuldade", "ERRO: asd dificuldade");
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public DificuldadeTreino getDificuldadeByID(int id){
        for (DificuldadeTreino dif : dificuldades) {
            if(dif.getId() == id)
                return dif;
        }
        return new DificuldadeTreino(0,0);
    }

    public void setDificuldades(ArrayList<DificuldadeTreino>difs){
        this.dificuldades = difs;
    }

}
