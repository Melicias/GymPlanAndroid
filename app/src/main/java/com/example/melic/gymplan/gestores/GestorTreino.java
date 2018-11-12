package com.example.melic.gymplan.gestores;

import com.example.melic.gymplan.classes.CategoriaTreino;
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.Treino;

import java.util.ArrayList;

public class GestorTreino {

    /*
    * Treino
    * int id, int repeticoes, DificuldadeTreino dificuldade, CategoriaTreino categoria
     */

    private ArrayList<Treino> treinos;

    public GestorTreino(){
        this.treinos = new ArrayList<>();
        adicionarDados();
    }

    private void adicionarDados(){
        GestorExercicio ge = new GestorExercicio();
        DificuldadeTreino d = new DificuldadeTreino(1,5);
        CategoriaTreino c = new CategoriaTreino(1,"Perda de peso");
        this.treinos.add(new Treino(1,"treino 1", 5, d,c));
        this.treinos.get(0).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(2,"treino 2", 4, new DificuldadeTreino(1,5),new CategoriaTreino(1,"Perda de peso")));
        this.treinos.get(1).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(3, "treino 3", 6, new DificuldadeTreino(1,6),new CategoriaTreino(1,"Ganhar massa muscular")));
        this.treinos.get(2).setExercicios(ge.getExercicios());
    }

    public ArrayList<Treino> getTreinos(){
        return this.treinos;
    }

    public Treino getTreino(int index){
        return this.treinos.get(index);
    }
}