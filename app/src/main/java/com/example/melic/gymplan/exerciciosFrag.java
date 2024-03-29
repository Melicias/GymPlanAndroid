package com.example.melic.gymplan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.melic.gymplan.adaptadores.Exercicios_Adapter;
import com.example.melic.gymplan.classes.Treino;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link exerciciosFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link exerciciosFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exerciciosFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "treino";

    // TODO: Rename and change types of parameters
    private Treino treino;
    Button btComecarTreino;
    private OnFragmentInteractionListener mListener;

    public exerciciosFrag() {
        // Required empty public constructor
    }


    public static exerciciosFrag newInstance(Treino treino) {
        exerciciosFrag fragment = new exerciciosFrag();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, treino);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.treino = (Treino)getArguments().getSerializable(ARG_PARAM);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_exercicios, container, false);

        RecyclerView rvExercicios =(RecyclerView)view.findViewById(R.id.rvExercicios);

        Exercicios_Adapter AdaptadorTreinos = new Exercicios_Adapter(treino.getExercicios());
        rvExercicios.setAdapter(AdaptadorTreinos);
        rvExercicios.setLayoutManager(new LinearLayoutManager(getContext()));

        this.btComecarTreino = (Button) view.findViewById(R.id.btComecarTreino);

        this.btComecarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComecarTreino();
            }
        });
        return view;
    }

    public void ComecarTreino() {
        Intent i = new Intent(getActivity(),TreinoActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(TreinoActivity.ARG_PARAM, treino);
        i.putExtras(mBundle);
        startActivity(i);
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
