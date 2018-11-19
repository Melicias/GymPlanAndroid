package com.example.melic.gymplan;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


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
        this.first = false;
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
            toolbar.setTitle("Todos os treinos");
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
            toolbar.setTitle("Meus treinos");
        } else if (id == R.id.nav_minhaConta) {
            tag = "minhaConta";
            toolbar.setTitle("Minha treinos");
            fragment = new minhaConta();
        } else if (id == R.id.nav_logout) {

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
}
