package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class main {
    public static Connection ConectarBD(String bd) {
        Connection conexion;
        String host = "jdbc:mysql://localhost/";
        String user = "root";
        String pass = "";

        System.out.println("Conectando...");

        try {
            conexion = DriverManager.getConnection(host+bd,user,pass);
            System.out.println("Conexion Exitosa!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return conexion;


    }

    public static void Desconexion(Connection cb){
        try {
            cb.close();
            System.out.println("Desconexion Exitosa!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Connection bd = ConectarBD("gestorEscolar");
        System.out.println("Consultas terminadas");
        Desconexion(bd);

    }
}
