package com.example.melic.gymplan.classes;

import com.example.melic.gymplan.MeusTreinosActivity;

import java.util.LinkedList;

public class GestorTreinos {
    private LinkedList<Treino> treinos;

    public GestorTreinos(){
        treinos = new LinkedList<>();
        adicionarDadosIniciais();
    }
    private void adicionarDadosIniciais() {
        this.treinos.add(new Treino(1, 5, new DificuldadeTreino(1,5),new CategoriaTreino(1,"Perda de peso")));
        this.treinos.add(new Treino(2, 10, new DificuldadeTreino(2,5),new CategoriaTreino(2,"Abdominais Only")));
        this.treinos.add(new Treino(3, 2, new DificuldadeTreino(3,5),new CategoriaTreino(3,"Perda de peso")));
        this.treinos.add(new Treino(4, 20, new DificuldadeTreino(4,5),new CategoriaTreino(4,"Ganhar Massa Muscular")));
        this.treinos.add(new Treino(5, 5, new DificuldadeTreino(5,5),new CategoriaTreino(5,"Perda de peso")));

    }
    public LinkedList<Treino> getTreinos() {
        return new LinkedList<>(treinos);
    }
    public Treino getTreinos(int MeusTreinosActivity){
        return treinos.get(MeusTreinosActivity);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Treino treinos: treinos) {
            sb.append(treinos).append("\n");
        }
        return sb.toString();
    }
}