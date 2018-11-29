package com.example.melic.gymplan.gestores;

import android.util.Log;

import com.example.melic.gymplan.classes.CategoriaTreino;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GestorCategoria {
    /*
    * CategoriaTreino
    * int id, String nome
     */

    private ArrayList<CategoriaTreino> categorias;

    public GestorCategoria(){
        this.categorias = new ArrayList<>();
        adicionarDados();
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
        scategorias.add("Nenhuma");
                for (int i = 0;i<this.categorias.size();i++){
            scategorias.add(this.categorias.get(i).getNome());
        }
        return scategorias;
    }
}
