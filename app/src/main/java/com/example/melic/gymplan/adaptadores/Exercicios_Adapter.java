package com.example.melic.gymplan.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.melic.gymplan.R;
import com.example.melic.gymplan.classes.Exercicio;

import java.util.ArrayList;

public class Exercicios_Adapter extends
        RecyclerView.Adapter<Exercicios_Adapter.ViewHolder> {
private ArrayList<Exercicio> exercicios;

public Exercicios_Adapter(ArrayList<Exercicio> exercicios) {
        this.exercicios = exercicios;
        }
@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View ExercicioView = inflater.inflate(R.layout.exercicio_card, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(ExercicioView);
        return viewHolder;
        }


    @Override
public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Exercicio exercicio = this.exercicios.get(i);

        TextView Nome = viewHolder.tvNome;
        TextView Foto = viewHolder.tvFoto;
        TextView Repeticoes = viewHolder.tvRepeticoes;
        TextView Descricao = viewHolder.tvDescricao;
        TextView Duracao = viewHolder.tvDuracao;


        Nome.setText(exercicio.getNome());
        Foto.setText(exercicio.getDescricao());
        Repeticoes.setText(exercicio.getFoto());
        Descricao.setText(exercicio.getDuracao());
        Duracao.setText(exercicio.getDuracao());

        }
@Override
public int getItemCount() {
        return exercicios.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNome,tvFoto,tvRepeticoes,tvDescricao,tvDuracao;

    public ViewHolder(View itemView) {

        super(itemView);
        tvNome = (TextView) itemView.findViewById(R.id.nomeExercicio);
        tvFoto = (TextView) itemView.findViewById(R.id.tvFoto);
        tvRepeticoes = (TextView) itemView.findViewById(R.id.tvRepeticoes);
        tvDescricao = (TextView) itemView.findViewById(R.id.tvDescricao);
        tvDuracao = (TextView) itemView.findViewById(R.id.tvDuracao);


    }
}
}

