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
import com.example.melic.gymplan.R;
import com.example.melic.gymplan.classes.CategoriaTreino;
import com.example.melic.gymplan.classes.ModeloBDHelper;
import com.example.melic.gymplan.classes.SingletonData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorCategoria {
    /*
    * CategoriaTreino
    * int id, String nome
     */
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private static final String URL = "categoria?access-token=";

    private Context context;
    private ModeloBDHelper modeloDB;
    private String accessToken;
    private ArrayList<CategoriaTreino> categorias;

    public GestorCategoria( Context context, int escolha, String accessToken){
        this.context = context;
        this.accessToken = accessToken;
        choice(escolha);
    }

    public GestorCategoria(Context context, int escolha, ModeloBDHelper modeloDB){
        this.context = context;
        this.modeloDB = modeloDB;
        choice(escolha);
    }

    public GestorCategoria(Context context, int escolha){
        this.context = context;
        choice(escolha);
    }


    public void choice(int escolha){
        categorias = new ArrayList<>();
        if(escolha == ONLINE){
            //getDataFromAPI();
        }else{
            getDadosFromDB();
        }
        if(escolha == 0) {
            adicionarDados();
        }
    }

    private void getDadosFromDB(){
        this.categorias = modeloDB.getAllCategorias();
    }

    private void adicionarDados(){
        this.categorias.add(new CategoriaTreino(1,"Perder Peso"));
        this.categorias.add(new CategoriaTreino(2,"Ganhar massa"));
        this.categorias.add(new CategoriaTreino(3,"Queimar gordura"));
        this.categorias.add(new CategoriaTreino(4,"Cardio"));
        this.categorias.add(new CategoriaTreino(5,"Manter"));
    }

    public ArrayList<CategoriaTreino> getCategorias(){
        return this.categorias;
    }

    public CategoriaTreino getCategoria(int index){
        return this.categorias.get(index);
    }

    public ArrayList<String> getCategoriasString(){
        ArrayList<String> scategorias = new ArrayList<>();
        scategorias.add("Categoria");
        scategorias.add("Todas");
                for (int i = 0;i<this.categorias.size();i++){
            scategorias.add(this.categorias.get(i).getNome());
        }
        return scategorias;
    }

    /*private void getDataFromAPI(){
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        // Initialize a new JsonArrayRequest instance
        String url = context.getResources().getString(R.string.url) + URL + accessToken;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
                url,
                null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Process the JSON
                    Log.d("categoria", "OLA2");
                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject cat = response.getJSONObject(i);

                            int id = cat.getInt("id_categoria");
                            String nome = cat.getString("nome");
                            CategoriaTreino c = new CategoriaTreino(id,nome);
                            categorias.add(c);
                            Log.d("categoria", c.getId() + "");
                        }
                        SingletonData.getInstance(context, GestorCategoria.ONLINE).setCategorias(categorias);
                    }catch (JSONException e){
                        e.printStackTrace();
                        Log.d("categoria", "OLA3");
                        ((IndexActivity)context).progressBar(false);
                    }
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.d("categoria", "ERRO: " + error.toString());
                    ((IndexActivity)context).progressBar(false);
                }
            }
        );

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 25000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 25000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("categoria", "ERRO: asd");
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }*/

    public CategoriaTreino getCategoriaByID(int id){
        for (CategoriaTreino cat : categorias) {
            if(cat.getId() == id)
                return cat;
        }
        return new CategoriaTreino(0,"ABSSS");
    }

    public void setCategorias(ArrayList<CategoriaTreino>cats){
        this.categorias = cats;
    }

    public boolean setCategoriaIfNotExists(CategoriaTreino cat){
        for (CategoriaTreino categoria : categorias) {
            if(categoria.getId() == cat.getId())
                return false;
        }
        categorias.add(cat);
        return true;
    }

    public void removeCategoria(CategoriaTreino cat){
        this.categorias.remove(cat);
    }
}
