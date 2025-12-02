package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Calificacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Maneja las calificaciones de alumnos en materias específicas por semestre
public class CalificacionDAO
{
    public boolean agregar(Calificacion c)
    {
        /**
         * Agrega una nueva calificación a la base de datos.
         * Relaciona un alumno con una materia en un semestre específico.
         *
         * @param c Objeto Calificacion con los datos a insertar
         * @return true si se insertó correctamente, false si hubo error
         */
        String sql = "INSERT INTO calificacion (id_alumno, id_materia, id_semestre, valorcalificacion, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, c.getIdAlumno());
            ps.setInt(2, c.getIdMateria());
            ps.setInt(3, c.getIdSemestre());
            ps.setDouble(4, c.getValor());
            ps.setString(5, c.getTipo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Actualiza una calificación existente en la base de datos.
     * Modifica todos los campos de una calificación identificada por su ID.
     *
     * @param c Objeto Calificacion con los nuevos datos (debe incluir ID)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Calificacion c) {
        String sql = "UPDATE calificacion SET id_alumno=?, id_materia=?, id_semestre=?, valorcalificacion=?, tipo=? WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, c.getIdAlumno());
            ps.setInt(2, c.getIdMateria());
            ps.setInt(3, c.getIdSemestre());
            ps.setDouble(4, c.getValor());
            ps.setString(5, c.getTipo());
            ps.setInt(6, c.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Elimina una calificación de la base de datos por su ID.
     *
     * @param id ID de la calificación a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM calificacion WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Obtiene una calificación específica por su ID.
     *
     * @param id ID de la calificación a buscar
     * @return Objeto Calificacion si se encuentra, null si no existe o hay error
     */
    public Calificacion obtenerPorId(int id) {
        String sql = "SELECT * FROM calificacion WHERE id_calificacion=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return new Calificacion(rs.getInt("id_calificacion"), rs.getInt("id_alumno"), rs.getInt("id_materia"),
                        rs.getInt("id_semestre"), rs.getDouble("valorcalificacion"), rs.getString("tipo"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Obtiene todas las calificaciones de la base de datos.
     *
     * @return Lista de todos los objetos Calificacion, lista vacía si no hay registros o hay error
     */
    public List<Calificacion> obtenerTodos()
    {
        List<Calificacion> list = new ArrayList<>();
        String sql = "SELECT * FROM calificacion";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                list.add(new Calificacion(rs.getInt("id_calificacion"), rs.getInt("id_alumno"), rs.getInt("id_materia"),
                        rs.getInt("id_semestre"), rs.getDouble("valorcalificacion"), rs.getString("tipo")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
