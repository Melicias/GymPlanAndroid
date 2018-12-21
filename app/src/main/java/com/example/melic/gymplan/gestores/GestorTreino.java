package com.example.melic.gymplan.gestores;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.Exercicio;
import com.example.melic.gymplan.classes.ModeloBDHelper;
import com.example.melic.gymplan.classes.NetStatus;
import com.example.melic.gymplan.classes.SingletonData;
import com.example.melic.gymplan.classes.Treino;
import com.example.melic.gymplan.menuTreinos;

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

    //private static final String URL = "treino/exercicios?access-token=";
    private static final String URL = "treino/exercicioscd?access-token=";

    private Context context;
    private String accessToken;
    private ModeloBDHelper modeloDB;
    private ArrayList<Treino> treinos;

    public GestorTreino(Context context, int escolha){
        this.context = context;
        choice(escolha);
    }

    public GestorTreino(Context context, int escolha,String accessToken){
        this.context = context;
        this.accessToken = accessToken;
        choice(escolha);
    }

    public GestorTreino(Context context, int escolha, ModeloBDHelper modeloDB){
        this.context = context;
        this.modeloDB = modeloDB;
        choice(escolha);
    }

    public void choice(int escolha){
        treinos = new ArrayList<>();
        if(escolha == ONLINE){
            //getDataFromAPI();
            if(NetStatus.getInstance(context).isOnline()) {
                getAllDataFromAPI();
            }else{
                Toast.makeText(context, "Não existe uma ligação a internet!", Toast.LENGTH_SHORT).show();
            }
        }else{
            getDadosFromDB();
        }
        if(escolha == TESTE) {
            this.treinos = new ArrayList<>();
            adicionarDados();
        }
    }

    private void getDadosFromDB(){
        this.treinos = modeloDB.getAllTreinos();
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

    private void getAllDataFromAPI(){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        IndexActivity act = (IndexActivity)context;
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
                        try{
                            // Loop through the array elements
                            menuTreinos fragment =(menuTreinos) ((IndexActivity) context).getSupportFragmentManager().findFragmentByTag("menuTreinos");
                            for(int i=0;i<response.length();i++){
                                //GestorCategoria categorias = SingletonData.getInstance(context, GestorCategoria.ONLINE).getGestorCategorias(GestorCategoria.ONLINE);
                                //GestorDificuldade dificuldades = SingletonData.getInstance(context, GestorDificuldade.ONLINE).getGestorDificuldades(GestorDificuldade.ONLINE);

                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);

                                //get treino
                                JSONObject tre = obj.getJSONObject("treino");
                                int id = tre.getInt("id_treino");
                                String nome = tre.getString("nome");
                                String descricao = tre.getString("descricao");
                                int repeticoes = tre.getInt("repeticoes");
                                //get categoria
                                JSONObject cat = obj.getJSONObject("categoria");
                                int id_categoria = cat.getInt("id_categoria");
                                String nomeCategoria = cat.getString("nome");
                                //get dificuldade
                                JSONObject dif = obj.getJSONObject("dificuldade");
                                int id_dificuldade = dif.getInt("id_dificuldade");
                                int dificuldade = dif.getInt("dificuldade");

                                CategoriaTreino c = new CategoriaTreino(id_categoria,nomeCategoria);
                                DificuldadeTreino d = new DificuldadeTreino(id_dificuldade,dificuldade);
                                //Treino treino = new Treino(id,nome,descricao,repeticoes,dificuldades.getDificuldadeByID(id_dificuldade),categorias.getCategoriaByID(id_categoria));
                                Treino treino = new Treino(id,nome,descricao,repeticoes,d,c);

                                SingletonData.getInstance(context, GestorCategoria.ONLINE).setCategoriaOnline(c);
                                SingletonData.getInstance(context, GestorCategoria.ONLINE).setDificuldadeOnline(d);

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
                return 15000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 15000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                if (error.networkResponse.statusCode == 401)
                {
                    ((IndexActivity)context).progressBar(false);
                    Toast.makeText(context, "Experimente fazer logout e tentar entrar que houve algum problemas com a autenticação", Toast.LENGTH_SHORT).show();

                    throw new VolleyError("Client is not authorized, retry is pointless");
                }
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    /*private void getDataFromAPI(){
        // Initialize a new RequestQueue instance
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
                return 25000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 25000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d("treino", "ERRO: asd treino");
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }*/



    public void setTreinos(ArrayList<Treino>tres){
        this.treinos = tres;
    }

    public void addTreino(Treino treino){
        this.treinos.add(treino);
    }

    public void removeTreino(Treino treino){
        this.treinos.remove(treino);
    }
}
