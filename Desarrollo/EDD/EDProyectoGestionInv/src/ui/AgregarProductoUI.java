package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import misClases.*;

/**
 * Interfaz para agregar productos al inventario
 */
public class AgregarProductoUI extends JFrame {
    private Inventario inventario;
    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtRegistroSanitario;
    private JTextField txtFabricante;
    private JTextField txtCondicionVenta;
    private JTextField txtFormaFarmaceutica;
    private JTextField txtPrecioVenta;
    private JTextField txtFechaVencimiento;
    private JTextField txtDescripcion;
    private JTextField txtStock;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    public AgregarProductoUI(Inventario inventario) {
        this.inventario = inventario;
        
        // Configurar ventana
        setTitle("Agregar Producto");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel principal con GridBagLayout para mejor organización
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titleLabel = new JLabel("Agregar Nuevo Producto", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Campos del formulario
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Nombre:"), gbc);
        
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtNombre, gbc);
        
        // Registro Sanitario
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Registro Sanitario:"), gbc);
        
        txtRegistroSanitario = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtRegistroSanitario, gbc);
        
        // Fabricante
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Fabricante:"), gbc);
        
        txtFabricante = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtFabricante, gbc);
        
        // Condición de Venta
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Condición de Venta:"), gbc);
        
        txtCondicionVenta = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtCondicionVenta, gbc);
        
        // Forma Farmacéutica
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Forma Farmacéutica:"), gbc);
        
        txtFormaFarmaceutica = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtFormaFarmaceutica, gbc);
        
        // Precio de Venta
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Precio de Venta:"), gbc);
        
        txtPrecioVenta = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtPrecioVenta, gbc);
        
        // Fecha de Vencimiento
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Fecha de Vencimiento:"), gbc);
        
        txtFechaVencimiento = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtFechaVencimiento, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Descripción:"), gbc);
        
        txtDescripcion = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtDescripcion, gbc);
        
        // Stock
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(new JLabel("Stock:"), gbc);
        
        txtStock = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtStock, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarProducto());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
    }
    
    // Método para guardar el producto en el inventario
    private void guardarProducto() {
        try {
            // Validar que los campos obligatorios no estén vacíos
            if (txtNombre.getText().trim().isEmpty() || 
                txtRegistroSanitario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre y registro sanitario son obligatorios", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir precio de venta a double
            double precioVenta = 0;
            try {
                precioVenta = Double.parseDouble(txtPrecioVenta.getText());
                if (precioVenta <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser mayor que 0", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir stock a entero
            int stock = 0;
            try {
                stock = Integer.parseInt(txtStock.getText());
                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "El stock no puede ser negativo", 
                                                 "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El stock debe ser un número entero", 
                                             "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el nuevo producto
            Producto nuevoProducto = new Producto(
                txtNombre.getText(),
                txtRegistroSanitario.getText(),
                txtFabricante.getText(),
                txtCondicionVenta.getText(),
                txtFormaFarmaceutica.getText(),
                precioVenta,
                txtFechaVencimiento.getText(),
                txtDescripcion.getText(),
                stock
            );
            
            // Agregar el producto al árbol de productos
            inventario.getProductos().insertarNodo(nuevoProducto);
            
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente", 
                                         "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            limpiarCampos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para limpiar todos los campos del formulario
    private void limpiarCampos() {
        txtNombre.setText("");
        txtRegistroSanitario.setText("");
        txtFabricante.setText("");
        txtCondicionVenta.setText("");
        txtFormaFarmaceutica.setText("");
        txtPrecioVenta.setText("");
        txtFechaVencimiento.setText("");
        txtDescripcion.setText("");
        txtStock.setText("");
        txtNombre.requestFocus();
    }
}