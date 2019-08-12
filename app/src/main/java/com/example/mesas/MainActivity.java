package com.example.mesas;

import android.os.Bundle;

import android.os.Handler;

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

import java.util.ArrayList;

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
        private int MSStatusfin;
        private int SDStatusfin=50;
        private TextView txt_sd;
        private TextView txt_mu;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> items= new ArrayList<Item>();
    //Datos para ingresar a la base de datos
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "wlhkKlqhlA";
    private String pwdMySQL = "fuEhEabZuG";
    private String database = "wlhkKlqhlA";
    private String[] datosConexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        generarMesas();
        /*items.add(new Item("1",R.drawable.ic_restaurant_black_24dp,"Mesa 1","Sillas disponibles: 10"));
        items.add(new Item("2",R.drawable.ic_restaurant_black_24dp,"Mesa 2","Sillas disponibles: 4"));
        items.add(new Item("3",R.drawable.ic_restaurant_black_24dp,"Mesa 3","Sillas disponibles: 0"));
        items.add(new Item("4",R.drawable.ic_restaurant_black_24dp,"Mesa 4","Sillas disponibles: 1"));
        items.add(new Item("5",R.drawable.ic_restaurant_black_24dp,"Mesa 5","Sillas disponibles: 5"));
        items.add(new Item("6",R.drawable.ic_restaurant_black_24dp,"Mesa 6","Sillas disponibles: 7"));
        items.add(new Item("7",R.drawable.ic_restaurant_black_24dp,"Mesa 7","Sillas disponibles: 8"));
        items.add(new Item("8",R.drawable.ic_restaurant_black_24dp,"Mesa 8","Sllas disponibles: 4"));
        items.add(new Item("9",R.drawable.ic_restaurant_black_24dp,"Mesa 9","Sillas disponibles: 4"));
        items.add(new Item("10",R.drawable.ic_restaurant_black_24dp,"Mesa 10","Sillas disponibles: 4"));*/


        //Se setea cuantas mesas hay
        MSStatusfin=items.size();
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
        Progressbar_SD=(ProgressBar) findViewById(R.id.progress_sillas);
        num_sillas=findViewById(R.id.num_sillas);
        num_mesas=findViewById(R.id.num_mesas);

        //Setea el numero de sillas disponibles
        num_sillas.setText(String.valueOf(SDStatusfin));
        //Setea el numero de mesas disponibles
        num_mesas.setText(String.valueOf(MSStatusfin));
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

    public void generarMesas()
    {
        String[] resultadoSQL = null;
        try{
            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    "SELECT * FROM mesa;"
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(MainActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String resultadoConsulta = resultadoSQL[0];
            //Log.e("Resultado",resultadoConsulta);
            String[] datosMesas =  resultadoConsulta.split("\n");
            for(int u=0;u<datosMesas.length;u++){
                String[] resultado = datosMesas[u].split(",");
                items.add(new Item(resultado[0],R.drawable.ic_restaurant_black_24dp,"Mesa "+resultado[0],"Sillas disponibles: "+resultado[1]));
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
}
