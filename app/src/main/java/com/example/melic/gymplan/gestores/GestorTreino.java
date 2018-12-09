package com.example.melic.gymplan.gestores;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.melic.gymplan.IndexActivity;
import com.example.melic.gymplan.classes.CategoriaTreino;
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.Exercicio;
import com.example.melic.gymplan.classes.SingletonData;
import com.example.melic.gymplan.classes.Treino;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorTreino {

    /*
    * Treino
    * int id, int repeticoes, DificuldadeTreino dificuldade, CategoriaTreino categoria
     */
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private static final String URL = "https://gymplanyii.000webhostapp.com/GymPlanYii/api/web/treino/exercicios?access-token=u3JAFUo5Mk2KuYg8yOgKpJ6snvgpKi7L";
    public String accessToken = "";

    private Context context;

    private ArrayList<Treino> treinos;

    public GestorTreino(Context context, int escolha){
        this.context = context;

        treinos = new ArrayList<>();
        if(escolha == ONLINE){
            getDataFromAPI();
        }else{
            //buscar a base de dados
        }
        if(escolha == 0) {
            this.treinos = new ArrayList<>();
            adicionarDados();
        }
    }

    private void adicionarDados(){
        GestorExercicio ge = new GestorExercicio(0);
        GestorCategoria gc = new GestorCategoria(context,0);
        GestorDificuldade gd = new GestorDificuldade(context,0);
        this.treinos.add(new Treino(1,"Dia a dia","", 10, gd.getDificuldade(0),gc.getCategoria(4)));
        this.treinos.get(0).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(2,"Fim de semana","", 5, gd.getDificuldade(2),gc.getCategoria(4)));
        this.treinos.get(1).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(3, "Perda de peso","", 8, gd.getDificuldade(4),gc.getCategoria(0)));
        this.treinos.get(2).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(4, "Peso bruto","", 15, gd.getDificuldade(6),gc.getCategoria(1)));
        this.treinos.get(3).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(5, "Comeco","", 5, gd.getDificuldade(0),gc.getCategoria(2)));
        this.treinos.get(4).setExercicios(ge.getExercicios());
    }

    public ArrayList<Treino> getTreinos(){
        return this.treinos;
    }

    public Treino getTreino(int index){
        return this.treinos.get(index);
    }

    private void getDataFromAPI(){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL + accessToken,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                Log.d("treino", "i" + i);
                                GestorCategoria categorias = SingletonData.getInstance(context, GestorCategoria.ONLINE).getGestorCategorias(GestorCategoria.ONLINE);
                                GestorDificuldade dificuldades = SingletonData.getInstance(context, GestorDificuldade.ONLINE).getGestorDificuldades(GestorDificuldade.ONLINE);

                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);

                                JSONObject tre = obj.getJSONObject("treino");
                                int id = tre.getInt("id_treino");
                                String nome = tre.getString("nome");
                                String descricao = tre.getString("descricao");
                                int id_categoria = tre.getInt("id_categoria");
                                int id_dificuldade = tre.getInt("id_dificuldade");
                                int repeticoes = tre.getInt("repeticoes");

                                Treino treino = new Treino(id,nome,descricao,repeticoes,dificuldades.getDificuldadeByID(id_dificuldade),categorias.getCategoriaByID(id_categoria));

                                JSONArray exer = obj.getJSONArray("exercicios");
                                ArrayList<Exercicio> exercicios = new ArrayList<>();
                                for(int j=0;j<exer.length();j++){
                                    Log.d("treino", "j" + j);
                                    JSONObject obj2 = exer.getJSONObject(j);
                                    int id_exercicio = obj2.getInt("id_exercicio");
                                    String foto = obj2.getString("foto");
                                    nome = obj2.getString("nome");
                                    descricao = obj2.getString("descricao");
                                    Exercicio e;
                                    if(!obj2.isNull("repeticoes")){
                                        repeticoes = obj2.getInt("repeticoes");
                                        e = new Exercicio(id_exercicio,foto,nome,descricao,repeticoes,true);
                                    }else{
                                        int tempo = obj2.getInt("tempo");
                                        e = new Exercicio(id_exercicio,foto,nome,descricao,tempo,false);
                                    }
                                    exercicios.add(e);
                                }
                                treino.setExercicios(exercicios);
                                treinos.add(treino);
                            }
                            SingletonData.getInstance(context, GestorTreino.ONLINE).setTreinos(treinos);
                            ((IndexActivity)context).progressBar(false);
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.d("treino", "OLA2");
                            ((IndexActivity)context).progressBar(false);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("treino", "OLA3");
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
                Log.d("treino", "ERRO: asd treino");
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public void setTreinos(ArrayList<Treino>tres){
        this.treinos = tres;
    }
}
