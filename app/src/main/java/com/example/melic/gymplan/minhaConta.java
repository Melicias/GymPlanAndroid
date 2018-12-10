package com.example.melic.gymplan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.melic.gymplan.classes.SingletonData;
import com.example.melic.gymplan.classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class minhaConta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    EditText etPrimeiroNome,etApelido,etAltura,etPeso;
    Button btAtualizarDados;
    private static String URL = "https://gymplanyii.000webhostapp.com/GymPlanYii/api/web/user/signup";

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
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("primeiroNome", etPrimeiroNome.getText());
                        jsonBody.put("ultimoNome", etApelido.getText());
                        jsonBody.put("peso", Double.parseDouble(etPeso.getText().toString()));
                        jsonBody.put("altura", Double.parseDouble(etAltura.getText().toString()));

                        JsonObjectRequest jsonObject = new JsonObjectRequest(
                                Request.Method.POST,
                                URL,
                                jsonBody,
                                new Response.Listener<JSONObject>() {
                                    public void onResponse(JSONObject response) {
                                        User user = SingletonData.getInstance(getContext(),1).getUser();
                                        user.saveUserInFile(getContext());
                                        Intent Index = new Intent(getActivity(),IndexActivity.class);
                                        startActivity(Index);
                                    }
                                }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                //algum erro, por exemplo cena
                                Toast.makeText(getActivity(), "Bla bla", Toast.LENGTH_SHORT).show();
                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(jsonObject);
                    } catch (JSONException e) {

                        Toast.makeText(getActivity(), "Ocurreu algum erro, tente mais tarde de novo", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Minha Conta");

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
