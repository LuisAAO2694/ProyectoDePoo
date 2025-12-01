package com.iteso.proyectoPoo.Gestor;

import com.iteso.proyectoPoo.UI.GestorUI;

public class mainUI
{
    public static void main(String[] args) {
        // Asegura que la UI se ejecute en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GestorUI().setVisible(true);
        });
    }
}
