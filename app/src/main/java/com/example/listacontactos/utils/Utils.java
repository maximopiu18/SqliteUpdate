package com.example.listacontactos.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public Utils(){

    }
    public void cargarPreferencias(Context context){
        SharedPreferences sharedPreferences = null; ;
        sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Constants.tipofiltro = sharedPreferences.getString("tipofiltro", "0");

    }
    public void guardarPreferencias(Context context, String tipofiltro){
        SharedPreferences sharedPreferences = null;
        sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putString("tipofiltro", tipofiltro);
        editor.commit();
    }
}
