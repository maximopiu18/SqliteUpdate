package com.example.listacontactos.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Contactos {

    Context context;

    // campos
    public static final String ID_FILA = "_id";
    public static final String ID_PERSONA = "nombre_persona";
    public static final String ID_TELEFONO = "telefono_persona";
    private static final String NBD = "Contactos";
    private static final String N_TABLA = "Tabla_Contactos";
    private static final int VERSION_DB = 1;

    private SQLiteDatabase DB;
    private  BDHelper nhelper;


    public Contactos(Context context){
        this.context = context;
    }

    public static  class BDHelper extends SQLiteOpenHelper{

        public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, NBD, null, VERSION_DB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // crear las latablas de sql

            db.execSQL(" CREATE TABLE "+ N_TABLA+ "( "+
                    ID_FILA+ " INTEGER PRIMARY KEY  AUTOINCREMENT, "+
                    ID_PERSONA +" TEXT NOT NULL, "+
                    ID_TELEFONO +" TEXT NOT NULL); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+ N_TABLA );
            onCreate(db);
        }
    }

    public Contactos abrir() throws Exception
    {

        nhelper = new BDHelper(context, null, null, 0);
        DB = nhelper.getReadableDatabase();
        return this;
    }

    public void cerrar() {
        nhelper.close();
    }

    public boolean  Insert(String nom, String tel) {

        ContentValues values = new ContentValues();
        values.put(ID_PERSONA, nom);
        values.put(ID_TELEFONO, tel);
        try{
            DB.insert(N_TABLA,null, values);
            return  true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String SelectAllRow() {
        String [] columnas =  new String[]{ID_FILA,ID_PERSONA,ID_TELEFONO };
        //Curson  lectura de nuestra tabla+
        Cursor c = DB.query( N_TABLA, columnas, null, null, null, null,null);
        String resultado = "";
        int ifila = c.getColumnIndex(ID_FILA);
        int inombre = c.getColumnIndex(ID_PERSONA);
        int itelefono = c.getColumnIndex(ID_TELEFONO);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            resultado = resultado +"ID: "+c.getString(ifila)+"\n Nombre: "+c.getString(inombre).replaceAll("\n","")+"\n num: "+c.getString(itelefono) +" \n\n";
        }
        return resultado;
    }

    public ArrayList<String> SelectNombres(){
        String [] columnas = new String[]{ID_PERSONA};
        Cursor c = DB.query( N_TABLA, columnas, null, null, null, null,null);

        ArrayList<String> nombres = new ArrayList<String>();
        int inombre = c.getColumnIndex(ID_PERSONA);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            nombres.add(c.getString(inombre));
        }
        return nombres;
    }
    public ArrayList<String> SelectTelefonos(){
        String [] columnas = new String[]{ID_TELEFONO};
        Cursor c = DB.query( N_TABLA, columnas, null, null, null, null,null);

        ArrayList<String> telefonos = new ArrayList<String>();
        int itelefono = c.getColumnIndex(ID_TELEFONO);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            telefonos.add(c.getString(itelefono));
        }
        return telefonos;
    }
    public ArrayList<String> SelectidDB(){
        String [] columnas = new String[]{ID_FILA};
        Cursor c = DB.query( N_TABLA, columnas, null, null, null, null,null);

        ArrayList<String> id_db = new ArrayList<String>();
        int iddb = c.getColumnIndex(ID_FILA);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            id_db.add(c.getString(iddb));
        }
        return id_db;
    }

    public void eliminarContacto(String id ){
        DB.delete(N_TABLA, ID_FILA + " = " + id, null);
    }
}
