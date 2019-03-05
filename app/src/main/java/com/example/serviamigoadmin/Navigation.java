package com.example.serviamigoadmin;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.serviamigoadmin.Fragment.ChatUsuarioFragment;
import com.example.serviamigoadmin.Fragment.ConsultaAlertasTempranasFragment;
import com.example.serviamigoadmin.Fragment.MisAsesoriasFragment;
import com.example.serviamigoadmin.Fragment.Registrar_AdministradorFragment;
import com.example.serviamigoadmin.Fragment.Registrar_noticiaFragment;
import com.example.servimaigoadmin.R;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConsultaAlertasTempranasFragment.OnFragmentInteractionListener, Registrar_noticiaFragment.OnFragmentInteractionListener, MisAsesoriasFragment.OnFragmentInteractionListener, ChatUsuarioFragment.OnFragmentInteractionListener, Registrar_AdministradorFragment.OnFragmentInteractionListener {
    private ConsultaAlertasTempranasFragment consultaAlertasTempranasFragment;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        consultaAlertasTempranasFragment = new ConsultaAlertasTempranasFragment();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.framengMaster,new ConsultaAlertasTempranasFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        boolean selecionado = false;
        if (id == R.id.consulta_alertas_tempranas) {
            fragment = consultaAlertasTempranasFragment;
            selecionado = true;
        }
        if (id == R.id.crear_noticia) {
            fragment = new Registrar_noticiaFragment();
            selecionado = true;
        }
        if (id == R.id.ver_mis_Asesorias) {
            fragment = new MisAsesoriasFragment();
            selecionado = true;
        }
        if (id == R.id.registrar_asesor) {
            fragment = new Registrar_AdministradorFragment();
            selecionado = true;
        }
        if (id == R.id.cerrar_sesion) {
            finish();
            return false;
        }
        if(selecionado)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.framengMaster,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
