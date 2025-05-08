package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import misClases.*;

/**
 * Interfaz para buscar productos por Registro Sanitario
 */
public class BuscarProductoUI extends JFrame {
    private Inventario inventario;
    
    // Componentes de la interfaz
    private JTextField txtRegistroSanitario;
    private JButton btnBuscar;
    private JTextArea txtResultado;
    private JButton btnCerrar;
    
    public BuscarProductoUI(Inventario inventario) {
        this.inventario = inventario;
        
        // Configurar ventana
        setTitle("Buscar Producto por Registro Sanitario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("Buscar Producto", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        searchPanel.add(new JLabel("Registro Sanitario:"));
        
        txtRegistroSanitario = new JTextField(15);
        searchPanel.add(txtRegistroSanitario);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProducto());
        searchPanel.add(btnBuscar);
        
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        
        // Panel de resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Resultado"));
        
        txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        
        // Botón de cerrar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPanel.add(btnCerrar);
        
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
    }
    
    // Método para buscar un producto por su registro sanitario
    private void buscarProducto() {
        String registroSanitario = txtRegistroSanitario.getText().trim();
        
        if (registroSanitario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un registro sanitario", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Nodo<Producto> nodoProducto = inventario.getProductos().buscarElemento(registroSanitario);
            
            if (nodoProducto == null) {
                txtResultado.setText("No se encontró ningún producto con el registro sanitario: " + registroSanitario);
            } else {
                Producto producto = nodoProducto.getDato();
                txtResultado.setText(producto.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el producto: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}