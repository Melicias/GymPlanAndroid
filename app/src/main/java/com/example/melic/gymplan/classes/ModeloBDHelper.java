package com.example.melic.gymplan.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ModeloBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gymplan";
    private static final int DB_VERSION = 1;
    private final SQLiteDatabase database;

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
                "( " + ID_TREINO_EXERCICIO + " INTEGER PRIMARY KEY, " +
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
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_DIFICULDADE, null);
        DificuldadeTreino dt;
        cursor.moveToFirst();
        dt = new DificuldadeTreino(cursor.getInt(0),cursor.getInt(1));
        return dt;
    }

    public CategoriaTreino getCategoriaById(int idCategoria){
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_CATEGORIA, null);
        CategoriaTreino ct;
        cursor.moveToFirst();
        ct = new CategoriaTreino(cursor.getInt(0),cursor.getString(1));
        return ct;
    }

}
