package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Aqui emepzamos con nuestros DAO:
/*
    Data Access Object (Objeto de Acceso a Datos)
    y es un patrón de diseño que separa la lógica de acceso a
    una fuente de datos (como una base de datos) del resto de la aplicación.
 */
//Realiza operaciones CRUD (Create, Read, Update, Delete) en la tabla 'alumno'.

public class AlumnoDAO
{

    /**
     * Agrega un nuevo alumno a la base de datos.
     * Utiliza INSERT con parámetros para prevenir SQL Injection.
     *
     * @param a Objeto Alumno con los datos a insertar
     * @return true si se insertó correctamente, false si hubo error
     */
    public boolean agregar(Alumno a) {
        String sql = "INSERT INTO alumno (nombre, apellido, expediente) VALUES (?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getExpediente());
            return ps.executeUpdate() > 0;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de un alumno existente en la base de datos.
     *
     * @param a Objeto Alumno con los nuevos datos (debe incluir el ID)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Alumno a) {
        String sql = "UPDATE alumno SET nombre=?, apellido=?, expediente=? WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getExpediente());
            ps.setInt(4, a.getId());
            return ps.executeUpdate() > 0;
        }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Elimina un alumno de la base de datos por su ID.
     *
     * @param id ID del alumno a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    /**
     * Obtiene un alumno específico por su ID.
     *
     * @param id ID del alumno a buscar
     * @return Objeto Alumno si se encuentra, null si no existe o hay error
     */
    public Alumno obtenerPorId(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno=?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Alumno(rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("expediente"));
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Obtiene todos los alumnos de la base de datos.
     * Los ordena por expediente (orden alfabético/numerico).
     *
     * @return Lista de todos los objetos Alumno, lista vacía si no hay registros o hay error
     */
    public List<Alumno> obtenerTodos() {
        List<Alumno> list = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY expediente";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                list.add(new Alumno(rs.getInt("id_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("expediente")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
