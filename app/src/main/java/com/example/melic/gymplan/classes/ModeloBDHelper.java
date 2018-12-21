package com.example.melic.gymplan.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.example.melic.gymplan.gestores.GestorCategoria;
import com.example.melic.gymplan.gestores.GestorTreino;
import com.example.melic.gymplan.menuTreinos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModeloBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gymplan";
    private static final int DB_VERSION = 5;
    private final SQLiteDatabase database;
    private Context context;

    private static final String TABLE_DIFICULDADE = "Dificuldade";
    private static final String TABLE_CATEGORIA = "Categoria";
    private static final String TABLE_TREINO = "Treino";
    private static final String TABLE_EXERCICIO = "Exercicio";
    private static final String TABLE_TREINO_EXERCICIO = "Treino_Exercicio";
    public static final String ID_DIFICULDADE = "id_dificuldade";
    public static final String DIFICULDADE = "dificuldade";
    public static final String ID_CATEGORIA = "id_categoria";
    public static final String NOME_CATEGORIA = "nome";
    public static final String ID_TREINO = "id_treino";
    public static final String NOME_TREINO = "nome";
    public static final String DESCRICAO_TREINO = "descricao";
    public static final String REPETICOES_TREINO = "repeticoes";
    public static final String ID_EXERCICIO = "id_exercicio";
    public static final String FOTO_EXERCICIO = "foto";
    public static final String NOME_EXERCICIO = "nome";
    public static final String DESCRICAO_EXERCICIO = "descricao";
    public static final String REPETICOES_EXERCICIO = "repeticoes";
    public static final String TEMPO_EXERCICIO = "tempo";
    public static final String ID_TREINO_EXERCICIO = "id";
    public static final String ID_TREINO_TREINO_EXERCICIO = "id_treino";
    public static final String ID_EXERCICIO_EXERCICIO = "id_exercicio";


    public ModeloBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDificuldadeTable = "CREATE TABLE " + TABLE_DIFICULDADE +
                "( " + ID_DIFICULDADE + " INTEGER PRIMARY KEY, " +
                DIFICULDADE + " INTEGER NOT NULL " +
                ");";
        String createCategoriaTable = "CREATE TABLE " + TABLE_CATEGORIA +
                "( " + ID_CATEGORIA + " INTEGER PRIMARY KEY, " +
                NOME_CATEGORIA + " TEXT NOT NULL " +
                ");";
        String createTreinoTable = "CREATE TABLE " + TABLE_TREINO +
                "( " + ID_TREINO + " INTEGER PRIMARY KEY, " +
                NOME_TREINO + " TEXT NOT NULL, " +
                DESCRICAO_TREINO + " TEXT NOT NULL, " +
                REPETICOES_TREINO + " INTEGER NOT NULL, " +
                ID_CATEGORIA + " INTEGER NOT NULL, " +
                ID_DIFICULDADE + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + ID_CATEGORIA + ") REFERENCES " + TABLE_CATEGORIA + "("+ID_CATEGORIA+")," +
                "FOREIGN KEY(" + ID_DIFICULDADE + ") REFERENCES " + TABLE_DIFICULDADE + "("+ID_DIFICULDADE+")" +
                ");";
        String createExercicioTable = "CREATE TABLE " + TABLE_EXERCICIO +
                "( " + ID_EXERCICIO + " INTEGER PRIMARY KEY, " +
                FOTO_EXERCICIO + " TEXT NOT NULL, " +
                NOME_EXERCICIO + " TEXT NOT NULL, " +
                DESCRICAO_EXERCICIO + " TEXT NOT NULL, " +
                REPETICOES_EXERCICIO + " INTEGER, " +
                TEMPO_EXERCICIO + " INTEGER " +
                ");";
        String createTreinoExercicioTable = "CREATE TABLE " + TABLE_TREINO_EXERCICIO +
                "( " + ID_TREINO_EXERCICIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID_TREINO_TREINO_EXERCICIO + " INTEGER NOT NULL, " +
                ID_EXERCICIO_EXERCICIO + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + ID_EXERCICIO_EXERCICIO + ") REFERENCES " + TABLE_EXERCICIO + "("+ID_EXERCICIO+")," +
                "FOREIGN KEY(" + ID_TREINO_TREINO_EXERCICIO + ") REFERENCES " + TABLE_TREINO + "("+ID_TREINO+")" +
                ");";


        db.execSQL(createDificuldadeTable);
        db.execSQL(createCategoriaTable);
        db.execSQL(createTreinoTable);
        db.execSQL(createExercicioTable);
        db.execSQL(createTreinoExercicioTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TREINO_EXERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TREINO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIFICULDADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        this.onCreate(db);
    }

    public ArrayList<DificuldadeTreino> getAllDificuldades(){
        ArrayList<DificuldadeTreino> dificuldades = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_DIFICULDADE, null);
        if(cursor.moveToFirst()){
            do{
                dificuldades.add(new DificuldadeTreino(
                        cursor.getInt(0),
                        cursor.getInt(1)));
            }while(cursor.moveToNext());
        }
        return dificuldades;
    }

    public ArrayList<CategoriaTreino> getAllCategorias(){
        ArrayList<CategoriaTreino> categorias = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_CATEGORIA, null);
        if(cursor.moveToFirst()){
            do{
                categorias.add(new CategoriaTreino(
                        cursor.getInt(0),
                        cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        return categorias;
    }

    public ArrayList<Treino> getAllTreinos(){
        ArrayList<Treino> treinos = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_TREINO, null);
        if(cursor.moveToFirst()){
            do{
                treinos.add(new Treino(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        getDificuldadeById(cursor.getInt(5)),
                        getCategoriaById(cursor.getInt(4))));
                treinos.get(treinos.size()-1).setExercicios(getExerciciosByTreinoId(cursor.getInt(0)));
            }while(cursor.moveToNext());
        }
        return treinos;
    }

    public ArrayList<Exercicio> getExerciciosByTreinoId(int idTreino){
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_TREINO_EXERCICIO + " WHERE "+ ID_TREINO + " = "+ idTreino, null);
        if(cursor.moveToFirst()){
            do{
                Cursor cursorExercicio = this.database.rawQuery("SELECT * FROM " + TABLE_EXERCICIO + " WHERE "+ ID_EXERCICIO + " = "+ cursor.getInt(2), null);
                cursorExercicio.moveToFirst();
                if(cursorExercicio.isNull(4)){
                    exercicios.add(new Exercicio(
                            cursorExercicio.getInt(0),
                            cursorExercicio.getString(1),
                            cursorExercicio.getString(2),
                            cursorExercicio.getString(3),
                            cursorExercicio.getInt(5),
                            false));
                }else{
                    exercicios.add(new Exercicio(
                            cursorExercicio.getInt(0),
                            cursorExercicio.getString(1),
                            cursorExercicio.getString(2),
                            cursorExercicio.getString(3),
                            cursorExercicio.getInt(4),
                            true));
                }
            }while(cursor.moveToNext());
        }
        return exercicios;
    }

    public DificuldadeTreino getDificuldadeById(int idDificuldade){
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_DIFICULDADE + " WHERE " + ID_DIFICULDADE + " = " + idDificuldade, null);
        DificuldadeTreino dt;
        cursor.moveToFirst();
        dt = new DificuldadeTreino(cursor.getInt(0),cursor.getInt(1));
        return dt;
    }

    public CategoriaTreino getCategoriaById(int idCategoria){
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_CATEGORIA  + " WHERE " + ID_CATEGORIA + " = " + idCategoria, null);
        CategoriaTreino ct;
        cursor.moveToFirst();
        ct = new CategoriaTreino(cursor.getInt(0),cursor.getString(1));
        return ct;
    }

    private boolean doesCategoriaExist(int idCategoria){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_CATEGORIA + " WHERE " + ID_CATEGORIA + " = " + idCategoria, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    private boolean doesDificuldadeExist(int idDificuldade){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_DIFICULDADE + " WHERE " + ID_DIFICULDADE + " = " + idDificuldade, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    private boolean doesExercicioExist(int idExercicio){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_EXERCICIO + " WHERE " + ID_EXERCICIO + " = " + idExercicio, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    private boolean doesTreinoExist(int idTreino){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_TREINO + " WHERE " + ID_TREINO + " = " + idTreino, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    public boolean guardarTreino(Treino treino){
        //ADD CATEGORIA
        if(!doesTreinoExist(treino.getId())) {
            if (!doesCategoriaExist(treino.getCategoria().getId())) {
                ContentValues values = new ContentValues();
                values.put(ID_CATEGORIA, treino.getCategoria().getId());
                values.put(NOME_CATEGORIA, treino.getCategoria().getNome());
                this.database.insert(TABLE_CATEGORIA, null, values);
                SingletonData.getInstance(context, 1).addCategoriaOffline(treino.getCategoria());
            } else {
                ContentValues values = new ContentValues();
                values.put(NOME_CATEGORIA, treino.getCategoria().getNome());
                this.database.update(TABLE_CATEGORIA, values, ID_CATEGORIA + " = ?", new String[]{"" + treino.getCategoria().getId()});
            }

            //ADD DIFICULDADE
            if (!doesDificuldadeExist(treino.getCategoria().getId())) {
                ContentValues values = new ContentValues();
                values.put(ID_DIFICULDADE, treino.getDificuldade().getId());
                values.put(DIFICULDADE, treino.getDificuldade().getDificuldade());
                this.database.insert(DIFICULDADE, null, values);
                SingletonData.getInstance(context, 1).addDificuldadeOffline(treino.getDificuldade());
            } else {
                ContentValues values = new ContentValues();
                values.put(DIFICULDADE, treino.getDificuldade().getDificuldade());
                this.database.update(TABLE_DIFICULDADE, values, ID_DIFICULDADE + " = ?", new String[]{"" + treino.getDificuldade().getId()});
            }
            //ADD TREINO

            ContentValues values = new ContentValues();
            values.put(ID_TREINO, treino.getId());
            values.put(NOME_TREINO, treino.getNome());
            values.put(DESCRICAO_TREINO, treino.getDescricao());
            values.put(REPETICOES_TREINO, treino.getRepeticoes());
            values.put(ID_CATEGORIA, treino.getCategoria().getId());
            values.put(ID_DIFICULDADE, treino.getDificuldade().getId());
            this.database.insert(TABLE_TREINO, null, values);
            SingletonData.getInstance(context, 1).addTreinoOffline(treino);

            //ADD EXERCICIOS
            //ADD TREINO_EXERCICIO
            for (int i = 0; i < treino.getExercicios().size(); i++) {
                Exercicio e = treino.getExercicio(i);
                if (!doesExercicioExist(treino.getExercicio(i).getId())) {
                    //ADD EXERCICIO
                    values = new ContentValues();
                    values.put(ID_EXERCICIO, e.getId());
                    values.put(FOTO_EXERCICIO, e.getFoto());
                    values.put(NOME_EXERCICIO, e.getNome());
                    values.put(DESCRICAO_EXERCICIO, e.getDescricao());
                    if (e.getRepeticoes() == 0) {
                        values.put(TEMPO_EXERCICIO, e.getDuracao());
                    } else {
                        values.put(REPETICOES_EXERCICIO, e.getRepeticoes());
                    }
                    this.database.insert(TABLE_EXERCICIO, null, values);
                } else {
                    Cursor cursorExercicio = this.database.rawQuery("SELECT * FROM " + TABLE_EXERCICIO + " WHERE " + ID_EXERCICIO + " = " + e.getId(), null);
                    cursorExercicio.moveToFirst();
                    Exercicio eOld;
                    if (cursorExercicio.isNull(4)) {
                        eOld = new Exercicio(
                                cursorExercicio.getInt(0),
                                cursorExercicio.getString(1),
                                cursorExercicio.getString(2),
                                cursorExercicio.getString(3),
                                cursorExercicio.getInt(5),
                                false);
                    } else {
                        eOld = new Exercicio(
                                cursorExercicio.getInt(0),
                                cursorExercicio.getString(1),
                                cursorExercicio.getString(2),
                                cursorExercicio.getString(3),
                                cursorExercicio.getInt(4),
                                true);
                    }
                    if (!eOld.compareValues(e)) {
                        values = new ContentValues();
                        values.put(FOTO_EXERCICIO, e.getFoto());
                        values.put(NOME_EXERCICIO, e.getNome());
                        values.put(DESCRICAO_EXERCICIO, e.getDescricao());
                        if (e.getRepeticoes() == 0) {
                            values.put(TEMPO_EXERCICIO, e.getDuracao());
                            values.putNull(REPETICOES_EXERCICIO);
                        } else {
                            values.put(REPETICOES_EXERCICIO, e.getRepeticoes());
                            values.putNull(TEMPO_EXERCICIO);
                        }
                        this.database.update(TABLE_EXERCICIO, values, ID_EXERCICIO + " = ?", new String[]{"" + e.getId()});
                    }
                }
                values = new ContentValues();
                values.put(ID_TREINO_TREINO_EXERCICIO, treino.getId());
                values.put(ID_EXERCICIO_EXERCICIO, treino.getExercicio(i).getId());
                this.database.insert(TABLE_TREINO_EXERCICIO, null, values);
            }
            return true;
        }
        return false;
    }

    public void removerTreino(Treino treino) {
        //DELETE TREINO_EXERCICIO
        this.database.delete(TABLE_TREINO_EXERCICIO, ID_TREINO + " = ?", new String[]{"" + treino.getId()});
        //DELETE TREINO
        this.database.delete(TABLE_TREINO, ID_TREINO + " = ?", new String[]{"" + treino.getId()});
        SingletonData.getInstance(context,0).removeTreino(treino);
        //DELETE CATEGORIA
        if (!doesCategoriaHaveTreinos(treino.getCategoria().getId())) {
            this.database.delete(TABLE_CATEGORIA, ID_CATEGORIA + " = ?", new String[]{"" + treino.getCategoria().getId()});
            SingletonData.getInstance(context,0).removeCategoria(treino.getCategoria());
        }
         //DELETE DIFICULDADE
        if (!doesDificuldadeHaveTreinos(treino.getDificuldade().getId())) {
            this.database.delete(TABLE_DIFICULDADE, ID_DIFICULDADE + " = ?", new String[]{"" + treino.getDificuldade().getId()});
            SingletonData.getInstance(context,0).removeDificuldade(treino.getDificuldade());
        }
        //DELETE EXERCICIO
        for (int i = 0; i < treino.getExercicios().size(); i++) {
            if (!doesExerciciosHaveTreinos(treino.getExercicio(i).getId(),treino.getId()))
                this.database.delete(TABLE_EXERCICIO, ID_EXERCICIO + " = ?", new String[]{"" + treino.getExercicio(i)});
        }
        ((IndexActivity)context).updatesMeusTreinos();
    }

    private boolean doesCategoriaHaveTreinos(int idCategoria){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_TREINO + " WHERE " + ID_CATEGORIA + " = " + idCategoria, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    private boolean doesDificuldadeHaveTreinos(int idDificuldade){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_TREINO + " WHERE " + ID_DIFICULDADE + " = " + idDificuldade, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    private boolean doesExerciciosHaveTreinos(int idExercicio, int idTreino){
        Cursor cursor = this.database.rawQuery("SELECT 1 FROM " + TABLE_TREINO_EXERCICIO + " WHERE " + ID_EXERCICIO + " = " + idExercicio + " AND " + ID_TREINO + " = " + idTreino, null);
        if(cursor.getCount() == 0)
            return false;
        return true;
    }

    public void updateDBFromApi(){
        ArrayList<Treino> treinosOffline = SingletonData.getInstance(context,0).getTreinosArray(GestorTreino.OFFLINE);
        if(treinosOffline.size() != 0){
            JSONArray json = new JSONArray();
            ArrayList<Integer>ids = new ArrayList<>();
            for (int i = 0; i<treinosOffline.size(); i++){
                ids.add(treinosOffline.get(i).getId());
            }
            json = new JSONArray(ids);
            getArrayTreinosFromAPI(json);
        }
    }

    private void getArrayTreinosFromAPI(JSONArray json){
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        IndexActivity act = (IndexActivity)context;
        // Initialize a new JsonArrayRequest instance
        String url = context.getResources().getString(R.string.url) + "treino/exercicioscdbyid?access-token=" + SingletonData.getInstance(context,0).getAccessToken();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                json,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            menuTreinos fragment =(menuTreinos) ((IndexActivity) context).getSupportFragmentManager().findFragmentByTag("menuTreinos");
                            ArrayList<Treino> treinos = new ArrayList<>();
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


                            //DO THE STUFF
                            checkData(treinos);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
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
                if (error.networkResponse.statusCode == 401)
                {
                    Toast.makeText(context, "Experimente fazer logout e tentar entrar que houve algum problemas com a autenticação", Toast.LENGTH_SHORT).show();

                    throw new VolleyError("Client is not authorized, retry is pointless");
                }
            }
        });
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void checkData(ArrayList<Treino> treinosOnline){
        ArrayList<Treino> treinosOffline = SingletonData.getInstance(context,0).getTreinosArray(GestorTreino.OFFLINE);
        for (int i = 0;i < treinosOnline.size();i++){
            for (int j = 0; j < treinosOffline.size(); j++){
                if(treinosOnline.get(i).getId() == treinosOffline.get(j).getId()){
                    removerTreino(treinosOffline.get(j));
                    guardarTreino(treinosOnline.get(i));
                    break;
                }
            }
        }
        ((IndexActivity)context).mudarParaMenuTreino(R.id.nav_meusPlanos);
        ((IndexActivity)context).updatesMeusTreinos();
    }


}
