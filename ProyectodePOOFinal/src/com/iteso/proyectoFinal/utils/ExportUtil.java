package com.iteso.proyectoFinal.utils;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ExportUtil
{
    // Exporta JTable a CSV (Excel compatible)
    public static boolean exportTableToCSV(JTable table, File file) {
        try (Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            TableModel model = table.getModel();
            // Headers
            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");
            // Filas
            for (int r = 0; r < model.getRowCount(); r++) {
                for (int c = 0; c < model.getColumnCount(); c++) {
                    Object val = model.getValueAt(r, c);
                    String cell = val == null ? "" : val.toString().replaceAll("\"", "\"\"");
                    if (cell.contains(",") || cell.contains("\n")) {
                        fw.write("\"" + cell + "\"");
                    } else {
                        fw.write(cell);
                    }
                    if (c < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }
            fw.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
