package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Clase que implementa las operaciones CRUD para la tabla materia
public class MateriaDAO
{
    /**
     * Agrega una nueva materia a la base de datos.
     *
     * @param m Objeto Materia con los datos a insertar (solo necesita nombre)
     * @return true si se insertó correctamente, false si hubo error
     */
    public boolean agregar(Materia m) {
        String sql = "INSERT INTO materia (nombre) VALUES (?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, m.getNombre());

            //executeUpdate() retorna número de filas afectadas
            //> 0 significa inserción exitosa
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Actualiza una materia existente en la base de datos.
     *
     * @param m Objeto Materia con los nuevos datos (debe incluir ID y nombre)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Materia m) {
        String sql = "UPDATE materia SET nombre=? WHERE id_materia=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Elimina una materia de la base de datos por su ID.
     * IMPORTANTE: Verificar que no haya calificaciones asociadas antes de eliminar.
     *
     * @param id ID de la materia a eliminar
     * @return true si se eliminó correctamente, false si hubo error o restricción de integridad
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM materia WHERE id_materia=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Obtiene todas las materias de la base de datos.
     * Las ordena alfabéticamente por nombre.
     *
     * @return Lista de todos los objetos Materia, lista vacía si no hay registros o hay error
     */
    public List<Materia> obtenerTodos() {
        List<Materia> list = new ArrayList<>();
        String sql = "SELECT * FROM materia ORDER BY nombre";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) list.add(new Materia(rs.getInt("id_materia"),
                    rs.getString("nombre")));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
