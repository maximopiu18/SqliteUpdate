package com.example.listacontactos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.listacontactos.adapter.AdapterGrid;
import com.example.listacontactos.adapter.AdapterList;
import com.example.listacontactos.db.Contactos;
import com.example.listacontactos.utils.Constants;
import com.example.listacontactos.utils.Utils;

import java.net.Inet4Address;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FrameLayout frameGrid, frameList;
    GridView gridView;
    ListView lista;
    ImageButton imgButton;
    Toolbar toolbarMain;

    Spinner spinnerFiltros;
    ArrayAdapter<String> adapterSpinner;
    String listaSpinner[] = {"Lista", "Celdas"};

    ArrayList<String> nombre, telefono, urlImagen, iddatabase;
    Integer imagenes[];
    Contactos contactos;

    int positionActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameList = (FrameLayout) findViewById(R.id.frame_lista);
        frameGrid = (FrameLayout) findViewById(R.id.frame_grid);
        gridView = (GridView)findViewById(R.id.lgridcontactos);
        lista =(ListView)findViewById(R.id.listacontactos);
        imgButton = (ImageButton)findViewById(R.id.imgbutton_addcontact);
        toolbarMain =(Toolbar)findViewById(R.id.toolbar_main);
        spinnerFiltros = (Spinner)findViewById(R.id.spfiltros);
        setSupportActionBar(toolbarMain);
        contactos = new Contactos(MainActivity.this);
        cargarMisPreferencias();
        crearSpinner();
        cargarDatos();
        gridView.setAdapter( new AdapterGrid(MainActivity.this, nombre, telefono, urlImagen));
        lista.setAdapter( new AdapterList(MainActivity.this, nombre, telefono, imagenes, iddatabase));

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_NuevoContacto.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public MainActivity(){

    }
    public void crearSpinner()
    {
        adapterSpinner = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listaSpinner);
        spinnerFiltros.setAdapter(adapterSpinner);
        spinnerFiltros.setSelection(positionActual);
        spinnerFiltros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(position ==0)
                {
                    frameListaVisible();

                }
                else if(position == 1)
                {
                    frameGridVisible();
                }
                guardarMisPreferencias(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void frameListaVisible(){
        frameList.setVisibility(View.VISIBLE);
        frameList.setEnabled(true);
        frameGrid.setVisibility(View.GONE);
        frameGrid.setEnabled(false);
    }
    public void frameGridVisible(){
        frameList.setVisibility(View.GONE);
        frameList.setEnabled(false);
        frameGrid.setVisibility(View.VISIBLE);
        frameGrid.setEnabled(true);
    }

    public void cargarDatos(){
        try{
            nombre.clear();
            telefono.clear();
            urlImagen.clear();
            iddatabase.clear();
        }
        catch (Exception e)
        {
            Log.e("error", "error al limpiar" + e);
        }
        nombre = new ArrayList<String>();
        telefono = new ArrayList<String>();
        urlImagen = new ArrayList<String>();
        iddatabase = new ArrayList<String>();
        try {
            contactos.abrir();
            nombre = contactos.SelectNombres();
            telefono = contactos.SelectTelefonos();
            urlImagen = contactos.SelectTelefonos();
            iddatabase = contactos.SelectidDB();
            contactos.cerrar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i =0;
        imagenes = new Integer[nombre.size()];
        while(i<nombre.size()){
            imagenes[i] = R.drawable.img_1;
            i++;
        }

    }


    public void cargarMisPreferencias(){
        Utils utils = new Utils();
        utils.cargarPreferencias(MainActivity.this);
        if(Constants.tipofiltro.equals("0"))
        {
            frameListaVisible();
        }
        else if(Constants.tipofiltro.equals("1")){
            frameGridVisible();
        }
        positionActual = Integer.parseInt(Constants.tipofiltro);
    }
    public void guardarMisPreferencias(int position){
        Utils utils = new Utils();
        utils.guardarPreferencias(MainActivity.this, String.valueOf(position));
    }


}
