package com.example.melic.gymplan.classes;

public class Treino {

    private int id;
    private int dificuldade;
    private int repeticoes;


    public Treino(int id, int dificuldade, int repeticoes){
        this.id = id;
        this.dificuldade=dificuldade;
        this.repeticoes=repeticoes;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDificuldade() { return dificuldade; }
    public void setDificuldade(int Dificuldade) { this.dificuldade = Dificuldade; }

    public int getRepeticoes() { return repeticoes; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }
}
