package com.example.listacontactos.adapter;

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
import android.widget.Toast;

import com.example.listacontactos.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterGrid extends BaseAdapter {
    Context contexto;
    ArrayList<String> nombre;
    ArrayList<String> telefono;
    ArrayList<String> urlImagen;

    public AdapterGrid(Context contexto, ArrayList<String> nombre, ArrayList<String>telefono, ArrayList<String> urlImagen ){
        this.contexto = contexto;
        this.nombre = nombre;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemGrid;

        if (convertView == null) {

            itemGrid = new View(contexto);
            itemGrid = inflater.inflate(R.layout.item_grid, null);
            TextView tv_name= (TextView) itemGrid.findViewById(R.id.item_name);
            TextView tv_number = (TextView) itemGrid.findViewById(R.id.item_phone);
            ImageView imageView = (ImageView) itemGrid.findViewById(R.id.item_photo);
            ImageButton imgLlamada = (ImageButton)itemGrid.findViewById(R.id.item_button);



            tv_name.setText(nombre.get(position));
            tv_number.setText(telefono.get(position));
            // set image based on selected text
           // imageView.setImageResource(R.drawable.img_1);

            imgLlamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Log.e("posicion", "posicion" + position);
                    //Toast.makeText(contexto, "posicion" +position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono.get(position).toString(), null));
                    contexto.startActivity(intent);
                  //  contexto.startActivity(intent);
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
