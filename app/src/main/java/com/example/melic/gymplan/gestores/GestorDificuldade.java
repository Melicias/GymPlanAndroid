package com.example.melic.gymplan.gestores;

import com.example.melic.gymplan.classes.DificuldadeTreino;

import java.util.ArrayList;

public class GestorDificuldade {

    /*
     * DificuldadeTreino
     * int id, int dificuldade
     */

    private ArrayList<DificuldadeTreino> dificuldades;

    public GestorDificuldade(){
        this.dificuldades = new ArrayList<>();
        adicionarDados();
    }

    private void adicionarDados(){
        this.dificuldades.add(new DificuldadeTreino(1,1));
        this.dificuldades.add(new DificuldadeTreino(2,2));
        this.dificuldades.add(new DificuldadeTreino(3,4));
        this.dificuldades.add(new DificuldadeTreino(4,5));
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
        sdificuldades.add("Nenhuma");
        for (int i = 0;i<this.dificuldades.size();i++){
            sdificuldades.add(this.dificuldades.get(i).getDificuldade() + "");
        }
        return sdificuldades;
    }
}
