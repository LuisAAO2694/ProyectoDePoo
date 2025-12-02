package com.iteso.proyectoFinal.utils;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;

/*
 * Exporta el contenido de una JTable a un archivo CSV compatible con Excel.
 *
 * @param table Tabla Swing que contiene los datos a exportar
 * @param file  Archivo de destino donde se guardarán los datos
 * @return true si la exportación fue exitosa, false si hubo error
 */
public class ExportUtil
{
    //Exporta JTable a CSV (Excel compatible)
    public static boolean exportTableToCSV(JTable table, File file)
    {
        try (Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            //Obtiene el modelo de datos de la tabla
            TableModel model = table.getModel();

            // ================================================
            // ESCRIBIR ENCABEZADOS DE COLUMNAS
            // ================================================
            //Headers - iteramos
            for (int i = 0; i < model.getColumnCount(); i++)
            {
                // Escribe el nombre de la columna
                fw.write(model.getColumnName(i));
                //Agrega coma separadora, excepto después de la última columna
                if (i < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");

            // ================================================
            // ESCRIBIR DATOS DE LAS FILAS
            // ================================================
            //Filas
            for (int r = 0; r < model.getRowCount(); r++)
            {
                //Itera sobre todas las columnas de la fila actual
                for (int c = 0; c < model.getColumnCount(); c++)
                {
                    //Obtiene el valor de la celda
                    Object val = model.getValueAt(r, c);
                    // Convierte a String, maneja valores null
                    String cell = val == null ? "" : val.toString().replaceAll("\"", "\"\"");

                    // ================================================
                    // CARACTERES ESPECIALES PARA CSV
                    // ================================================
                    // Determinar si la celda necesita comillas alrededor
                    if (cell.contains(",") || cell.contains("\n"))
                    {
                        fw.write("\"" + cell + "\"");
                    }
                    else
                    {
                        fw.write(cell);
                    }
                    if (c < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }
            //Forzar escritura de cualquier dato pendiente en el buffer
            fw.flush();
            return true;
        }
        catch (Exception e)
        {

            e.printStackTrace();
            return false;
        }
    }
}
