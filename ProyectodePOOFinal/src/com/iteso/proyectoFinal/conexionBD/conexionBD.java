package com.iteso.proyectoFinal.conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexionBD
{
    private static final String URL = "jdbc:mysql://localhost/gestorEscolar";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexion exitosa a MySQL.");
        }
        catch (Exception e)
        {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }
}
