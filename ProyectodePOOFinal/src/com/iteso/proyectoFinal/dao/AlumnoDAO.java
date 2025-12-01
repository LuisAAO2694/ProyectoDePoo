package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO
{
    public boolean agregar(Alumno a) {
        String sql = "INSERT INTO alumno (nombre, apellido, expediente) VALUES (?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getExpediente());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Alumno a) {
        String sql = "UPDATE alumno SET nombre=?, apellido=?, expediente=? WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getExpediente());
            ps.setInt(4, a.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public Alumno obtenerPorId(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Alumno(rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("expediente"));
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Alumno> obtenerTodos() {
        List<Alumno> list = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY expediente";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Alumno(rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("expediente")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
