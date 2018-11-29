package com.example.melic.gymplan.adaptadores;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.melic.gymplan.R;
import com.example.melic.gymplan.classes.Treino;
import com.example.melic.gymplan.exerciciosFrag;

import java.util.ArrayList;

public class Treinos_Adapter extends
        RecyclerView.Adapter<Treinos_Adapter.ViewHolder> {
    private ArrayList<Treino> treinos;
    private ArrayList<Treino> treinosNew;
    private Context context;
    public Treinos_Adapter(ArrayList<Treino> treinos) {
        this.treinos = treinos;
        this.treinosNew = treinos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
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


    public void pesquisaTudo(String nome,int idDificuldade, int idCategoria){
        this.treinos = (ArrayList<Treino>)treinosNew.clone();
        for(int i = 0;i<this.treinos.size();i++){
            if(!treinos.get(i).getNome().contains(nome)){
                treinos.remove(i);
                i--;
            }
        }
        if(idDificuldade != -1){
            for(int i = 0;i<this.treinos.size();i++){
                if(treinos.get(i).getDificuldade().getId() != idDificuldade){
                    treinos.remove(i);
                    i--;
                }
            }
        }
        if(idCategoria != -1){
            for(int i = 0;i<this.treinos.size();i++){
                if(treinos.get(i).getCategoria().getId() != idCategoria){
                    treinos.remove(i);
                    i--;
                }
            }
        }
    }

    public void pesquisaNome(String nome){

    }

    public void pesquisaDificuldade(int idCategoria){
        this.treinos = this.treinosNew;

    }

    public void pesquisaCategoria(int idCategoria){
        this.treinos = this.treinosNew;

    }

    @Override
    public int getItemCount() {
        return treinos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome,tvCategoria,tvDificuldade,tvRepeticoes,tvNumeroExercicios;
        public ImageButton ibSave;


        public ViewHolder(View itemView) {

            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.nomeTreino);
            tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);
            tvDificuldade = (TextView) itemView.findViewById(R.id.tvDificuldade);
            tvRepeticoes = (TextView) itemView.findViewById(R.id.tvRepeticoes);
            tvNumeroExercicios = (TextView) itemView.findViewById(R.id.tvNumeroExercicios);
            ibSave = (ImageButton) itemView.findViewById(R.id.ibSave);

            ibSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //para guardar nos meus treinos
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent exercicios = new Intent(context, ExerciciosActivity.class);
                    exercicios.putExtra("treino", treinos.get(getAdapterPosition()));
                    context.startActivity(exercicios);*/
                    Fragment fragExercicios = new exerciciosFrag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("treino", treinos.get(getAdapterPosition()));
                    fragExercicios.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportActionBar().setTitle(treinos.get(getAdapterPosition()).getNome());
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragExercicios).addToBackStack(null).commit();
                }
            });
        }
    }
}
