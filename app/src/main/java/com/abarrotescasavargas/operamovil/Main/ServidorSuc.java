package com.abarrotescasavargas.operamovil.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ServidorSuc {

    public String IP = "";
    private Connection cn;
    public String Base = "";
    public String PassW="";

    ////////////////// conectarse a suc //////////////////
    public ServidorSuc(Context context, boolean suc){
        Srv_Sucursal Srv_Suc= new Srv_Sucursal(context);
        SQLiteDatabase db = Srv_Suc.getWritableDatabase();
        Cursor Servidor = db.rawQuery("SELECT IP,Base,PassW FROM Configuracion",null);
        Servidor.moveToNext();
        IP = Servidor.getString(0);
        Base = Servidor.getString(1).replace("PV_","SUC");
        //Base = "SUCZARAGOZA";
        //Base = "SUCVIAMORELOS";
          //Base = "SUCXALOSTOC";
        PassW=Servidor.getString(2);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //cn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ";databaseName=" + Servidor.getString(1) + ";user=sa;password=" + Servidor.getString(2) + ";");
            cn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ";databaseName=" + Base + ";user=sa;password=" + Servidor.getString(2) + ";");
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {

            String a = e.getMessage();
        }
        db.close();
    }
    ////////////////// conectarse a suc //////////////////


    public Integer ejecuta(String query){
        Integer Res = -1;
        try{
            Statement st = cn.createStatement();
            Res = st.executeUpdate(query);
        }catch(SQLException e){

        }
        return Res;
    }

    public java.sql.ResultSet tabla(String query, Boolean Leer){
        try{
            Statement st = cn.createStatement();
            java.sql.ResultSet tb = st.executeQuery(query);
            if(Leer) tb.next();
            return tb;
        }catch (SQLException e){
            return null;
        }
    }

    public  Connection Con() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection cn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ";databaseName=" + Base + ";user=sa;password=" + PassW + ";");
            //Connection cn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.14;databaseName=IMG_ECATEPEC;user=sa;password=wincaja;");
            //Connection cn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.49.1;databaseName=CORPORATIVO;user=sa;password=82007Riwuap85;");
            return cn;
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            return null;
        }
    }

    public PreparedStatement PSte(String query) throws SQLException {
        return Con().prepareStatement(query);
    }
}
