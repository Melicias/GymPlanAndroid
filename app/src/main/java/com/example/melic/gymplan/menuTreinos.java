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
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melic.gymplan.adaptadores.Treinos_Adapter;
import com.example.melic.gymplan.classes.CategoriaTreino;
import com.example.melic.gymplan.classes.DificuldadeTreino;
import com.example.melic.gymplan.classes.SingletonData;
import com.example.melic.gymplan.classes.Treino;
import com.example.melic.gymplan.gestores.GestorCategoria;
import com.example.melic.gymplan.gestores.GestorDificuldade;
import com.example.melic.gymplan.gestores.GestorTreino;

import java.util.ArrayList;
import java.util.List;


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
    private Spinner spDificuldade;
    private Spinner spCategoria;


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
        ProgressBar progressBar =(ProgressBar) view.findViewById(R.id.pbMenuTreinos);
        // Initialize contacts
        if(this.escolha == MENU){
            //menu option
            //ONLINE
            this.treinos = SingletonData.getInstance(getActivity(), MENU).getTreinosArray(MENU);
            /*GestorTreino gt = new GestorTreino(0);
            this.treinos = gt.getTreinos();*/
        }else{
            //meus treinos option
            //OFFLINE
            this.treinos = SingletonData.getInstance(getActivity(), MEU).getTreinosArray(MEU);
            /*GestorTreino gt = new GestorTreino(0);
            this.treinos = gt.getTreinos();*/
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
                        ((IndexActivity)getActivity()).mudarParaMenuTreino(R.id.nav_todosTreinos);
                    }
                });
            }
        }else{
            final Treinos_Adapter AdaptadorTreinos = new Treinos_Adapter(treinos, escolha);
            rvTreinos.setAdapter(AdaptadorTreinos);
            rvTreinos.setLayoutManager(new LinearLayoutManager(getContext()));
            final GestorCategoria gc;
            if(this.escolha == MENU){
                gc = SingletonData.getInstance(getActivity(), MENU).getGestorCategorias(MENU);
            }else{
                gc = SingletonData.getInstance(getActivity(), MEU).getGestorCategorias(MEU);
            }
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
            final GestorDificuldade gd;
            if(this.escolha == MENU){
                gd = SingletonData.getInstance(getActivity(), MENU).getGestorDificuldades(MENU);
            }else{
                gd = SingletonData.getInstance(getActivity(), MEU).getGestorDificuldades(MEU);
            }
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

            this.spCategoria =(Spinner)view.findViewById(R.id.spCategorias);
            spinnerArrayAdapterCategoria.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spCategoria.setAdapter(spinnerArrayAdapterCategoria);
            spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idDificuldade = spDificuldade.getSelectedItemPosition();
                    if(idDificuldade == 0 || idDificuldade == 1){
                        idDificuldade = -1;
                    }else{
                        idDificuldade -=2;
                        idDificuldade = gd.getDificuldade(idDificuldade).getId();
                    }

                    if(position == 0 || position == 1){
                        AdaptadorTreinos.pesquisaTudo("",idDificuldade ,-1);
                    }else{
                        AdaptadorTreinos.pesquisaTudo("",idDificuldade ,gc.getCategoria((position-2)).getId());
                    }
                    AdaptadorTreinos.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            this.spDificuldade =(Spinner)view.findViewById(R.id.spDificuldade);
            spinnerArrayAdapterDificuldade.setDropDownViewResource(R.layout.spinner_item);
            spDificuldade.setAdapter(spinnerArrayAdapterDificuldade);
            spDificuldade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria = spCategoria.getSelectedItemPosition();
                    if(idCategoria == 0 || idCategoria == 1){
                        idCategoria = -1;
                    }else{
                        idCategoria -=2;
                        idCategoria = gc.getCategoria(idCategoria).getId();
                    }

                    if(position == 0 || position == 1){
                        AdaptadorTreinos.pesquisaTudo("" , -1,idCategoria);
                    }else{
                        AdaptadorTreinos.pesquisaTudo("" , gd.getDificuldade((position-2)).getId(),idCategoria);
                    }
                    AdaptadorTreinos.notifyDataSetChanged();
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
        if(this.escolha == this.MENU){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Todos os treinos");
        }else{
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Meus treinos");
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
