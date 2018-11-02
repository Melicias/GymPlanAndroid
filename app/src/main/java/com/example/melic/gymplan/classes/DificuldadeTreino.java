package com.example.melic.gymplan.classes;

public class DificuldadeTreino {

    private int id;
    private int dificuldade;

    public DificuldadeTreino(int id, int dificuldade){
        this.id = id;
        this.dificuldade = dificuldade;
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

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
}
