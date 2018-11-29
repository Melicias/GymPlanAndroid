package com.example.melic.gymplan.gestores;

import com.example.melic.gymplan.classes.DificuldadeTreino;

import java.util.ArrayList;

public class GestorDificuldade {

    /*
     * DificuldadeTreino
     * int id, int dificuldade
     */
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private ArrayList<DificuldadeTreino> dificuldades;

    public GestorDificuldade(int escolha){
        if(escolha == ONLINE){
            //buscar api
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

}
