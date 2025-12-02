package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Clase que implementa las operaciones CRUD para la tabla semestre
public class SemestreDAO
{
    /**
     * Agrega un nuevo semestre a la base de datos.
     *
     * @param s Objeto Semestre con los datos a insertar (nombre y periodo)
     * @return true si se insertó correctamente, false si hubo error
     */
    public boolean agregar(Semestre s) {
        String sql = "INSERT INTO semestre (nombre, periodo) VALUES (?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getPeriodo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Actualiza un semestre existente en la base de datos.
     *
     * @param s Objeto Semestre con los nuevos datos (debe incluir ID, nombre y periodo)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Semestre s) {
        String sql = "UPDATE semestre SET nombre=?, periodo=? WHERE id_semestre=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getPeriodo());
            ps.setInt(3, s.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Elimina un semestre de la base de datos por su ID.
     * IMPORTANTE: Verificar que no haya calificaciones asociadas antes de eliminar.
     *
     * @param id ID del semestre a eliminar
     * @return true si se eliminó correctamente, false si hubo error o restricción de integridad
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM semestre WHERE id_semestre=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Obtiene todos los semestres de la base de datos.
     * Los ordena primero por periodo y luego por nombre.
     *
     * @return Lista de todos los objetos Semestre, lista vacía si no hay registros o hay error
     */
    public List<Semestre> obtenerTodos() {
        List<Semestre> list = new ArrayList<>();
        String sql = "SELECT * FROM semestre ORDER BY periodo, nombre";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) list.add(new Semestre(rs.getInt("id_semestre"), rs.getString("nombre"), rs.getString("periodo")));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
