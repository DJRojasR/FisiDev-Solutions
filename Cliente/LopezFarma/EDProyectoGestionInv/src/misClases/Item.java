/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 * Un item corresponde a la informacion registrada en una venta o una compra acerca de un producto
 */
public class Item implements Identificable{
    private String registroSanitario;
    private String nombre;
    private int cantidad;
    private double precioUnitario;
    
    public Item(String rs, String n, int c, double pU){
        this.registroSanitario = rs;
        this.nombre = n;
        this.cantidad = c;
        this.precioUnitario = pU;
    }
    
    public double calcularSubTotal(){
        return cantidad*precioUnitario;
    }
    public String getRegistroSanitario(){
        return this.registroSanitario;
    }
    public String getNombre(){
        return this.nombre;
    }
    public int getCantidad(){
        return this.cantidad;
    }

    @Override
    public String getCodigo() {
        return this.registroSanitario;
    }
    @Override
    public String toString(){
        return """
               Nombre: """ + this.nombre +
                "\nRegistro sanitario: " + this.registroSanitario +
                "\nCantidad: " + this.cantidad +
                "\nPrecio unitario: " + this.precioUnitario;
    }
}
