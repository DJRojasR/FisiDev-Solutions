/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

import java.util.Date;
import java.util.Scanner;

/**
 *
 *
 */
public class Compra implements Identificable { // Los objetos van en una cola
    private static Scanner scanner = new Scanner(System.in);
    private String idCompra;
    private Date fecha;
    private String proveedor;
    private ListaSimple<Item> items;
    private double total = 0;
    private static int id = 0;
    
    public Compra(String p){
        id +=1;
        this.idCompra = Integer.toString(id);
        this.fecha = new Date();
        this.proveedor = p;
    }
    
    
    @Override
    public String getCodigo(){
        return this.idCompra;
    }
    
    public Date getFecha(){
        return this.fecha;
    }
    
    public String getProveedor(){
        return this.proveedor;
    }
    public ListaSimple<Item> getItems(){
        return this.items;
    }

    public void generarItems(Inventario inv){
        char opcion;
        String rs, nombre;
        int cantidad = -1;
        double preciodeCompra;
        ListaSimple<Item> li = new ListaSimple<>();
        
        int cont = 1;
        do{
            do {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El campo no puede estar vacío. Vuelva a intentar");
            }
            } while (nombre.isEmpty());
            
            do {
            System.out.print("Registro Sanitario: ");
            rs = scanner.nextLine().trim();
            if (rs.isEmpty()) {
                System.out.println("El campo no puede estar vacío. Vuelva a intentar");
            }
            } while (rs.isEmpty());
            
            while (true) {    
            System.out.print("Precio de compra por unidad en soles: ");
            if (scanner.hasNextDouble()) {
                preciodeCompra = scanner.nextDouble();
                scanner.nextLine();
                if (preciodeCompra > 0) {
                    break;
                } else {
                    System.out.println("El precio debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                scanner.next();
                }
            }
            
            while (true) {
            System.out.print("Stock: ");
            if (scanner.hasNextInt()) {
                cantidad = scanner.nextInt();
                scanner.nextLine();
                if (cantidad > 0) {
                    break;
                } else {
                    System.out.println("El Stock debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                scanner.next();
                }
            }
            
            Producto prod = new Producto(nombre, rs, cantidad);
            Item i = new Item(prod.getRegistroSanitario(), prod.getNombre(), cantidad, prod.getPrecioVenta());
            if(li.estaVacia()) li.insertarAlInicio(i);
            else{
                li.insertarAlFinal(i);
            }
            
            System.out.print("Desea continuar agregando items? (y/n): ");opcion = scanner.nextLine().toLowerCase().charAt(0);
            cont++;
            this.total += preciodeCompra * cantidad;
        }while(opcion == 'y');
        this.items = li;
    }
    
    public void procesarCompra(Inventario inv){
        Nodo <Item> actual = items.getInicio();
        while(actual != null){
            //Podria imprimirse la compra aqui
            
            Nodo <Producto> productoActual = inv.getProductos().buscarElemento(actual.getDato().getCodigo());
            
            if(productoActual != null){ //Existe producto en el inventario, se suma al stock
                //Si el producto tenia stock agotado habra estado en la cola de productos sin stock, como se le va a añadir stock se le retira de la cola
                if(productoActual.getDato().getStock() == 0){
                    inv.getProductosSinStock().retirarDato(productoActual.getDato().getCodigo());
                }
                
                productoActual.getDato().setStock(productoActual.getDato().getStock() + 
                                                actual.getDato().getCantidad());
                
            }
            else{
                
                Producto prod = new Producto(actual.getDato().getNombre(),
                                            actual.getDato().getRegistroSanitario(),
                                            actual.getDato().getCantidad());
                inv.getProductos().insertarNodo(prod); //Se añade al arbol el nuevo producto
                
                inv.getProductosConInformacionFaltante().encolar(prod);
                //insertar los productos tambien a una cola para rellenar la informacion faltante
            }
            actual = actual.getSiguiente();
        }
        //Si compra exitosa
        if(inv.getRegistroCompras().estaVacia()) inv.getRegistroCompras().insertarAlInicio(this);
        else{
            inv.getRegistroCompras().insertarAlFinal(this);
        }
        System.out.println("Compra realizada");
    }
    
    @Override
    public String toString(){
        return """
               idCompra: """ + this.idCompra +
                "\nProveedor: " + this.proveedor +
                "\nFecha: " + this.fecha +
                "\nTotal: " + this.total;
    }
}
