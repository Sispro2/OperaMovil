package com.abarrotescasavargas.operamovil.Main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Srv_Sucursal extends SQLiteOpenHelper {

    private final Context context;

    public Srv_Sucursal(Context context){
        super(context, "Servidor.db", null, 23);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { }

}
