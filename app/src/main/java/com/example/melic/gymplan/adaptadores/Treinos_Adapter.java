package com.example.melic.gymplan.adaptadores;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melic.gymplan.R;
import com.example.melic.gymplan.classes.Treino;

import java.util.ArrayList;

public class Treinos_Adapter extends
        RecyclerView.Adapter<Treinos_Adapter.ViewHolder> {
    private ArrayList<Treino> treinos;

    public Treinos_Adapter(ArrayList<Treino> treinos) {
        this.treinos = treinos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View treinoView = inflater.inflate(R.layout.treino_card, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(treinoView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Treino treino = this.treinos.get(i);

        TextView Nome = viewHolder.tvNome;
        TextView Categoria = viewHolder.tvCategoria;
        TextView Dificuldade = viewHolder.tvDificuldade;
        TextView Repeticoes = viewHolder.tvRepeticoes;
        TextView NumeroExercicios = viewHolder.tvNumeroExercicios;



        Nome.setText(treino.getNome());
        Categoria.setText(treino.getCategoria().getNome());
        Dificuldade.setText("" + treino.getDificuldade().getDificuldade());
        Repeticoes.setText("" +treino.getRepeticoes());
        NumeroExercicios.setText("" + treino.getExercicios().size());

    }
    @Override
    public int getItemCount() {
        return treinos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome,tvCategoria,tvDificuldade,tvRepeticoes,tvNumeroExercicios;


        public ViewHolder(View itemView) {

            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.nomeTreino);
            tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);
            tvDificuldade = (TextView) itemView.findViewById(R.id.tvDificuldade);
            tvRepeticoes = (TextView) itemView.findViewById(R.id.tvRepeticoes);
            tvNumeroExercicios = (TextView) itemView.findViewById(R.id.tvNumeroExercicios);


        }
    }
}