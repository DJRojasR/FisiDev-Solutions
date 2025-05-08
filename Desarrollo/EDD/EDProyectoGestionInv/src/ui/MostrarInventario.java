package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import misClases.*;

/**
 * Interfaz para mostrar el inventario en forma de árbol
 */
public class MostrarInventario extends JFrame {
    private Inventario inventario;
    
    // Componentes de la interfaz
    private JTextArea txtArbol;
    private JButton btnMostrar;
    private JButton btnCerrar;
    
    public MostrarInventario(Inventario inventario) {
        this.inventario = inventario;
        
        // Configurar ventana
        setTitle("Mostrar Inventario en Forma de Árbol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("Árbol de Productos", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel del árbol
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.setBorder(BorderFactory.createTitledBorder("Visualización del Árbol"));
        
        txtArbol = new JTextArea();
        txtArbol.setEditable(false);
        txtArbol.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada para mejor visualización
        JScrollPane scrollPane = new JScrollPane(txtArbol);
        treePanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(treePanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnMostrar = new JButton("Mostrar Árbol");
        btnMostrar.addActionListener(e -> mostrarArbol());
        buttonPanel.add(btnMostrar);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
    }
    
    // Método para mostrar el árbol de productos
    private void mostrarArbol() {
        try {
            // Redirigir la salida estándar para capturar el resultado de imprimirArbol
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            
            // Llamar al método imprimirArbol
            inventario.getProductos().imprimirArbol();
            
            // Restaurar la salida estándar
            System.out.flush();
            System.setOut(old);
            
            // Mostrar el resultado en el JTextArea
            txtArbol.setText(baos.toString());
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar el árbol: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}