package com.example.melic.gymplan;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melic.gymplan.classes.DownloadImageTask;
import com.example.melic.gymplan.classes.Exercicio;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link exercicioFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link exercicioFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exercicioFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM = "exercicio";

    // TODO: Rename and change types of parameters
    private Exercicio exercicio;
    private CountDownTimer cdt;
    private long timeProgress;
    private TextView tvDuracaoRepeticoes;
    private Button btComecar;

    private OnFragmentInteractionListener mListener;

    public exercicioFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment exercicioFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static exercicioFrag newInstance(Exercicio param) {
        exercicioFrag fragment = new exercicioFrag();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercicio = (Exercicio)getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercicio, container, false);
        final TextView tvNome,tvDescricao;
        ImageView ivFoto;

        tvNome = (TextView) view.findViewById(R.id.tvNome);
        tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        tvDuracaoRepeticoes = (TextView) view.findViewById(R.id.tvDuracaoRepeticoes);
        ivFoto = (ImageView) view.findViewById(R.id.ivExercicio);
        btComecar = view.findViewById(R.id.btComecar);
        tvDescricao.setMovementMethod(new ScrollingMovementMethod());

        tvNome.setText(exercicio.getNome());
        tvDescricao.setText(exercicio.getDescricao());
        if (exercicio.getDuracao() != 0){
            tvDuracaoRepeticoes.setText("Duração: " + getDurationBreakdown(exercicio.getDuracao()));
            timeProgress = this.exercicio.getDuracao()*1000;
        }else{
            tvDuracaoRepeticoes.setText("Repetições: " + exercicio.getRepeticoes());
            tvDuracaoRepeticoes.setTextColor(Color.parseColor("#5e0000"));
            btComecar.setVisibility(View.GONE);
        }
        //new DownloadImageTask(ivFoto).execute(exercicio.getFoto());
        Picasso.get().load(exercicio.getFoto()).placeholder(R.drawable.loading).error(R.drawable.image_not_available).into(ivFoto);

        btComecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btComecar.getText().toString().equals("Pausar")){
                    cdt.cancel();
                    btComecar.setText("Começar");
                }else{
                    cdt = new CountDownTimer(timeProgress,1000) {
                        @Override
                        public void onTick(long l) {
                            String text = String.format(Locale.getDefault(), "Duração: %02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(l) % 60,
                                    TimeUnit.MILLISECONDS.toSeconds(l) % 60);
                            //tvDuracaoRepeticoes.setText(l/1000 + "s");
                            tvDuracaoRepeticoes.setText(text);
                            timeProgress = l;
                        }

                        @Override
                        public void onFinish() {
                            tvDuracaoRepeticoes.setText("Feito!");
                        }
                    };
                    cdt.start();
                    btComecar.setText("Pausar");
                }
            }
        });

        return view;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exercicio.getDuracao() != 0){
            if(cdt != null){
                cdt.cancel();
                btComecar.setText("Pausar");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exercicio.getDuracao() != 0){
            if(cdt != null){
                cdt.cancel();
                btComecar.setText("Pausar");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exercicio.getDuracao() != 0){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
