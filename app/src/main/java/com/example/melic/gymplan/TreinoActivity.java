package com.example.melic.gymplan;

import android.app.ActionBar;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.melic.gymplan.classes.Treino;
import com.rd.PageIndicatorView;

import me.relex.circleindicator.CircleIndicator;


public class TreinoActivity extends AppCompatActivity  implements exercicioFrag.OnFragmentInteractionListener, FinalExericioFrag.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static final String ARG_PARAM = "treino";
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private PageIndicatorView pageIndicatorView;
    private Treino treino;
    private int repeticoesEmFalta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        treino = (Treino)getIntent().getExtras().getSerializable(ARG_PARAM);

        if(treino.getRepeticoes() == 0){
            repeticoesEmFalta = treino.getRepeticoes() - 1;
        }else{
            repeticoesEmFalta = treino.getRepeticoes();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onResume(){
        super.onResume();
        ((AppCompatActivity) this).getSupportActionBar().setTitle("Treino Iniciado");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setViewPagerIndex(int position){
        this.mViewPager.setCurrentItem(position);
        this.repeticoesEmFalta--;
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(treino.getExercicios().size() == position)
                return FinalExericioFrag.newInstance(repeticoesEmFalta);
            return exercicioFrag.newInstance(treino.getExercicio(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return treino.getExercicios().size()+1;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof FinalExericioFrag) {
                ((FinalExericioFrag) object).setNrRepeticoesRestantes(repeticoesEmFalta);
            }
            return POSITION_NONE;
        }

    }

}
