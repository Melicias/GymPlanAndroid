package com.example.melic.gymplan;

public class Exercicio {

    private String foto;
    private String nome_exercicio;
    private String descricao;

    public Exercicio(String Foto, String NomeExercicio, String Descricao){
        foto = Foto;
        nome_exercicio = NomeExercicio;
        descricao = Descricao;

    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome_exercicio() {
        return nome_exercicio;
    }

    public void setNome_exercicio(String NomeExercicio) {
        this.nome_exercicio = NomeExercicio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

