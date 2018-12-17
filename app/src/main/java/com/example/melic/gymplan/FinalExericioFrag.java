package com.example.melic.gymplan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinalExericioFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinalExericioFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalExericioFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NrRepeticoesRestantes = "repeticoesRestantes";

    private TextView tvRepeticoesEmFalta;
    private Button btRepetir;

    private int nrRepeticoesRestantes;

    private OnFragmentInteractionListener mListener;

    public FinalExericioFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FinalExericioFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalExericioFrag newInstance(int nrRepeticoesRestantes) {
        FinalExericioFrag fragment = new FinalExericioFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_NrRepeticoesRestantes, nrRepeticoesRestantes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nrRepeticoesRestantes = getArguments().getInt(ARG_NrRepeticoesRestantes);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_exericio, container, false);

        tvRepeticoesEmFalta = (TextView)view.findViewById(R.id.tvRepeticoesRestantes);
        btRepetir = (Button)view.findViewById(R.id.btRepetirTreino);

        if (nrRepeticoesRestantes == 0){
            tvRepeticoesEmFalta.setText("Já acabou o treino. Parabéns!");
            btRepetir.setText("Voltar ao menu principal");
        }else{
            tvRepeticoesEmFalta.setText("Repetições em falta: " + nrRepeticoesRestantes);
            btRepetir.setText("Voltar ao primeiro exercicio");
        }

        btRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nrRepeticoesRestantes == 0){
                    Intent Index = new Intent(getActivity(),IndexActivity.class);
                    startActivity(Index);
                    getActivity().finish();
                }else{
                    ((TreinoActivity)getContext()).setViewPagerIndex(0);
                }
            }
        });


        return view;
    }

    public void setNrRepeticoesRestantes(int nr){

        if (nrRepeticoesRestantes == 0){
            tvRepeticoesEmFalta.setText("Já acabou o treino. Parabéns!");
            btRepetir.setText("Voltar ao menu principal");
        }else{
            tvRepeticoesEmFalta.setText("Repetições em falta: " + nrRepeticoesRestantes);
            btRepetir.setText("Voltar ao primeiro exercicio");
        }
        this.nrRepeticoesRestantes = nr;
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
