package com.example.melic.gymplan.classes;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

import com.example.melic.gymplan.IndexActivity;
import com.example.melic.gymplan.gestores.GestorCategoria;
import com.example.melic.gymplan.gestores.GestorDificuldade;
import com.example.melic.gymplan.gestores.GestorTreino;

import org.json.JSONArray;

import java.util.ArrayList;

public class SingletonData {

    private static SingletonData INSTANCE = null;
    private static ModeloBDHelper modeloDB = null;
    private int escolha;
    private Context context;

    private User user;


    private GestorCategoria gestorCategoriasOnline;
    private GestorDificuldade gestorDificuldadesOnline;
    private GestorTreino gestorTreinoOnline;

    private GestorCategoria gestorCategoriasOffline;
    private GestorDificuldade gestorDificuldadesOffline;
    private GestorTreino gestorTreinoOffline;



    public static synchronized SingletonData getInstance(Context context, int escolha)
    {
        if( INSTANCE == null ){
            INSTANCE = new SingletonData(context, escolha);
        }
        return INSTANCE;
    }

    private SingletonData(Context context, int escolha) {
        this.escolha = escolha;
        this.context = context;

        this.user = new User();
        this.user = this.user.getUserFromFile(context);

        reloadArraysOnline();

        if(modeloDB == null)
            modeloDB = new ModeloBDHelper(context);

        realoadArraysOffline();
    }

    public void realoadArraysOffline(){
        this.gestorCategoriasOffline = new GestorCategoria(context, GestorCategoria.OFFLINE, modeloDB);
        this.gestorDificuldadesOffline = new GestorDificuldade(context, GestorDificuldade.OFFLINE, modeloDB);
        this.gestorTreinoOffline = new GestorTreino(context, GestorTreino.OFFLINE,modeloDB,this.user.getAuth_key());

    }

    public void reloadArraysOnline(){
        ((IndexActivity)context).progressBar(true);
        this.gestorCategoriasOnline = new GestorCategoria(context, GestorCategoria.ONLINE,this.user.getAuth_key());
        this.gestorDificuldadesOnline = new GestorDificuldade(context, GestorDificuldade.ONLINE,this.user.getAuth_key());
        this.gestorTreinoOnline = new GestorTreino(context, GestorTreino.ONLINE,this.user.getAuth_key());
    }

    public GestorCategoria getGestorCategorias(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorCategoriasOnline;
        return this.gestorCategoriasOffline;
    }

    public ArrayList<CategoriaTreino> getCategoriaTreinoArray(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorCategoriasOnline.getCategorias();
        return this.gestorCategoriasOffline.getCategorias();
    }

    public GestorDificuldade getGestorDificuldades(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorDificuldadesOnline;
        return this.gestorDificuldadesOffline;
    }

    public ArrayList<DificuldadeTreino> getDificuldadeTreinoArray(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorDificuldadesOnline.getDificuldades();
        return this.gestorDificuldadesOffline.getDificuldades();
    }

    public GestorTreino getGestorTreinos(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorTreinoOnline;
        return this.gestorTreinoOffline;
    }

    public ArrayList<Treino> getTreinosArray(int escolha){
        if(escolha == GestorTreino.ONLINE)
            return this.gestorTreinoOnline.getTreinos();
        return this.gestorTreinoOffline.getTreinos();
    }

    public void setCategorias(ArrayList<CategoriaTreino>cats){
        this.gestorCategoriasOnline.setCategorias(cats);
    }

    public void setDificuldades(ArrayList<DificuldadeTreino>difs){
        this.gestorDificuldadesOnline.setDificuldades(difs);
        //this.gestorTreinoOnline = new GestorTreino(context, GestorTreino.ONLINE,this.user.getAuth_key());
    }

    public void setTreinos(ArrayList<Treino>tres){
        this.gestorTreinoOnline.setTreinos(tres);
    }

    public void setTreinosOffline(ArrayList<Treino>tres){
        this.gestorTreinoOffline.setTreinos(tres);
    }

    public boolean setCategoriaOnline(CategoriaTreino cat){
        return this.gestorCategoriasOnline.setCategoriaIfNotExists(cat);
    }

    public boolean setDificuldadeOnline(DificuldadeTreino dif){
        return this.gestorDificuldadesOnline.setDificuldadIfNotExists(dif);
    }

    public User getUser() {
        return this.user;
    }

    public String getAccessToken() {
        return this.user.getAuth_key();
    }

    public ModeloBDHelper getModeloDB(){
        return modeloDB;
    }

    public void addCategoriaOffline(CategoriaTreino ct){
        this.gestorCategoriasOffline.setCategoriaIfNotExists(ct);
    }

    public void addDificuldadeOffline(DificuldadeTreino dt){
        this.gestorDificuldadesOffline.setDificuldadIfNotExists(dt);
    }

    public void addTreinoOffline(Treino treino){
        this.gestorTreinoOffline.addTreino(treino);
    }

    public void removeCategoria(CategoriaTreino cat){
        this.gestorCategoriasOffline.removeCategoria(cat);
    }

    public void removeDificuldade(DificuldadeTreino dif){
        this.gestorDificuldadesOffline.removeDificuldade(dif);
    }

    public void removeTreino(Treino treino){
        this.gestorTreinoOffline.removeTreino(treino);
    }

    public void ordernar(){
        this.gestorCategoriasOnline.ordenarCategorias();
        this.gestorDificuldadesOnline.ordenarDificuldades();
        this.gestorCategoriasOffline.ordenarCategorias();
        this.gestorDificuldadesOffline.ordenarDificuldades();
    }
}
