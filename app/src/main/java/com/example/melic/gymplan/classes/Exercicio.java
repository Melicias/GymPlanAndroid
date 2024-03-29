package com.example.melic.gymplan.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Exercicio implements Serializable {

    private int id;
    private String foto;
    private String nome;
    private String descricao;
    private int repeticoes;
    private int duracao;

    public Exercicio(int id, String foto, String nome, String descricao, int repeticoes, int duracao){
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.descricao = descricao;
        this.repeticoes = repeticoes;
        this.duracao = duracao;
    }

    public Exercicio(int id, String foto, String nome, String descricao, int repeticoes_duracao, boolean repeticoes){
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.descricao = descricao;
        if(repeticoes == true){
            this.repeticoes = repeticoes_duracao;
            this.duracao = 0;
        }else{
            this.duracao = repeticoes_duracao;
            this.repeticoes = 0;
        }
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

    public int getRepeticoes() { return repeticoes; }

    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    public int getDuracao() { return duracao; }

    public void setDuracao(int duracao) { this.duracao = duracao; }

    @Override
    public String toString() {
        return "Repetições: " + repeticoes ;
    }

    public boolean compareValues(Exercicio e){
        if(this.descricao.equals(e.getDescricao()) && this.nome.equals(e.getNome()) && this.foto.equals(e.getDescricao()) && this.duracao == e.getDuracao() && this.repeticoes == e.getRepeticoes())
            return true;
        return false;
    }
}



