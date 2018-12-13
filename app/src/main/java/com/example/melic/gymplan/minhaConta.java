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
import android.widget.TextView;
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

    private OnFragmentInteractionListener mListener;

    EditText etPrimeiroNome,etApelido,etAltura,etPeso;
    Button btAtualizarDados;
    ConstraintLayout cl;
    ProgressBar pb;
    TextView tvAtualizarDados;
    private static String URL = "userupdate/update?id=";

    private User user;

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
        this.cl = (ConstraintLayout) view.findViewById(R.id.clMinhaConta);
        this.pb = (ProgressBar) view.findViewById(R.id.pbMinhaConta);
        this.tvAtualizarDados = (TextView) view.findViewById(R.id.tvAtualizarDados);

        this.user = SingletonData.getInstance(getActivity(),0).getUser();

        this.etPrimeiroNome.setText(this.user.getPrimeiroNome());
        this.etApelido.setText(this.user.getUltimoNome());
        this.etAltura.setText(this.user.getAltura() + "");
        this.etPeso.setText(this.user.getPeso() + "");

        this.btAtualizarDados.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(checkValues()){
                    atualizarDados();
                }
            }
        });

        this.tvAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        return view;

    }

    private void atualizarDados(){
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("primeiroNome", etPrimeiroNome.getText());
            jsonBody.put("ultimoNome", etApelido.getText());
            jsonBody.put("peso", Double.parseDouble(etPeso.getText().toString()));
            jsonBody.put("altura", Double.parseDouble(etAltura.getText().toString()));

            String url = getActivity().getResources().getString(R.string.url) + URL + user.getId() + "&access-token=" + user.getAuth_key();
            JsonObjectRequest jsonObject = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            user.saveUserInFile(getContext());
                            Toast.makeText(getActivity(), "Utilizador atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                            //((IndexActivity)getActivity()).mudarParaMenuTreino(R.id.nav_meusPlanos);
                        }
                    }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    //algum erro, por exemplo cena
                    Toast.makeText(getActivity(), "Ocurreu um erro, tente de novo mais tarde.", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObject);
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Erro na conversão", Toast.LENGTH_SHORT).show();
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

    public void updateUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = getActivity().getString(R.string.url) + "userupdate/status?access-token=" + this.user.getAuth_key();
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        //save num file do user
                        if(!response.isNull("primeiroNome")){
                            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                User u = new User(response.getInt("id"),response.getString("primeiroNome"),response.getString("ultimoNome"),
                                        in.parse(response.getString("dataNascimento")), response.getDouble("altura"),response.getDouble("peso"),
                                        response.getInt("sexo"),response.getString("auth_key"));
                                user = u;
                                user.saveUserInFile(getActivity());
                                etPrimeiroNome.setText(user.getPrimeiroNome());
                                etApelido.setText(user.getUltimoNome());
                                etAltura.setText(user.getAltura() + "");
                                etPeso.setText(user.getPeso() + "");
                                Toast.makeText(getActivity(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Timeout", Toast.LENGTH_SHORT).show();
                        }
                        pb.setVisibility(View.GONE);
                        cl.setEnabled(true);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //algum erro, por exemplo cena
                pb.setVisibility(View.GONE);
                cl.setEnabled(true);
                Toast.makeText(getActivity(), "Timeout", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObject);
    }
}
