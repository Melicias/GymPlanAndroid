package com.example.melic.gymplan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class minhaConta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    EditText etPrimeiroNome,etApelido,etAltura,etPeso;
    Button btAtualizarDados;

    public minhaConta() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static minhaConta newInstance() {
        minhaConta fragment = new minhaConta();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    private boolean checkValues() {
        if (this.etPrimeiroNome.getText().toString().length() >= 3) {
            if (this.etApelido.getText().toString().length() >= 3) {
                if (this.etAltura.getText().toString().length() != 0) {
                    if (this.etPeso.getText().toString().length() != 0) {

                        try {
                            Double altura = Double.parseDouble(this.etAltura.getText().toString());
                            Double peso = Double.parseDouble(this.etPeso.getText().toString());

                            String[] splitterAltura = altura.toString().split("\\.");
                            if (splitterAltura.length >= 2) {
                                if (splitterAltura[0].length() >= 1 && splitterAltura[1].length() >= 1 && splitterAltura[0].length() <= 2 && splitterAltura[1].length() <= 2) {
                                    String[] splitterPeso = peso.toString().split("\\.");
                                    if (splitterPeso.length >= 2) {
                                        if (splitterPeso[0].length() >= 1 && splitterPeso[1].length() >= 1 && splitterPeso[0].length() <= 3 && splitterPeso[1].length() <= 3) {
                                            return true;
                                        } else {
                                            //numeros errados no peso
                                        }
                                    } else {
                                        //peso so com 1 ou 2
                                    }
                                } else {
                                    //numeros errados na altura
                                }
                            } else {
                                //altura so com 1 ou 2
                            }
                        } catch (IllegalArgumentException ex) {
                            //erro na conversao
                        }
                    } else {
                        //peso null
                    }
                } else {
                    //altura null
                }

            } else {
                //ultimo nome null ou menor que 3
            }
        } else {
            //primerio nome null ou menor que 3
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_minha_conta, container, false);
         this.etPrimeiroNome = (EditText) view.findViewById(R.id.etPrimeiroNome);
        this.etApelido = (EditText) view.findViewById(R.id.etApelido);
        this.etAltura = (EditText) view.findViewById(R.id.etAltura);
        this.etPeso = (EditText) view.findViewById(R.id.etPeso);
        this.btAtualizarDados = (Button) view.findViewById(R.id.btAtualizarDados);

        this.btAtualizarDados.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(checkValues()){

                        Toast.makeText(getContext(), "Yh Deu", Toast.LENGTH_LONG).show();
                    }
                }

        });

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View content = view.findViewById(R.id.content);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
