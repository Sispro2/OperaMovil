package com.abarrotescasavargas.operamovil.Main.BaseDatos;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.abarrotescasavargas.operamovil.Main.Repository.SucursalRepository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class BD_SQL {

    public static Context context;
    private static String CreaCadenaConexion() {
        SucursalRepository sucursalRepository = new SucursalRepository(BD_SQL.context);
        //sucursalRepository.GetDetalleSucursal();
        Log.d("TODOS", "CreaCadenaConexion: solol para que distinga el cambio ");
        String cadCon = "jdbc:jtds:sqlserver://" + sucursalRepository.GetDetalleSucursal().getKS_IP() + ";databaseName="
                + sucursalRepository.GetDetalleSucursal().getKS_BASEDT()
                + ";user=sa;password="
                + sucursalRepository.GetDetalleSucursal().getKS_PABASE() + ";";
        return cadCon;
    }
    public static String ObtenerExistenciaTotalClave(String cveArticulo) {
        try {
            Connection cn = DriverManager.getConnection(CreaCadenaConexion());
            String storedProcedureCall = "{ call dbo.ObtenerExistenciaTotalClave(?) }";
            CallableStatement cS = cn.prepareCall(storedProcedureCall);
            cS.setString(1, cveArticulo);
            cS.execute();

            // Obtén el valor de la columna 'ExistenciaUnidadVenta' desde el procedimiento almacenado
            ResultSet resultSet = cS.getResultSet();
            if (resultSet.next()) {
                String existenciaUnidadVenta = resultSet.getString("ExistenciaUnidadVenta");
                resultSet.close();
                cS.close();
                cn.close();
                return existenciaUnidadVenta;
            }

            // Cierra los recursos y la conexión
            resultSet.close();
            cS.close();
            cn.close();
        } catch (Exception e) {
            Log.v("Error ObtenerExistenciaTotalClave", e.toString());
        }
        return null;
    }
    public static String getLineaNegocioClave(String claveArticulo,String campo) {
        String clave = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Con2();  // o usa Con1(context) si necesitas el contexto
            if (connection != null) {
                String storedProcedureCall = "{ call sp_GetLineaNegocio(?, ?) }";
                callableStatement = connection.prepareCall(storedProcedureCall);
                callableStatement.setString(1, claveArticulo);
                callableStatement.setString(2, campo);
                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    clave = resultSet.getString(campo);
                }
            }
        } catch (SQLException e) {
            Log.e("Error getLineaNegocioClave", e.toString());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e("Error closing resources", e.toString());
            }
        }
        return clave;
    }


    public static Statement Con1(Context context) {
        try {
            // Asigna el contexto a la variable estática 'context' en la clase BD_SQL
            BD_SQL.context = context;

            // Configura una política de Thread para permitir todas las operaciones (no recomendado en producción)
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Carga el controlador JDBC de la base de datos (JTDs en este caso)
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            try {
                // Crea una conexión a la base de datos utilizando la cadena de conexión generada por la función CreaCadenaConexion()
                Connection cn = DriverManager.getConnection(CreaCadenaConexion());

                return cn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection Con2() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            return DriverManager.getConnection(CreaCadenaConexion());
        } catch (SQLException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ResultSet tabla(String query, Boolean Leer, Context context) {
        try {
            // Obtiene una conexión a la base de datos utilizando la función Con1()
            ResultSet tb = Objects.requireNonNull(Con1(context)).executeQuery(query);

            // Si Leer es verdadero, avanza al primer registro del resultado (primer fila)
            if (Leer) tb.next();

            // Retorna el ResultSet que contiene los resultados de la consulta
            return tb;
        } catch (SQLException e) {
            // En caso de excepción SQL, retorna null (indicando un error)
            return null;
        }
    }

    public static int ejecuta(String query, Context context) {
        int Res = -1;
        try {
            Res = Objects.requireNonNull(Con1(context)).executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Res;
    }

    public static void envia(String Mensaje, Context context) {
        try {
            Objects.requireNonNull(Con1(context)).executeUpdate("INSERT INTO Mensajes (Mensaje) VALUES ('" + Mensaje + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement preparedStatement(String query) throws SQLException {
        try {
            return Objects.requireNonNull(Con2()).prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //return  Objects.requireNonNull(Con2()).prepareStatement(query);
    }


}
