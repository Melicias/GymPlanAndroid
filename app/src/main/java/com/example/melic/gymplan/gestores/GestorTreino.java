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
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private ArrayList<Treino> treinos;

    public GestorTreino(int escolha){
        if(escolha == ONLINE){
            //buscar api
        }else{
            //buscar a base de dados
        }
        if(escolha == 0) {
            this.treinos = new ArrayList<>();
            adicionarDados();
        }
    }

    private void adicionarDados(){
        GestorExercicio ge = new GestorExercicio(0);
        GestorCategoria gc = new GestorCategoria(0);
        GestorDificuldade gd = new GestorDificuldade(0);
        this.treinos.add(new Treino(1,"Dia a dia","", 10, gd.getDificuldade(0),gc.getCategoria(4)));
        this.treinos.get(0).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(2,"Fim de semana","", 5, gd.getDificuldade(2),gc.getCategoria(4)));
        this.treinos.get(1).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(3, "Perda de peso","", 8, gd.getDificuldade(4),gc.getCategoria(0)));
        this.treinos.get(2).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(4, "Peso bruto","", 15, gd.getDificuldade(6),gc.getCategoria(1)));
        this.treinos.get(3).setExercicios(ge.getExercicios());
        this.treinos.add(new Treino(5, "Comeco","", 5, gd.getDificuldade(0),gc.getCategoria(2)));
        this.treinos.get(4).setExercicios(ge.getExercicios());
    }

    public ArrayList<Treino> getTreinos(){
        return this.treinos;
    }

    public Treino getTreino(int index){
        return this.treinos.get(index);
    }
}
