package com.example.nachodelaviuda.festivaleoglobal;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nachodelaviuda.festivaleoglobal.chat.Mensajeria;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentoInformacion.OnFragmentInteractionListener {

    private DrawerLayout drawer;
    private TextView nombreUsuario, correoUsuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        auth = FirebaseAuth.getInstance();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //----------------------------------------------------------------------------------------------------------------------------------------------
        View hView = navigationView.getHeaderView(0);
        nombreUsuario = (TextView) hView.findViewById(R.id.nombreDeUsuario);
        correoUsuario = (TextView) hView.findViewById(R.id.correoUsuario);
        try {
            String str = auth.getCurrentUser().getDisplayName();
            if (Utilidades.toastero) {
                Toast.makeText(this, "Bienvenid@: " + str, Toast.LENGTH_SHORT).show();
                Utilidades.toastero = false;
            }
            nombreUsuario.setText(auth.getCurrentUser().getDisplayName());
            correoUsuario.setText(auth.getCurrentUser().getEmail());

        } catch (NullPointerException e) {
        }
        //----------------------------------------------------------------------------------------------------------------------------------------------

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PantallaPrincipal()).commit();
            navigationView.setCheckedItem(R.id.nav_principal);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Salir? ");
            builder.setMessage("¿Estás seguro de salir?");
            builder.setCancelable(true);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        //super.onBackPressed();
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_end_session) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            Fragment miFragment = null;
            boolean fragmentoSeleccionado = false;
            switch (item.getItemId()) {
                case R.id.nav_principal:
                    Utilidades.proveniencia = "principal";
                    miFragment = new PantallaPrincipal();
                    fragmentoSeleccionado = true;
                    break;
                case R.id.nav_europa:
                    Utilidades.proveniencia = "europa";
                    Intent intentoEuropa = new Intent(MainActivity.this, ListaDeFestivales.class);
                    startActivity(intentoEuropa);
                    break;
                case R.id.nav_america_norte:
                    Utilidades.proveniencia = "norteamerica";
                    Intent intentoAmNor = new Intent(MainActivity.this, ListaDeFestivales.class);
                    startActivity(intentoAmNor);
                    break;
                case R.id.nav_america_sur:
                    Utilidades.proveniencia = "latinoamerica";
                    Intent intentoAmericaSur = new Intent(MainActivity.this, ListaDeFestivales.class);
                    startActivity(intentoAmericaSur);
                    break;
                case R.id.nav_asia:
                    Utilidades.proveniencia = "asia";
                    Intent intentoAsia = new Intent(MainActivity.this, ListaDeFestivales.class);
                    startActivity(intentoAsia);
                    break;
                case R.id.nav_send:
                    Intent intent = new Intent(this, Mensajeria.class);
                    startActivity(intent);
                    break;
                case R.id.crearFestival:
                    Intent intent2 = new Intent(this, CrearFestival.class);
                    startActivity(intent2);
                    break;
                case R.id.information:
                    miFragment = new FragmentoInformacion();
                    fragmentoSeleccionado = true;
                    break;
            }
            if (fragmentoSeleccionado) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, miFragment).commit();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        @Override
        public void onFragmentInteraction (Uri uri){

        }

    }
