package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Calificacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionDAO
{
    public boolean agregar(Calificacion c) {
        String sql = "INSERT INTO calificacion (id_alumno, id_materia, id_semestre, valorcalificacion, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdAlumno());
            ps.setInt(2, c.getIdMateria());
            ps.setInt(3, c.getIdSemestre());
            ps.setDouble(4, c.getValor());
            ps.setString(5, c.getTipo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Calificacion c) {
        String sql = "UPDATE calificacion SET id_alumno=?, id_materia=?, id_semestre=?, valorcalificacion=?, tipo=? WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdAlumno());
            ps.setInt(2, c.getIdMateria());
            ps.setInt(3, c.getIdSemestre());
            ps.setDouble(4, c.getValor());
            ps.setString(5, c.getTipo());
            ps.setInt(6, c.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM calificacion WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public Calificacion obtenerPorId(int id) {
        String sql = "SELECT * FROM calificacion WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Calificacion(rs.getInt("id_calificacion"), rs.getInt("id_alumno"), rs.getInt("id_materia"),
                        rs.getInt("id_semestre"), rs.getDouble("valorcalificacion"), rs.getString("tipo"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Calificacion> obtenerTodos() {
        List<Calificacion> list = new ArrayList<>();
        String sql = "SELECT * FROM calificacion";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Calificacion(rs.getInt("id_calificacion"), rs.getInt("id_alumno"), rs.getInt("id_materia"),
                        rs.getInt("id_semestre"), rs.getDouble("valorcalificacion"), rs.getString("tipo")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
