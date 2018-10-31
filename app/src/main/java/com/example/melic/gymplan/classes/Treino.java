package com.example.melic.gymplan.classes;

import java.util.ArrayList;

public class Treino {

    private int id;
    private int dificuldade;
    private int repeticoes;
    private ArrayList<Exercicio> exercicios;

    public Treino(int id, int dificuldade, int repeticoes){
        this.id = id;
        this.dificuldade=dificuldade;
        this.repeticoes=repeticoes;
        this.exercicios= new ArrayList<>();

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getDificuldade() { return dificuldade; }

    public void setDificuldade(int Dificuldade) { this.dificuldade = Dificuldade; }

    public int getRepeticoes() { return repeticoes; }

    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    public ArrayList getExercicios(){return exercicios;}

    public void setExercicios(ArrayList<Exercicio> exercicios){this.exercicios = exercicios;}

    
}
