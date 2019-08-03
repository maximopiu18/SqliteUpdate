package com.example.listacontactos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listacontactos.db.Contactos;

public class Activity_NuevoContacto extends Activity {
    EditText ed_nombre, ed_telefono;
    Button btn_insertar;
    Contactos contactos;
    boolean datoInsertado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevocontacto);
        ed_nombre = (EditText)findViewById(R.id.formulario_nombre);
        ed_telefono=(EditText)findViewById(R.id.formulario_telefono);
        btn_insertar = (Button)findViewById(R.id.formulario_btn_insertar);
        contactos = new Contactos(Activity_NuevoContacto.this);
        btn_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_nombre.getText().toString().isEmpty() || ed_nombre.getText().toString().equals("") || ed_nombre.getText().toString().matches("")
                ||ed_telefono.getText().toString().isEmpty() || ed_telefono.getText().toString().equals("") || ed_telefono.getText().toString().matches(""))
                {
                    Toast.makeText(Activity_NuevoContacto.this, "Agrega campos,", Toast.LENGTH_SHORT).show();
                    ed_nombre.requestFocus();
                }
                else {

                    try {
                        contactos.abrir();
                        datoInsertado = contactos.Insert(ed_nombre.getText().toString(), ed_telefono.getText().toString());
                        if (datoInsertado == true) {
                            Toast.makeText(Activity_NuevoContacto.this, "Dato Agregado", Toast.LENGTH_SHORT).show();
                            String Resultados = contactos.SelectAllRow();
                            Log.e("Datos", "Datos" + Resultados);
                            abrirLista();
                        } else {
                            Toast.makeText(Activity_NuevoContacto.this, "No AGregado", Toast.LENGTH_SHORT).show();
                        }
                        contactos.cerrar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirLista();
    }

    public void abrirLista(){
        Intent intent = new Intent(Activity_NuevoContacto.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
