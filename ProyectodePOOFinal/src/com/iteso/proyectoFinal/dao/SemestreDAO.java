package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SemestreDAO
{
    public boolean agregar(Semestre s) {
        String sql = "INSERT INTO semestre (nombre, periodo) VALUES (?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getPeriodo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean actualizar(Semestre s) {
        String sql = "UPDATE semestre SET nombre=?, periodo=? WHERE id_semestre=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getPeriodo());
            ps.setInt(3, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM semestre WHERE id_semestre=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Semestre> obtenerTodos() {
        List<Semestre> list = new ArrayList<>();
        String sql = "SELECT * FROM semestre ORDER BY periodo, nombre";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Semestre(rs.getInt("id_semestre"), rs.getString("nombre"), rs.getString("periodo")));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
