package com.example.melic.gymplan.classes;

import java.io.Serializable;

public class DificuldadeTreino implements Serializable {

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


    @Override
    public String toString() {
        return  "Dificuldade: " + dificuldade ;
    }
}
