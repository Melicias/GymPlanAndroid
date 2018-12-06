package com.example.melic.gymplan.classes;

import com.example.melic.gymplan.gestores.GestorCategoria;
import com.example.melic.gymplan.gestores.GestorDificuldade;
import com.example.melic.gymplan.gestores.GestorTreino;

import java.util.ArrayList;

public class SingletonData {

    private static SingletonData INSTANCE = null;
    private int escolha;

    private GestorCategoria gestorCategorias;
    private GestorDificuldade gestorDificuldades;
    private GestorTreino gestorTreino;


    public static synchronized SingletonData getInstance(int escolha)
    {
        if( INSTANCE == null ){
            INSTANCE = new SingletonData(escolha);
        }
        return INSTANCE;
    }
    private SingletonData(int escolha) {
        this.escolha = escolha;
        this.gestorCategorias = new GestorCategoria(escolha);
        this.gestorDificuldades = new GestorDificuldade(escolha);
        this.gestorTreino = new GestorTreino(escolha);
    }

    public GestorCategoria getGestorCategorias(){
        return this.gestorCategorias;
    }

    public ArrayList<CategoriaTreino> getCategoriaTreinoArray(){
        return this.gestorCategorias.getCategorias();
    }

    public GestorDificuldade getGestorDificuldades(){
        return this.gestorDificuldades;
    }

    public ArrayList<DificuldadeTreino> getDificuldadeTreinoArray(){
        return this.gestorDificuldades.getDificuldades();
    }

    public GestorTreino getGestorTreinos(){
        return this.gestorTreino;
    }

    public ArrayList<Treino> getTreinosArray(){
        return this.gestorTreino.getTreinos();
    }

}
