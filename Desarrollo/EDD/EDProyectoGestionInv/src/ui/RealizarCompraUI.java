package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import misClases.*;

public class RealizarCompraUI extends JFrame {
    private Inventario inventario;
    private JTextField txtProveedor;
    private JTextField txtNombre;
    private JTextField txtRegistroSanitario;
    private JTextField txtPrecioCompra;
    private JTextField txtCantidad;
    private JTextArea txtAreaItems;
    private JButton btnAgregar;
    private JButton btnFinalizar;
    private JButton btnCancelar;
    
    private ListaSimple<Item> items;
    private int itemCounter = 0;
    
    public RealizarCompraUI(Inventario inventario) {
        this.inventario = inventario;
        items = new ListaSimple<>();
        
        // Configurar ventana
        setTitle("Realizar Compra");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de proveedor
        JPanel proveedorPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        proveedorPanel.add(new JLabel("Proveedor:"));
        txtProveedor = new JTextField();
        proveedorPanel.add(txtProveedor);
        
        mainPanel.add(proveedorPanel, BorderLayout.NORTH);
        
        // Panel de formulario de items
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        
        formPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);
        
        formPanel.add(new JLabel("Registro Sanitario:"));
        txtRegistroSanitario = new JTextField();
        formPanel.add(txtRegistroSanitario);
        
        formPanel.add(new JLabel("Precio de Compra (S/.):"));
        txtPrecioCompra = new JTextField();
        formPanel.add(txtPrecioCompra);
        
        formPanel.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        formPanel.add(txtCantidad);
        
        // Panel para items y formulario
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        
        // Panel de items
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Items de la Compra"));
        
        txtAreaItems = new JTextArea();
        txtAreaItems.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaItems);
        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        
        centerPanel.add(itemsPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnAgregar = new JButton("Agregar Item");
        btnAgregar.addActionListener(e -> agregarItem());
        
        btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.addActionListener(e -> finalizarCompra());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnFinalizar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
    }
    
    private void agregarItem() {
        try {
            // Validar campos
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (txtRegistroSanitario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el registro sanitario del producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double precioCompra;
            try {
                precioCompra = Double.parseDouble(txtPrecioCompra.getText().trim());
                if (precioCompra <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido para el precio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int cantidad;
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido para la cantidad", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear item
            String nombre = txtNombre.getText().trim();
            String registroSanitario = txtRegistroSanitario.getText().trim();
            
            Item item = new Item(registroSanitario, nombre, cantidad, precioCompra);
            
            // Agregar a la lista de items
            if (items.estaVacia()) {
                items.insertarAlInicio(item);
            } else {
                items.insertarAlFinal(item);
            }
            
            // Actualizar área de texto
            itemCounter++;
            txtAreaItems.append("Item #" + itemCounter + ": " + nombre + 
                               " - RS: " + registroSanitario +
                               " - Cantidad: " + cantidad + 
                               " - Precio: S/." + precioCompra + 
                               " - Subtotal: S/." + item.calcularSubTotal() + "\n");
            
            // Limpiar campos para nuevo item
            txtNombre.setText("");
            txtRegistroSanitario.setText("");
            txtPrecioCompra.setText("");
            txtCantidad.setText("");
            txtNombre.requestFocus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void finalizarCompra() {
        try {
            // Validar proveedor
            if (txtProveedor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del proveedor", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que haya items
            if (items.estaVacia()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un item a la compra", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear compra
            Compra compra = new Compra(txtProveedor.getText().trim());
            
            // Asignar items a la compra (simulando el comportamiento de generarItems pero usando nuestra lista)
            java.lang.reflect.Field itemsField = Compra.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            itemsField.set(compra, items);
            
            // Procesar compra (actualiza inventario e inserta en registro de compras)
            compra.procesarCompra(inventario);
            
            JOptionPane.showMessageDialog(this, "Compra registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al finalizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}