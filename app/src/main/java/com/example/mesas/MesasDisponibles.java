package com.example.mesas;

import android.widget.Toast;

public class MesasDisponibles {
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "wlhkKlqhlA";
    private String pwdMySQL = "fuEhEabZuG";
    private String database = "wlhkKlqhlA";
    String driver = "com.mysql.jdbc.Driver";
    private String[] datosConexion = null;

    private String[] resultadoSQL = null;

    private String[] conexionBasedeDatos(String string) {
        return new String[]{
                serverIP,
                port,
                database,
                userMySQL,
                pwdMySQL,
                string
        };

    }

    public MesasDisponibles() {
        try {
            String[] datosConexion2 = conexionBasedeDatos("SELECT * FROM silla;");
            try {
                Class.forName(driver).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            resultadoSQL = new AsyncQuery().execute(datosConexion2).get();


            String resultadoConsulta = resultadoSQL[0];
            //Log.e("Resultado",resultadoConsulta);
            String[] datosMesas = resultadoConsulta.split("\n");
            for (String datosMesa : datosMesas) {

                String[] resultado = datosMesa.split(",");

                if (resultado[1].equals("1")) {
                }


            }
        } catch (Exception ex) {

        }
    }
}



