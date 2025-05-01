/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import misClases.*;

public class MostrarVentasUI extends JFrame {
    private Inventario inventario;
    private JTextArea txtAreaVentas;
    private JButton btnCerrar;
    
    public MostrarVentasUI(Inventario inventario) {
        this.inventario = inventario;
        
        // Configurar ventana
        setTitle("Registro de Ventas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Área de texto para mostrar las ventas
        txtAreaVentas = new JTextArea();
        txtAreaVentas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaVentas);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPanel.add(btnCerrar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
        
        // Cargar y mostrar el registro de ventas
        cargarVentas();
    }
    
    private void cargarVentas() {
        try {
            ListaDobleEnlazada<Venta> ventasRegistro = inventario.getRegistroVentas();
            
            if (ventasRegistro.estaVacia()) {
                txtAreaVentas.setText("No hay ventas registradas.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("===== REGISTRO DE VENTAS =====\n\n");
            
            Nodo<Venta> ventaActual = ventasRegistro.getInicio();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            while (ventaActual != null) {
                Venta venta = ventaActual.getDato();
                
                sb.append("ID Venta: ").append(venta.getCodigo()).append("\n");
                sb.append("Fecha: ").append(dateFormat.format(venta.getFecha())).append("\n");
                sb.append("Cliente: ").append(venta.getClienteIdentificacion()).append("\n");
                
                // Mostrar items
                sb.append("Items:\n");
                
                ListaSimple<Item> items = venta.getItems();
                if (items != null && !items.estaVacia()) {
                    Nodo<Item> itemActual = items.getInicio();
                    int contador = 1;
                    
                    while (itemActual != null) {
                        Item item = itemActual.getDato();
                        sb.append("   ").append(contador).append(". ");
                        sb.append(item.getNombre()).append(" - ");
                        sb.append("Cantidad: ").append(item.getCantidad()).append(" - ");
                        sb.append("Subtotal: S/.").append(item.calcularSubTotal()).append("\n");
                        
                        itemActual = itemActual.getSiguiente();
                        contador++;
                    }
                } else {
                    sb.append("   No hay items registrados\n");
                }
                
                // Calcular el total de la venta manualmente
                double total = 0;
                if (items != null && !items.estaVacia()) {
                    Nodo<Item> itemActual = items.getInicio();
                    while (itemActual != null) {
                        total += itemActual.getDato().calcularSubTotal();
                        itemActual = itemActual.getSiguiente();
                    }
                }
                
                sb.append("Total de la venta: S/.").append(total).append("\n");
                sb.append("-------------------------------------\n\n");
                
                ventaActual = ventaActual.getSiguiente();
            }
            
            txtAreaVentas.setText(sb.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}