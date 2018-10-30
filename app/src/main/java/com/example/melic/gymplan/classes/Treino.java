package com.example.melic.gymplan.classes;

public class Treino {

    private int id;
    private int dificuldade;

    public Treino(int id, int dificuldade){
        this.id = id;
        this.dificuldade=dificuldade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int Dificuldade) {
        this.dificuldade = Dificuldade;
    }
}
