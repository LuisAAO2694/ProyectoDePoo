package com.iteso.proyectoPoo.Gestor;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iteso.proyectoPoo.conexionBD.ConexionDB;

public class Gestor {

    private DefaultTableModel modeloTabla;

    public Gestor(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }

    // ============================
    // CARGAR DATOS DESDE MYSQL
    // ============================
    public void cargarDesdeBD() {
        String sql = "SELECT * FROM alumno";

        modeloTabla.setRowCount(0); // <<< LIMPIA LA TABLA

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[] {
                        rs.getString("expediente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        //rs.getString("semestre"),
                        //rs.getInt("periodo"),
                        rs.getString("materia"),
                        rs.getString("tipo"),
                        rs.getDouble("valor")
                });
            }

        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }


    // ============================
    // GUARDAR / INSERTAR
    // ============================
    public void guardarEnBD(String expediente, String nombre, String apellido,
                            String semestre, int periodo, String materia,
                            String tipo, double valor) {

        String sql = "INSERT INTO alumno VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, expediente);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, semestre);
            ps.setInt(5, periodo);
            ps.setString(6, materia);
            ps.setString(7, tipo);
            ps.setDouble(8, valor);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // ============================
    // ELIMINAR
    // ============================
    public void eliminarEnBD(String expediente) {

        String sql = "DELETE FROM alumno WHERE expediente = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, expediente);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // ============================
    // ACTUALIZAR
    // ============================
    public void actualizarEnBD(String expediente, String nombre, String apellido,
                               String semestre, int periodo, String materia,
                               String tipo, double valor) {

        String sql = "UPDATE alumno SET nombre=?, apellido=?, semestre=?, periodo=?, materia=?, tipo=?, valor=? WHERE expediente=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, semestre);
            ps.setInt(4, periodo);
            ps.setString(5, materia);
            ps.setString(6, tipo);
            ps.setDouble(7, valor);
            ps.setString(8, expediente);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void buscarPorApellido(String apellido) {
        String sql = "SELECT * FROM alumno WHERE apellido LIKE ?";
        modeloTabla.setRowCount(0);

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + apellido + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getString("expediente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("semestre"),
                        rs.getInt("periodo"),
                        rs.getString("materia"),
                        rs.getString("tipo"),
                        rs.getDouble("valor")
                });
            }

        } catch (Exception e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }

}

//package com.iteso.proyectoPoo.Gestor;
//
//import com.iteso.proyectoPoo.Dao.AlumnoDAO;
//import com.iteso.proyectoPoo.Modelos.Alumno;
//
//import javax.swing.table.DefaultTableModel;
//
//public class Gestor
//{
//    public Gestor(DefaultTableModel modeloTabla) {
//
//    }
//
//    public static void main(String[] args) {
//
//        AlumnoDAO dao = new AlumnoDAO();
//
//        // Crear alumno
//        Alumno nuevo = new Alumno(0, "Mario", "Lopez", "X999");
//        dao.agregarAlumno(nuevo);
//
//        // Leer alumnos
//        dao.obtenerTodos().forEach(a ->
//                System.out.println(a.getId() + " - " + a.getNombre())
//        );
//    }
//
//    public void guardarEnBD(String expediente, String nombre, String apellido, String semestre, int periodo, String materia, String tipo, double valor) {
//    }
//}
