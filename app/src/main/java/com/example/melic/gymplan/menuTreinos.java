package com.example.melic.gymplan;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.melic.gymplan.adaptadores.Treinos_Adapter;
import com.example.melic.gymplan.classes.CategoriaTreino;
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.Treino;
import com.example.melic.gymplan.gestores.GestorCategoria;
import com.example.melic.gymplan.gestores.GestorDificuldade;
import com.example.melic.gymplan.gestores.GestorTreino;

import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link menuTreinos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link menuTreinos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menuTreinos extends Fragment {
    public static final int MENU = 1;
    public static final int MEU = 2;

    ArrayList<Treino> treinos;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "escolha";

    // TODO: Rename and change types of parameters
    private int escolha;


    private OnFragmentInteractionListener mListener;

    public menuTreinos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment menuTreinos.
     */
    // TODO: Rename and change types and number of parameters
    public static menuTreinos newInstance(int escolha) {
        menuTreinos fragment = new menuTreinos();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, escolha);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            escolha = getArguments().getInt(ARG_PARAM);
        }
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("aaa");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_treinos, container, false);

        RecyclerView rvTreinos =(RecyclerView)view.findViewById(R.id.rvTreinos);
        // Initialize contacts
        if(this.escolha == MENU){
            //menu option
            GestorTreino gt = new GestorTreino();
            this.treinos = gt.getTreinos();
        }else{
            //meus treinos option
            GestorTreino gt = new GestorTreino();
            this.treinos = gt.getTreinos();
        }

        if(this.treinos.size() == 0){
            ConstraintLayout cl = (ConstraintLayout)view.findViewById(R.id.clTreinos);
            cl.setVisibility(view.GONE);
            if(this.escolha == MENU){
                TextView tv = (TextView)view.findViewById(R.id.tvNaoHaTreinos);
                tv.setVisibility(view.VISIBLE);
            }else{
                Button btRederecionarMenuTreinos = (Button)view.findViewById(R.id.btRederecionarMenuTreinos);
                btRederecionarMenuTreinos.setVisibility(view.VISIBLE);
                btRederecionarMenuTreinos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //mandar para Menu treinos
                        //fazer a acao
                    }
                });
            }
        }else{
            Treinos_Adapter AdaptadorTreinos = new Treinos_Adapter(treinos);
            rvTreinos.setAdapter(AdaptadorTreinos);
            rvTreinos.setLayoutManager(new LinearLayoutManager(getContext()));

            GestorCategoria gc = new GestorCategoria();
            ArrayAdapter<String> spinnerArrayAdapterCategoria = new ArrayAdapter<String>(
                    getContext(),R.layout.spinner_item,gc.getCategoriasString()){
                @Override
                public boolean isEnabled(int position){
                    if(position == 0){
                        return false;
                    } else {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        tv.setTextColor(Color.GRAY);
                    }
                    return view;
                }
            };
            Spinner spCategoria =(Spinner)view.findViewById(R.id.spCategorias);
            spinnerArrayAdapterCategoria.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spCategoria.setAdapter(spinnerArrayAdapterCategoria);
            spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position > 1){
                        // position 0 and 1 with shit, so need to add 2 for this to work

                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            GestorDificuldade gd = new GestorDificuldade();
            ArrayAdapter<String> spinnerArrayAdapterDificuldade = new ArrayAdapter<String>(
                    getContext(),R.layout.spinner_item,gd.getDificuldadesString()){
                @Override
                public boolean isEnabled(int position){
                    if(position == 0){
                        return false;
                    } else {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        tv.setTextColor(Color.GRAY);
                    }
                    return view;
                }
            };
            Spinner spDificuldade =(Spinner)view.findViewById(R.id.spDificuldade);
            spinnerArrayAdapterDificuldade.setDropDownViewResource(R.layout.spinner_item);
            spDificuldade.setAdapter(spinnerArrayAdapterDificuldade);
            spDificuldade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position > 1){
                        // position 0 and 1 with shit, so need to add 2 for this to work

                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return view;
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
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().getActionBar()
                .setTitle("Menu Treino");
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
