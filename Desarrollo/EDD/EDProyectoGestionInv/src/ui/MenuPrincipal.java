package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import misClases.*;

/**
 * Interfaz gráfica principal para el sistema de inventario
 */
public class MenuPrincipal extends JFrame {
    private Inventario inventario;
    
    // Componentes de la interfaz
    private JButton btnAgregarProducto;
    private JButton btnBuscarProducto;
    private JButton btnMostrarInventario;
    private JButton btnRealizarVenta;
    private JButton btnRealizarCompra;
    private JButton btnMostrarVentas;
    private JButton btnMostrarCompras;
    private JButton btnProductosPendientes;
    private JButton btnProductosSinStock;
    private JButton btnSalir;
    
    public MenuPrincipal() {
        // Inicializar inventario
        inventario = new Inventario();
        
        // Configurar ventana principal
        setTitle("Sistema de Gestión de Inventario");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel principal con GridLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("--- Menú de Inventario ---", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Crear botones con listeners
        btnAgregarProducto = createButton("1. Agregar producto", e -> abrirAgregarProducto());
        btnBuscarProducto = createButton("2. Buscar producto por RS", e -> abrirBuscarProducto());
        btnMostrarInventario = createButton("3. Mostrar inventario en forma de árbol", e -> mostrarInventario());
        btnRealizarVenta = createButton("4. Realizar Venta", e -> abrirRealizarVenta());
        btnRealizarCompra = createButton("5. Realizar Compra", e -> abrirRealizarCompra());
        btnMostrarVentas = createButton("6. Mostrar Ventas", e -> mostrarVentas());
        btnMostrarCompras = createButton("7. Mostrar Compras", e -> mostrarCompras());
        btnSalir = createButton("10. Salir", e -> System.exit(0));
        
        // Añadir botones al panel
        buttonPanel.add(btnAgregarProducto);
        buttonPanel.add(btnBuscarProducto);
        buttonPanel.add(btnMostrarInventario);
        buttonPanel.add(btnRealizarVenta);
        buttonPanel.add(btnRealizarCompra);
        buttonPanel.add(btnMostrarVentas);
        buttonPanel.add(btnMostrarCompras);
  
        buttonPanel.add(btnSalir);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
    }
    
    // Método para crear un botón con un ActionListener
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(listener);
        return button;
    }
    
    // Métodos para abrir diferentes ventanas o realizar acciones
    private void abrirAgregarProducto() {
        AgregarProductoUI agregarProducto = new AgregarProductoUI(inventario);
        agregarProducto.setVisible(true);
    }
    
    private void abrirBuscarProducto() {
        BuscarProductoUI buscarProducto = new BuscarProductoUI(inventario);
        buscarProducto.setVisible(true);
    }
    
    private void mostrarInventario() {
        MostrarInventario mostrarInventario = new MostrarInventario(inventario);
        mostrarInventario.setVisible(true);
    }
    
    private void abrirRealizarVenta() {
        RealizarVenta realizarVenta = new RealizarVenta(inventario);
        realizarVenta.setVisible(true);
    }
    
    private void abrirRealizarCompra() {
        RealizarCompraUI realizarCompra = new RealizarCompraUI(inventario);
        realizarCompra.setVisible(true);
    }
    
    private void mostrarVentas() {
        MostrarVentasUI mostrarVentas = new MostrarVentasUI(inventario);
        mostrarVentas.setVisible(true);
    }
    
    private void mostrarCompras() {
        MostrarComprasUI mostrarCompras = new MostrarComprasUI(inventario);
        mostrarCompras.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.setVisible(true);
        });
    }
}