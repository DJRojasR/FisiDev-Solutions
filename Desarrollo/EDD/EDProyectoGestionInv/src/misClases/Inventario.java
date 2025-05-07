/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 * 
 */
public class Inventario { // Registro de productos, ventas y compras en distintas estructuras
    private final Arbol productos = new Arbol();
    private final Cola<Producto> productosConInformacionFaltante = new Cola<>();
    private final Cola<Producto> productosSinStock = new Cola<>();
    private final ListaDobleEnlazada<Venta> registroVentas = new ListaDobleEnlazada<>();
    private final ListaDobleEnlazada<Compra> registroCompras = new ListaDobleEnlazada<>();

    public Arbol getProductos() {
        return productos;
    }
    
    public Cola getProductosConInformacionFaltante(){
        return productosConInformacionFaltante;
    }

    public Cola getProductosSinStock() {
        return productosSinStock;
    }

    public ListaDobleEnlazada getRegistroVentas() {
        return registroVentas;
    }

    public ListaDobleEnlazada getRegistroCompras() {
        return registroCompras;
    }
    
}
