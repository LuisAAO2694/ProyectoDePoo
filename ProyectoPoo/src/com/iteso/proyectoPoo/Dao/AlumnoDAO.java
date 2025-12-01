package com.iteso.proyectoPoo.Dao;

import com.iteso.proyectoPoo.conexionBD.ConexionDB;
import com.iteso.proyectoPoo.Modelos.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    // CREATE
    public boolean agregarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno (nombre, apellido, expediente) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellido());
            stmt.setString(3, alumno.getExpediente());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al agregar alumno: " + e.getMessage());
            return false;
        }
    }

    // READ - obtener uno
    public Alumno obtenerAlumno(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno = ?";
        Alumno alumno = null;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alumno = new Alumno(
                        rs.getInt("id_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("expediente")
                );
            }

        } catch (Exception e) {
            System.out.println("Error al obtener alumno: " + e.getMessage());
        }

        return alumno;
    }

    // READ - obtener todos
    public List<Alumno> obtenerTodos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getInt("id_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("expediente")
                );
                lista.add(alumno);
            }

        } catch (Exception e) {
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE
    public boolean actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE alumno SET nombre=?, apellido=?, expediente=? WHERE id_alumno=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellido());
            stmt.setString(3, alumno.getExpediente());
            stmt.setInt(4, alumno.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar alumno: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar alumno: " + e.getMessage());
            return false;
        }
    }
}