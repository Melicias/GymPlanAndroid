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
        String url = "http://www.spekeapartments.com/sites/default/files/styles/my_lightbox/public/gallery/gym8.jpg?itok=WhjbWtUA";
        this.exercicios.add(new Exercicio(1,url,"Abs", "descricao123",12,true));
        this.exercicios.add(new Exercicio(2,url,"Abs2","descricao123",12,true));
        this.exercicios.add(new Exercicio(3,url,"Abs3","descricao123",12,true));
        this.exercicios.add(new Exercicio(4,url,"Abs4","descricao123",66,false));
        this.exercicios.add(new Exercicio(5,url,"Abs5","descricao123",12,true));
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
