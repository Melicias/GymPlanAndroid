package com.example.melic.gymplan;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melic.gymplan.classes.DownloadImageTask;
import com.example.melic.gymplan.classes.Exercicio;

public class PopUpExercicio extends Activity {

    public static final String PARAM_1 = "EXERCICIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_exercicio);

        TextView tvNome,tvDescricao,tvDuracaoRepeticoes;
        ImageView ivFoto;

        tvNome = (TextView)findViewById(R.id.tvNome);
        tvDescricao = (TextView) findViewById(R.id.tvDescricao);
        tvDuracaoRepeticoes = (TextView) findViewById(R.id.tvDuracaoRepeticoes);
        ivFoto = (ImageView) findViewById(R.id.ivExercicio);
        tvDescricao.setMovementMethod(new ScrollingMovementMethod());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        Exercicio exercicio = (Exercicio) getIntent().getSerializableExtra(PARAM_1);

        tvNome.setText(exercicio.getNome());
        tvDescricao.setText(exercicio.getDescricao());
        if (exercicio.getDuracao() != 0){
            tvDuracaoRepeticoes.setText("Duração: " + getDurationBreakdown(exercicio.getDuracao()));
        }else{
            tvDuracaoRepeticoes.setText("Repetições: " + exercicio.getRepeticoes());
        }
        new DownloadImageTask(ivFoto).execute(exercicio.getFoto());
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
