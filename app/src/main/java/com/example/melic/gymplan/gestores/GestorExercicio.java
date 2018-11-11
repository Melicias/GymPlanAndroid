package com.example.melic.gymplan.gestores;

import com.example.melic.gymplan.classes.Exercicio;

import java.util.ArrayList;

public class GestorExercicio {

    /*
    * Exercicio
    * int id, String foto, String nome, String descricao, int repeticoes, int duracao
    * int id, String foto, String nome, String descricao, int repeticoes_duracao, boolean repeticoes
     */

    private ArrayList<Exercicio> exercicios;

    public GestorExercicio(){
        this.exercicios = new ArrayList<>();
        adicionarDados();
    }

    private void adicionarDados(){
        this.exercicios.add(new Exercicio(1,"asdasda","Abs","descricao123",12,true));
        this.exercicios.add(new Exercicio(2,"asdasda","Abs2","descricao123",12,true));
        this.exercicios.add(new Exercicio(3,"asdasda","Abs3","descricao123",12,true));
        this.exercicios.add(new Exercicio(4,"asdasda","Abs4","descricao123",12,true));
        this.exercicios.add(new Exercicio(5,"asdasda","Abs5","descricao123",12,true));
    }

    public ArrayList<Exercicio> getExercicios(){
        return this.exercicios;
    }

    public Exercicio getExercicio(int index){
        return this.exercicios.get(index);
    }

    /*public void setExercicio(Exercicio ex){
        this.exercicios.add(ex);
    }*/
}
