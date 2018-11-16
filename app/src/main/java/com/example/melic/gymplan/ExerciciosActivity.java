package com.example.melic.gymplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.melic.gymplan.adaptadores.Exercicios_Adapter;
import com.example.melic.gymplan.classes.Treino;

public class ExerciciosActivity extends AppCompatActivity {

    private Treino treino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicios);

        treino = (Treino)getIntent().getSerializableExtra("treino");

        RecyclerView rvExercicios =(RecyclerView) findViewById(R.id.rvExercicios);
        Exercicios_Adapter AdaptadorTreinos = new Exercicios_Adapter(treino.getExercicios());
        rvExercicios.setAdapter(AdaptadorTreinos);
        rvExercicios.setLayoutManager(new LinearLayoutManager(this));

    }
        //interligar exercicios com atividade
}
