package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import misClases.*;

public class RealizarVenta extends JFrame {
    private Inventario inventario;
    private JTextField txtClienteID;
    private JTextField txtRegistroSanitario;
    private JTextField txtCantidad;
    private JTextArea txtAreaItems;
    private JButton btnAgregar;
    private JButton btnFinalizar;
    private JButton btnCancelar;
    
    private ListaSimple<Item> items;
    private int itemCounter = 0;
    
    public RealizarVenta(Inventario inventario) {
        this.inventario = inventario;
        items = new ListaSimple<>();
        
        // Configurar ventana
        setTitle("Realizar Venta");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        formPanel.add(new JLabel("DNI/RUC Cliente:"));
        txtClienteID = new JTextField();
        formPanel.add(txtClienteID);
        
        formPanel.add(new JLabel("Registro Sanitario:"));
        txtRegistroSanitario = new JTextField();
        formPanel.add(txtRegistroSanitario);
        
        formPanel.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        formPanel.add(txtCantidad);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Panel de items
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Items de la Venta"));
        
        txtAreaItems = new JTextArea();
        txtAreaItems.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaItems);
        itemsPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(itemsPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnAgregar = new JButton("Agregar Item");
        btnAgregar.addActionListener(e -> agregarItem());
        
        btnFinalizar = new JButton("Finalizar Venta");
        btnFinalizar.addActionListener(e -> finalizarVenta());
        
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
            if (txtRegistroSanitario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el registro sanitario del producto", "Error", JOptionPane.ERROR_MESSAGE);
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
            
            // Buscar producto en inventario
            String registroSanitario = txtRegistroSanitario.getText().trim();
            Nodo<Producto> nodoProducto = inventario.getProductos().buscarElemento(registroSanitario);
            
            if (nodoProducto == null) {
                JOptionPane.showMessageDialog(this, "El producto con registro sanitario " + registroSanitario + " no existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Producto producto = nodoProducto.getDato();
            
            // Verificar stock
            if (producto.getStock() < cantidad) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + producto.getStock(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear item
            Item item = new Item(registroSanitario, producto.getNombre(), cantidad, producto.getPrecioVenta());
            
            // Agregar a la lista de items
            if (items.estaVacia()) {
                items.insertarAlInicio(item);
            } else {
                items.insertarAlFinal(item);
            }
            
            // Actualizar área de texto
            itemCounter++;
            txtAreaItems.append("Item #" + itemCounter + ": " + producto.getNombre() + 
                               " - Cantidad: " + cantidad + 
                               " - Precio: S/." + producto.getPrecioVenta() + 
                               " - Subtotal: S/." + item.calcularSubTotal() + "\n");
            
            // Limpiar campos para nuevo item
            txtRegistroSanitario.setText("");
            txtCantidad.setText("");
            txtRegistroSanitario.requestFocus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void finalizarVenta() {
        try {
            // Validar cliente ID
            if (txtClienteID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el DNI/RUC del cliente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que haya items
            if (items.estaVacia()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un item a la venta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear venta
            Venta venta = new Venta(txtClienteID.getText().trim());
            
            // Asignar items a la venta (simulando el comportamiento de generarItems pero usando nuestra lista)
            java.lang.reflect.Field itemsField = Venta.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            itemsField.set(venta, items);
            
            // Procesar venta (actualiza stock e inserta en registro de ventas)
            venta.procesarVenta(inventario);
            
            JOptionPane.showMessageDialog(this, "Venta realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al finalizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}