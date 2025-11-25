package com.iteso.proyectoPoo.UI;

import com.iteso.proyectoPoo.Gestor;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Interfaz gráfica del Gestor Escolar - Estilo similar a la imagen de referencia
 */
public class GestorUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtExpediente, txtNombre, txtApellido, txtBuscarApellidos;
    private JComboBox<String> cbSemestre, cbMateria, cbTipo;
    private JSpinner spPeriodo, spValor;
    private JButton btnNuevo, btnActualizar, btnEliminar, btnCerrar;

    private Gestor gestor;

    // Colores inspirados en la imagen
    private final Color GRIS_FONDO = new Color(192, 192, 192);
    private final Color GRIS_MEDIO = new Color(220, 220, 220);
    private final Color VERDE_BOTON = new Color(101, 147, 204);
    private final Color ROJO_BOTON = new Color(119, 141, 184);
    private final Color AZUL_BOTON = new Color(60, 140, 180);
    private final Color NARANJA_HEADER = new Color(84, 100, 184);
    private final Color AMARILLO_ROW = new Color(189, 212, 239);

    public GestorUI() {
        setTitle("Alumnos Registrados");
        setSize(1500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(GRIS_FONDO);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL IZQUIERDO: TABLA =====
        JPanel panelTabla = crearPanelTabla();

        // ===== PANEL DERECHO: FORMULARIO Y BOTONES =====
        JPanel panelDerecho = crearPanelDerecho();

        add(panelTabla, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        // ===== Inicialización =====
        agregarDatosEjemplo();
        gestor = new Gestor(modeloTabla);
        configurarEventos();
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        // Título
        JLabel titulo = new JLabel("Alumnos Registrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(GRIS_MEDIO);
        titulo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        titulo.setPreferredSize(new Dimension(0, 35));

        // Tabla
        String[] columnas = {"Expediente", "Nombre", "Apellido", "Semestre", "Periodo", "Nombre del Curso", "Tipo", "Valor"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.setGridColor(Color.GRAY);
        tabla.setShowGrid(true);

        // Renderizador personalizado
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(211, 206, 211, 174));
                    c.setForeground(Color.BLACK);
                } else if (row % 2 == 0) {
                    c.setBackground(AMARILLO_ROW);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                setHorizontalAlignment(SwingConstants.CENTER); // CENTRADO
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return c;
            }
        });

        // Encabezado
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(NARANJA_HEADER);
        header.setForeground(Color.WHITE); // BLANCO
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        // Centrar encabezado
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        panel.setPreferredSize(new Dimension(300, 0));

        // ===== FORMULARIO =====
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(60, 80, 120)); // Azul oscuro como en la imagen
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // Inicializar componentes
        txtExpediente = new JTextField(15);
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        cbSemestre = new JComboBox<>(new String[]{"Enero–Mayo", "Agosto–Diciembre"});
        spPeriodo = new JSpinner(new SpinnerNumberModel(2025, 2000, 2100, 1));
        cbMateria = new JComboBox<>(new String[]{"Programación", "Matemáticas", "Bases de Datos"});
        cbTipo = new JComboBox<>(new String[]{"EXAMEN", "TAREA", "PROYECTO", "FINAL"});
        spValor = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.5));

        // Estilo de los spinners
        JComponent editorPeriodo = spPeriodo.getEditor();
        JSpinner.DefaultEditor spinnerEditorPeriodo = (JSpinner.DefaultEditor) editorPeriodo;
        spinnerEditorPeriodo.getTextField().setHorizontalAlignment(JTextField.LEFT);

        JComponent editorValor = spValor.getEditor();
        JSpinner.DefaultEditor spinnerEditorValor = (JSpinner.DefaultEditor) editorValor;
        spinnerEditorValor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        agregarCampoForm(panelForm, gbc, "Expediente:", txtExpediente, 0);
        agregarCampoForm(panelForm, gbc, "Nombre:", txtNombre, 1);
        agregarCampoForm(panelForm, gbc, "Apellido:", txtApellido, 2);
        agregarCampoForm(panelForm, gbc, "Semestre:", cbSemestre, 3);
        agregarCampoForm(panelForm, gbc, "Periodo:", spPeriodo, 4);
        agregarCampoForm(panelForm, gbc, "Materia:", cbMateria, 5);
        agregarCampoForm(panelForm, gbc, "Tipo:", cbTipo, 6);
        agregarCampoForm(panelForm, gbc, "Valor:", spValor, 7);

        // ===== BUSCAR ALUMNOS (CAJA SEPARADA) =====
        JPanel panelBuscar = new JPanel(new GridBagLayout());
        panelBuscar.setBackground(GRIS_MEDIO);
        panelBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbcBuscar = new GridBagConstraints();
        gbcBuscar.fill = GridBagConstraints.HORIZONTAL;
        gbcBuscar.insets = new Insets(5, 5, 5, 5);

        gbcBuscar.gridx = 0;
        gbcBuscar.gridy = 0;
        gbcBuscar.gridwidth = 2;
        JLabel lblBuscarTitulo = new JLabel(" Buscar Alumno");
        lblBuscarTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblBuscarTitulo.setForeground(Color.BLACK);
        panelBuscar.add(lblBuscarTitulo, gbcBuscar);

        gbcBuscar.gridy = 1;
        gbcBuscar.gridwidth = 1;
        gbcBuscar.insets = new Insets(10, 5, 5, 5);
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Arial", Font.BOLD, 11));
        lblApellidos.setForeground(Color.BLACK);
        panelBuscar.add(lblApellidos, gbcBuscar);

        gbcBuscar.gridx = 1;
        txtBuscarApellidos = new JTextField(15);
        txtBuscarApellidos.setPreferredSize(new Dimension(150, 22));
        panelBuscar.add(txtBuscarApellidos, gbcBuscar);

        // ===== BOTONES =====
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBackground(GRIS_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        btnNuevo = crearBotonCompacto(" Nuevo", VERDE_BOTON);
        btnActualizar = crearBotonCompacto(" Actualizar", AZUL_BOTON);
        btnEliminar = crearBotonCompacto(" Eliminar", ROJO_BOTON);
        btnCerrar = crearBotonCompacto(" Cerrar", new Color(156, 150, 150));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        // Agregar todo al panel derecho en orden
        panel.add(panelForm);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panelBuscar);  // La caja de búsqueda separada
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panelBotones);

        return panel;
    }

    private void agregarCampoForm(JPanel panel, GridBagConstraints gbc, String texto, JComponent campo, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        label.setForeground(Color.WHITE); // Texto blanco sobre fondo azul oscuro
        panel.add(label, gbc);

        gbc.gridx = 1;
        campo.setPreferredSize(new Dimension(150, 22));
        panel.add(campo, gbc);
    }

    private JButton crearBotonCompacto(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    private void configurarEventos() {
        btnNuevo.addActionListener(e -> {
            String expediente = txtExpediente.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String semestre = (String) cbSemestre.getSelectedItem();
            int periodo = (int) spPeriodo.getValue();
            String materia = (String) cbMateria.getSelectedItem();
            String tipo = (String) cbTipo.getSelectedItem();
            double valor = (double) spValor.getValue();

            if (expediente.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos Expediente y Nombre", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Agregar a la tabla: Expediente, Nombre, Apellido, Semestre, Periodo, Nombre del Curso, Tipo, Valor
            modeloTabla.addRow(new Object[]{expediente, nombre, apellido, semestre, periodo, materia, tipo, valor});
            limpiarCampos();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                modeloTabla.removeRow(fila);
            }
        });

        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para actualizar", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String expediente = txtExpediente.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String semestre = (String) cbSemestre.getSelectedItem();
            int periodo = (int) spPeriodo.getValue();
            String materia = (String) cbMateria.getSelectedItem();
            String tipo = (String) cbTipo.getSelectedItem();
            double valor = (double) spValor.getValue();

            modeloTabla.setValueAt(expediente, fila, 0);
            modeloTabla.setValueAt(nombre, fila, 1);
            modeloTabla.setValueAt(apellido, fila, 2);
            modeloTabla.setValueAt(semestre, fila, 3);
            modeloTabla.setValueAt(periodo, fila, 4);
            modeloTabla.setValueAt(materia, fila, 5);
            modeloTabla.setValueAt(tipo, fila, 6);
            modeloTabla.setValueAt(valor, fila, 7);
            limpiarCampos();
        });

        btnCerrar.addActionListener(e -> System.exit(0));

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtExpediente.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                cbSemestre.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
                cbMateria.setSelectedItem(modeloTabla.getValueAt(fila, 5).toString());
                cbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());

                try {
                    spPeriodo.setValue(Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString()));
                    spValor.setValue(Double.parseDouble(modeloTabla.getValueAt(fila, 7).toString()));
                } catch (NumberFormatException ex) {
                    // Ignorar si no se puede parsear
                }
            }
        });

        // Búsqueda en tiempo real por apellidos (columna 2)
        txtBuscarApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String busqueda = txtBuscarApellidos.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
                tabla.setRowSorter(sorter);
                if (busqueda.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda, 2)); // Columna 2 = Apellido
                }
            }
        });
    }

    private void limpiarCampos() {
        txtExpediente.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cbSemestre.setSelectedIndex(0);
        spPeriodo.setValue(2025);
        cbMateria.setSelectedIndex(0);
        cbTipo.setSelectedIndex(0);
        spValor.setValue(0.0);
    }

    private void agregarDatosEjemplo() {
        modeloTabla.addRow(new Object[]{"A001", "Luis", "Arriaga", "Enero–Mayo", 2025, "Programación", "EXAMEN", 95.0});
        modeloTabla.addRow(new Object[]{"A002", "Ana", "Torres", "Agosto–Diciembre", 2025, "Bases de Datos", "FINAL", 78.0});
        modeloTabla.addRow(new Object[]{"A003", "José", "Munõaca Manay", "Enero–Mayo", 2025, "Matemáticas", "TAREA", 85.5});
        modeloTabla.addRow(new Object[]{"A004", "María", "Díaz Mendoza", "Agosto–Diciembre", 2024, "Programación", "PROYECTO", 92.0});
        modeloTabla.addRow(new Object[]{"A005", "Carlos", "Flores Sanchez", "Enero–Mayo", 2025, "Bases de Datos", "EXAMEN", 88.0});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorUI().setVisible(true));
    }
}