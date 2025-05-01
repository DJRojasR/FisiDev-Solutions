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

public class MostrarComprasUI extends JFrame {
    private Inventario inventario;
    private JTextArea txtAreaCompras;
    private JButton btnCerrar;
    
    public MostrarComprasUI(Inventario inventario) {
        this.inventario = inventario;
        
        // Configurar ventana
        setTitle("Registro de Compras");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Área de texto para mostrar las compras
        txtAreaCompras = new JTextArea();
        txtAreaCompras.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaCompras);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPanel.add(btnCerrar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Añadir panel principal a la ventana
        add(mainPanel);
        
        // Cargar y mostrar el registro de compras
        cargarCompras();
    }
    
    private void cargarCompras() {
        try {
            ListaDobleEnlazada<Compra> comprasRegistro = inventario.getRegistroCompras();
            
            if (comprasRegistro.estaVacia()) {
                txtAreaCompras.setText("No hay compras registradas.");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("===== REGISTRO DE COMPRAS =====\n\n");
            
            Nodo<Compra> compraActual = comprasRegistro.getInicio();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            while (compraActual != null) {
                Compra compra = compraActual.getDato();
                
                sb.append("ID Compra: ").append(compra.getCodigo()).append("\n");
                sb.append("Fecha: ").append(dateFormat.format(compra.getFecha())).append("\n");
                sb.append("Proveedor: ").append(compra.getProveedor()).append("\n");
                
                // Mostrar items
                sb.append("Items:\n");
                
                ListaSimple<Item> items = compra.getItems();
                if (items != null && !items.estaVacia()) {
                    Nodo<Item> itemActual = items.getInicio();
                    int contador = 1;
                    
                    while (itemActual != null) {
                        Item item = itemActual.getDato();
                        sb.append("   ").append(contador).append(". ");
                        sb.append(item.getNombre()).append(" - ");
                        sb.append("RS: ").append(item.getRegistroSanitario()).append(" - ");
                        sb.append("Cantidad: ").append(item.getCantidad()).append(" - ");
                        sb.append("Subtotal: S/.").append(item.calcularSubTotal()).append("\n");
                        
                        itemActual = itemActual.getSiguiente();
                        contador++;
                    }
                } else {
                    sb.append("   No hay items registrados\n");
                }
                
                // Calcular el total de la compra manualmente
                double total = 0;
                if (items != null && !items.estaVacia()) {
                    Nodo<Item> itemActual = items.getInicio();
                    while (itemActual != null) {
                        total += itemActual.getDato().calcularSubTotal();
                        itemActual = itemActual.getSiguiente();
                    }
                }
                
                sb.append("Total de la compra: S/.").append(total).append("\n");
                sb.append("-------------------------------------\n\n");
                
                compraActual = compraActual.getSiguiente();
            }
            
            txtAreaCompras.setText(sb.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}