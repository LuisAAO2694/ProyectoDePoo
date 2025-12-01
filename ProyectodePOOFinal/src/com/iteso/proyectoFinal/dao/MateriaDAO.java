package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO
{
    public boolean agregar(Materia m) {
        String sql = "INSERT INTO materia (nombre) VALUES (?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Materia m) {
        String sql = "UPDATE materia SET nombre=? WHERE id_materia=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM materia WHERE id_materia=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Materia> obtenerTodos() {
        List<Materia> list = new ArrayList<>();
        String sql = "SELECT * FROM materia ORDER BY nombre";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Materia(rs.getInt("id_materia"), rs.getString("nombre")));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
