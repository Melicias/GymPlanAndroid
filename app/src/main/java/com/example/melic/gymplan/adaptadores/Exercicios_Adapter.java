package com.example.melic.gymplan.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melic.gymplan.PopUpExercicio;
import com.example.melic.gymplan.R;
import com.example.melic.gymplan.classes.DownloadImageTask;
import com.example.melic.gymplan.classes.Exercicio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Exercicios_Adapter extends
        RecyclerView.Adapter<Exercicios_Adapter.ViewHolder> {
    private ArrayList<Exercicio> exercicios;
    private Context context;

    public Exercicios_Adapter(ArrayList<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

        //The Adapter is responsible for making a View for each item in the data set.

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
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
        final int ii = i;

        TextView Nome = viewHolder.tvNome;
        ImageView Image = viewHolder.image;
        TextView DuracaoRepeticoes = viewHolder.tvDuracaoRepeticoes;

        Nome.setText(exercicio.getNome());
        //new DownloadImageTask(Image).execute(exercicio.getFoto());
        Picasso.get().load(exercicio.getFoto()).placeholder(R.drawable.loading).error(R.drawable.image_not_available).into(Image);
        if (exercicio.getDuracao() != 0){
            DuracaoRepeticoes.setText("Duração: " + getDurationBreakdown(exercicio.getDuracao()));
        }else{
            DuracaoRepeticoes.setText("Repetições: " + exercicio.getRepeticoes());
        }
    }

    @Override
    public int getItemCount() {
        return exercicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome, tvDuracaoRepeticoes;
        public ImageView image;
        public Button btDetalhes;

        public ViewHolder(View itemView) {

            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.tvNomeExercicio);
            image = (ImageView) itemView.findViewById(R.id.ivExercicio);
            tvDuracaoRepeticoes = (TextView) itemView.findViewById(R.id.tvDuracaoRepeticoes);
            btDetalhes = (Button)itemView.findViewById(R.id.btDetalhes);

            btDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,PopUpExercicio.class);
                    i.putExtra(PopUpExercicio.PARAM_1, exercicios.get((getAdapterPosition())));
                    context.startActivity(i);
                }
            });

        }
    }

    public static String getDurationBreakdown(int millis)
    {
        int mins = millis / 60;
        int remainder = millis - mins * 60;
        int secs = remainder;

        /*StringBuilder sb = new StringBuilder(64);
        sb.append(mins);
        sb.append(":");
        sb.append(secs);
        return(sb.toString());*/
        return String.format("%02d:%02d",mins,secs);
    }
}

