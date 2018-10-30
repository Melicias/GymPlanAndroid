package com.example.melic.gymplan;

public class DificuldadeTreino {

    private String nome_treino;
    private String tipo_treino;

    public DificuldadeTreino(String NomeTreino, String TipoTreino){
        setNome_treino(NomeTreino);
        setTipo_treino(TipoTreino);

    }

    public String getNome_treino() {
        return nome_treino;
    }

    public void setNome_treino(String nome_treino) {
        this.nome_treino = nome_treino;
    }

    public String getTipo_treino() {
        return tipo_treino;
    }

    public void setTipo_treino(String tipo_treino) {
        this.tipo_treino = tipo_treino;
    }
}
