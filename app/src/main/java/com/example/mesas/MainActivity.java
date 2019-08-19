package com.example.mesas;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //PROGRESS BAR DE LAS SILLAS DISPONIBLES
        private ProgressBar Progressbar_SD;
        private ProgressBar Progressbar_MS;
    //Numero de sillas disponibles
        private TextView num_sillas;
    //Numero de mesas disponibles
        private TextView num_mesas;
    //PROGRESS BAR DE LAS SILLAS EN USO
        private Handler mHandler= new Handler();
    //PROGRESS BAR DE LAS MESAS DISPONIBLES
        private int MSStatus=0;
        private int SDStatus=0;
        private int MSStatusfin=0;
        private int SDStatusfin=0;
        private TextView txt_sd;
        private TextView txt_mu;
    //Vistas y adaptadores
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
    //Datos para ingresar a la base de datos
        private String serverIP = "remotemysql.com";
        private String port = "3306";
        private String userMySQL = "wlhkKlqhlA";
        private String pwdMySQL = "fuEhEabZuG";
        private String database = "wlhkKlqhlA";
        String driver = "com.mysql.jdbc.Driver";
        private String[] datosConexion = null;
        private int numSillas;
    //Array de mesas e items
        private ArrayList<Mesa> mesas = new ArrayList<>();
        private ArrayList<Item> items= new ArrayList<Item>();
        private ArrayList<Silla> sillas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        //Con el metodo generar items , se crean las cartas que aparecen de las mesas que tenemos a disposicon en el restaurante
        //Este metodo tambien permite contar cuantas mesas hay completamante disponibles
        generarItems();
        setMesasEnSillas(mesas);

        //con este metodo contamos cuantas sillas hay disponibles
        contarSillas();



        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter= new ExampleAdapter(items);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mesa agregada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                MSStatusfin++;
            }
        });




        Progressbar_MS= findViewById(R.id.progress_mesas);

        //decimos cual es el maximo numero de mesas
        Progressbar_MS.setMax(items.size());
        Progressbar_SD=(ProgressBar) findViewById(R.id.progress_sillas);

        //decimos cual es el maximo de la barra de sillas
        Progressbar_SD.setMax(numSillas);

        num_sillas=findViewById(R.id.num_sillas);
        num_mesas=findViewById(R.id.num_mesas);

        //Setea el numero de sillas disponibles
        num_sillas.setText(String.valueOf(SDStatusfin)+"/"+String.valueOf(numSillas));
        //Setea el numero de mesas disponibles
        num_mesas.setText(String.valueOf(MSStatusfin)+"/"+String.valueOf(items.size()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(SDStatus<SDStatusfin){
                    SDStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                        Progressbar_SD.setProgress(SDStatus,true);
                        }
                    });
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(MSStatus<MSStatusfin){
                    MSStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Progressbar_MS.setProgress(MSStatus,true);
                        }
                    });
                }

            }
        }).start();



    }

    public void liberarSilla(View v){
        SDStatusfin--;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void generarItems(){
        String[] resultadoSQL = null;
        try{
            datosConexion = conexionBasedeDatos("SELECT * FROM mesa;");
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(MainActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String resultadoConsulta = resultadoSQL[0];
            //Log.e("Resultado",resultadoConsulta);
            String[] datosMesas =  resultadoConsulta.split("\n");
            for (String datosMesa : datosMesas) {
                String[] resultado = datosMesa.split(",");

                if (resultado[2].equals("0")){
                items.add(new Item(resultado[0], R.mipmap.ic_desocupada
                        , "Mesa " + resultado[0], "Sillas disponibles: " + resultado[1]));
                Mesa mesa = new Mesa(Integer.valueOf(resultado[0]),crearSillas(resultado[0]));
                mesas.add(mesa);
                MSStatusfin++;}

                else if (resultado[2].equals("1")){
                    items.add(new Item(resultado[0], R.mipmap.ic_mesasemiocupada
                            , "Mesa " + resultado[0], "Sillas disponibles: " + resultado[1]));
                    Mesa mesa = new Mesa(Integer.valueOf(resultado[0]),crearSillas(resultado[0]));
                    mesas.add(mesa);
                }

                else if (resultado[2].equals("2")){
                    items.add(new Item(resultado[0], R.mipmap.mesa_ocupada
                            , "Mesa " + resultado[0], "Sillas disponibles: " + resultado[1]));
                    Mesa mesa = new Mesa(Integer.valueOf(resultado[0]),crearSillas(resultado[0]));
                    mesas.add(mesa);

                }


            }
            }catch(Exception ex)
            {
                Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();

            }
    }


    public void contarSillas(){
        String[] resultadoSQL = null;
        try{
            String [] datosConexion2 = conexionBasedeDatos("SELECT * FROM silla;");
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion2).get();
            Toast.makeText(MainActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String resultadoConsulta = resultadoSQL[0];
            //Log.e("Resultado",resultadoConsulta);
            String[] datosMesas =  resultadoConsulta.split("\n");
            for (String datosMesa : datosMesas) {
                numSillas++;
                String[] resultado = datosMesa.split(",");

                if (resultado[1].equals("0")){
                    SDStatusfin++;}


            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private String[] conexionBasedeDatos(String string){
        return new String[]{
                serverIP,
                port,
                database,
                userMySQL,
                pwdMySQL,
                string
        };
    }

    private void setMesasEnSillas(ArrayList<Mesa> mesas){
        for(Mesa mesa: mesas){
            ArrayList<Silla> sill = mesa.getSillas();
            for(Silla silla: sill){
                silla.setMesa(mesa);
                sillas.add(silla);
                Log.e("TAG",silla.toString());
            }
        }
    }

    private ArrayList<Silla> crearSillas(String string) {
        ArrayList<Silla> sillas = new ArrayList<>();
        String[] resultadoSQL = null;
        try {
            String[] datosConexion = conexionBasedeDatos("SELECT * FROM silla WHERE mesa=" + string+ ";");
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            String[] datosSillas = resultadoSQL[0].split("\n");
            for(String datosSilla : datosSillas){
                String[] resultado = datosSilla.split(",");
                Silla silla = new Silla(Integer.valueOf(resultado[0]),convertirBooleano(resultado[1]));
                sillas.add(silla);
            }

        } catch (Exception ex){
            Log.e("TAG", Arrays.toString(ex.getStackTrace()));
        }
        return sillas;
    }

    private boolean convertirBooleano(String s){
        if(s.equals("0")){
            return false;
        }else if(s.equals("1")){
            return true;
        }
        return false;
    }
}
