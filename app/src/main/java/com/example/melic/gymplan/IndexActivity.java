package com.example.melic.gymplan;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melic.gymplan.classes.ModeloBDHelper;
import com.example.melic.gymplan.classes.NetStatus;
import com.example.melic.gymplan.classes.SingletonData;
import com.example.melic.gymplan.classes.User;


public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        menuTreinos.OnFragmentInteractionListener,
        minhaConta.OnFragmentInteractionListener,
        exerciciosFrag.OnFragmentInteractionListener {

    Toolbar toolbar;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_meusPlanos);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_meusPlanos));

        TextView tv = (TextView)navigationView.getHeaderView(0).findViewById(R.id.tvEmailNavHeader);
        User u = new User();
        u = u.getUserFromFile(this);
        tv.setText(u.getEmail());

        this.first = false;
    }

    public void progressBar(boolean pb){
        ProgressBar progressBar =(ProgressBar) findViewById(R.id.progressBar);
        FrameLayout fl = (FrameLayout) findViewById(R.id.content_frame);
        if(pb == true){
            progressBar.setVisibility(View.VISIBLE);
            fl.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            fl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String tag = "";
        /*FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();*/

        if (id == R.id.nav_todosTreinos) {
            tag = "menuTreinos";
            //fragment = fManager.findFragmentByTag(tag);
            //if(fragment == null)
            fragment = new menuTreinos();
            Bundle bundle = new Bundle();
            bundle.putInt("escolha", menuTreinos.MENU);
            fragment.setArguments(bundle);
            /*if (fragment == null) {
                fTransaction.add(R.id.content_frame, fragment, "uniqueTag").addToBackStack(null).commit();
            }
            else { // re-use the old fragment
                fTransaction.replace(R.id.content_frame, fragment, "uniqueTag").addToBackStack(null).commit();
            }*/
        } else if (id == R.id.nav_meusPlanos) {
            tag = "meusTreinos";
            fragment = new menuTreinos();
            Bundle bundle = new Bundle();
            bundle.putInt("escolha", menuTreinos.MEU);
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_minhaConta) {
            tag = "minhaConta";
            fragment = new minhaConta();
        } else if (id == R.id.nav_logout) {
            User u = new User();
            u.deleteUserFile(getApplicationContext());
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_AtualizarTreinosLocal) {
            if(NetStatus.getInstance(this).isOnline()) {
                ModeloBDHelper modeloDB = SingletonData.getInstance(getApplicationContext(),0).getModeloDB();
                modeloDB.updateDBFromApi();
            }else{
                Toast.makeText(this, "Não existe uma ligação a internet!", Toast.LENGTH_SHORT).show();
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment,tag);
            if(!first)
                ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void mudarParaMenuTreino(int opcao){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(opcao);
        onNavigationItemSelected(navigationView.getMenu().findItem(opcao));
    }

    public void updatesMeusTreinos(){
        menuTreinos frag = (menuTreinos) getSupportFragmentManager().findFragmentByTag("meusTreinos");
        if(frag != null){
            frag.updateAfterAddRemove();
            frag.updateSpinners();
        }
    }
}
