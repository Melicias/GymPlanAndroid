package com.example.melic.gymplan.classes;

public class Exercicio {

    private int id;
    private String foto;
    private String nome;
    private String descricao;
    private String repeticoes;
    private String duracao;

    public Exercicio(int id, String foto, String nome, String descricao, String repeticoes, String duracao){
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.descricao = descricao;
        this.repeticoes = repeticoes;
        this.duracao = duracao;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRepeticoes() { return repeticoes; }

    public void setRepeticoes(String repeticoes) { this.repeticoes = repeticoes; }

    public String getDuracao() { return duracao; }

    public void setDuracao(String duracao) { this.duracao = duracao; }


}



