package com.example.melic.gymplan.classes;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Treino {

    private int id;
    private int repeticoes;
    private DificuldadeTreino dificuldade;
    private CategoriaTreino categoria;
    private ArrayList<Exercicio> exercicios;

    public Treino(int id, int repeticoes, DificuldadeTreino dificuldade, CategoriaTreino categoria){
        this.id = id;
        this.repeticoes=repeticoes;
        this.dificuldade=dificuldade;
        this.categoria = categoria;
        this.exercicios= new ArrayList<>();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getRepeticoes() { return repeticoes; }

    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    public DificuldadeTreino getDificuldade() { return dificuldade; }

    public void setDificuldade(DificuldadeTreino Dificuldade) { this.dificuldade = Dificuldade; }

    public CategoriaTreino getCategoria() { return categoria; }

    public void setCategoria(CategoriaTreino categoria) { this.categoria = categoria; }

    public ArrayList getExercicios(){return exercicios;}

    public void setExercicios(ArrayList<Exercicio> exercicios){this.exercicios = exercicios;}

    public void addExercicio(Exercicio exercicio){
        this.exercicios.add(exercicio);
    }

    public void removeExercicio(Exercicio exercicio){
        this.exercicios.remove(exercicio);
    }

    public void removeExercicio(int index){
        this.exercicios.remove(index);
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id ;
    }
    
}
