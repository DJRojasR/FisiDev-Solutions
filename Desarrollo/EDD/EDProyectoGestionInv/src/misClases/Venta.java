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
public class Venta implements Identificable{
    private static Scanner scanner = new Scanner(System.in);
    private String idVenta;
    private Date fecha;
    private String clienteIdentificacion;
    private ListaSimple<Item> items;
    private double total;
    private static int id = 0;
    
    public Venta(String cI){
        id += 1;
        this.idVenta = Integer.toString(id);
        this.clienteIdentificacion = cI;
        this.fecha = new Date();
    }
    public Date getFecha(){
        return this.fecha;
    }
    
    public String getClienteIdentificacion(){
        return this.clienteIdentificacion;
    }
    public ListaSimple<Item> getItems(){
        return this.items;
    }
    private double total(){ 
        Nodo<Item> actual = items.getInicio();
        while(actual != null){
           total += actual.getDato().calcularSubTotal();
           actual = actual.getSiguiente();
        }
        return total;
    }

    public void generarItems(Inventario inv){
        char opcion;
         String rs;
         int cantidad = -1;
         ListaSimple<Item> li = new ListaSimple<>();
         int cont = 1;
         do{
            System.out.println(" Item de venta #" + cont);

            do {
            System.out.print("Ingrese el registro sanitario del producto: "); //Eso es muy poco intuitivo..
            rs = scanner.nextLine().trim();
            if (rs.isEmpty()) {
                System.out.println("El campo no puede estar vacío. Vuelva a intentar");
            }
            } while (rs.isEmpty());
            
            Nodo<Producto> nodo = inv.getProductos().buscarElemento(rs);
            
            if(nodo != null){
                Producto prod = nodo.getDato();
                
                while (true) {
                System.out.print("Cantidad a vender: ");
                if (scanner.hasNextInt()) {
                    cantidad = scanner.nextInt();
                    scanner.nextLine();
                    if (cantidad > 0) { //No hace falta revisar 
                        if( prod.getStock() < cantidad ){ //Si la cantidad en el inventario es menor a la que se pide
                        throw new IllegalArgumentException("Cantidad del producto "+ prod.getNombre() + " es " + prod.getStock() + " y se pide: " + cantidad);
                        }
                        break;
                    }
                    else {
                        System.out.println("El Stock debe ser mayor que 0. Vuelva a intentar.");
                    }
                } else {
                    System.out.println("Entrada inválida. Ingrese un número válido.");
                    scanner.next();
                    }
                } // fin while
                
                Item i = new Item(prod.getRegistroSanitario(), prod.getNombre(), cantidad, prod.getPrecioVenta());
                if(li.estaVacia()) li.insertarAlInicio(i);
                else{
                    li.insertarAlFinal(i);
                }
            }
            else{
                System.out.println("El producto no existe");
            }
            
            
            System.out.print("Desea continuar agregando items? (y/n): ");opcion = scanner.nextLine().toLowerCase().charAt(0);
            cont++;
         }while( opcion == 'y');
         this.items = li;
         this.total = total();

    }
    
    public void procesarVenta(Inventario inv){ //Uso de las estructuras de datos
        Nodo <Item> actual = items.getInicio();
        while(actual != null){
            
            Nodo <Producto> productoActual = inv.getProductos().buscarElemento(actual.getDato().getCodigo()); //Busca si uno de los productos que aparece en la boleta de venta
            int nuevoStock = productoActual.getDato().getStock() - actual.getDato().getCantidad();
            if(nuevoStock == 0) inv.getProductosSinStock().encolar(productoActual.getDato());
            productoActual.getDato().setStock(nuevoStock);
            actual = actual.getSiguiente();
        }
  
        //Si venta exitosa
        if(inv.getRegistroVentas().estaVacia()) inv.getRegistroVentas().insertarAlInicio(this);
        else{
            inv.getRegistroVentas().insertarAlFinal(this);
        }
        System.out.println("Venta realizada");
    }
    
    @Override
    public String getCodigo() {
        return idVenta;
    }
    
    @Override
    public String toString(){
        return """
               idVenta: """ + this.idVenta +
                "\nCliente Identificado con DNI o RUC: " + this.clienteIdentificacion +
                "\nFecha: " + this.fecha +
                "\nTotal: " + this.total;
    }
}
