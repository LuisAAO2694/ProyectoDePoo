package com.iteso.proyectoFinal.conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

//Creamos esta clase que maneja la conexion a la base de datos
public class conexionBD
{
    //Bien aqui solo declaramos nuestra url con la que se conecta a la base de datos
    //Usamos el jdbc que es la libreria para la conexion.
    /*
        Declaramos nuestro user que por defecto nos viene como root
        Y ademas la pass que es la password no tiene porque noo tenenmos asiganda a nuestro
        gestor de base de datos
     */
    private static final String URL = "jdbc:mysql://localhost/gestorEscolar";
    private static final String USER = "root";
    private static final String PASS = "";

    //Aqui es nuestro metodo estatico, que establece y nos devueleve solamente un aviso
    //sobre si la base de datos se conecto correcctamente
    //Ademas usamos el patrón Singleton para asegurar una única instancia de conexión
    //Singelton que solo tenga una instacia
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
