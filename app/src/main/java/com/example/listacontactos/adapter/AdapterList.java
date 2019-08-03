package com.example.listacontactos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listacontactos.MainActivity;
import com.example.listacontactos.R;
import com.example.listacontactos.db.Contactos;

import java.util.ArrayList;

public class AdapterList extends BaseAdapter {
    Context contexto;
    ArrayList<String> nombre;
    ArrayList<String> telefono;
    ArrayList<String> urlImagen;
    ArrayList<String>iddatabase;
    Integer imagenes[];
    Contactos contactos;
    public AdapterList(Context contexto, ArrayList<String> nombre, ArrayList<String>telefono, Integer imagenes[], ArrayList<String> iddatabase){
        this.contexto = contexto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
        this.imagenes = imagenes;
        this.iddatabase = iddatabase;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemGrid;

        if (convertView == null) {

            itemGrid = new View(contexto);
            itemGrid = inflater.inflate(R.layout.item_lista, null);
            TextView tv_name= (TextView) itemGrid.findViewById(R.id.item_name);
            TextView tv_number = (TextView) itemGrid.findViewById(R.id.item_phone);
            ImageView imageView = (ImageView) itemGrid.findViewById(R.id.item_photo);
            ImageView imageDelete = (ImageView) itemGrid.findViewById(R.id.item_button_delete);
            ImageView imageEdit = (ImageView) itemGrid.findViewById(R.id.item_button_edit);
            ImageButton imgLlamada = (ImageButton)itemGrid.findViewById(R.id.item_button);

            tv_name.setText(nombre.get(position));
            tv_number.setText(telefono.get(position));
            imageView.setImageResource(imagenes[position]);

            imgLlamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono.get(position).toString(), null));
                    contexto.startActivity(intent);
                }
            });
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactos = new Contactos(contexto);
                    try {
                        contactos.abrir();
                        contactos.eliminarContacto(iddatabase.get(position));
                        contactos.cerrar();

                        Intent intent = new Intent(contexto, MainActivity.class);
                        contexto.startActivity(intent);
                        ((Activity)contexto).finish();
                        Log.e("eliminado", "eliminado");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("error", "error al eliminar" + e);
                    }

                }
            });
            imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else {
            itemGrid = (View) convertView;
        }
        return itemGrid;
    }

    @Override
    public int getCount() {
        return nombre.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
