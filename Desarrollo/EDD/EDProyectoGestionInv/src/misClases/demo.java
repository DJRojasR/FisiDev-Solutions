/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

import java.util.Scanner;

/**
 *
 *
 */
public class demo {
    private static Scanner scanner = new Scanner(System.in);
    private static Inventario inv = new Inventario();
    
    public static void main (String[] args){
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    try{
                        buscarProducto();
                    }catch(Exception e){
                        System.out.println("No existen productos aun");
                    }
                    break;
                case 3:
                        inv.getProductos().imprimirArbol();
                    break;
                
                case 4:
                    vender();
                    break;
                case 5:
                    comprar();
                    break;
                case 6:
                    try{
                        mostrarVentas();
                    }catch(Exception e){
                        System.out.println("No existen mas registros de ventas");
                    }
                    
                    break;
                case 7:
                    try{
                        mostrarCompras();
                    }catch(Exception e){
                        System.out.println("No existen mas registros de compras");
                    }
                    
                    break;
                case 8:
                    try{
                        mostrarProductosConInformacionRestante();
                    }catch(Exception e){
                        System.out.println("No existen productos con informacion por rellenar");
                    }
                    
                    break;
                case 9:
                    try{
                        mostrarProductosSinStock();
                    }catch(Exception e){
                        System.out.println("No existen productos sin stock");
                    }
                    
                    break;
                case 10:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 10);
    }
    
    
    private static void mostrarMenu() {
        System.out.println("\n--- Menu de Inventario ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Buscar producto por RS");
        System.out.println("3. Mostrar inventario en forma de arbol"); 
        
        System.out.println("4. Realizar Venta"); 
        System.out.println("5. Realizar Compra"); 
        System.out.println("6. Mostrar Ventas");
        System.out.println("7. Mostrar Compras");
        
        System.out.println("8. Ver Productos pendientes de llenado");
        System.out.println("9. Ver Productos sin stock");
        
        System.out.println("10. Salir");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static void agregarProducto(){
        String nombre, registroSanitario, fabricante, condicionVenta = null, formaFarmaceutica, fechaVencimiento, descripcion;
        double precioVenta;
        int stock;
        System.out.println("\n------- Producto nuevo -------");
        
        do {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (nombre.isEmpty());

        do {
            System.out.print("Registro Sanitario: ");
            registroSanitario = scanner.nextLine().trim();
            if (registroSanitario.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (registroSanitario.isEmpty());
        
        do {
            System.out.print("Fabricante: ");
            fabricante = scanner.nextLine().trim();
            if (fabricante.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (fabricante.isEmpty());
        
        int opcion;
        do {
            
            System.out.println("Condicion de venta");
            System.out.println("1) Con receta medica");
            System.out.println("2) Con receta medica retenida");
            System.out.println("3) Sin receta medica");
            
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion){
                case 1 -> condicionVenta = "Con receta medica";
                case 2 -> condicionVenta = "Con receta medica retenida";
                case 3 -> condicionVenta = "Sin receta medica";
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while ( opcion < 1 || opcion > 3 );
        
        do {
            System.out.print("Forma farmaceutica: ");
            formaFarmaceutica = scanner.nextLine().trim();
            if (formaFarmaceutica.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (formaFarmaceutica.isEmpty());
        
        while (true) {
            System.out.print("Precio de venta en soles: ");
            if (scanner.hasNextDouble()) {
                precioVenta = scanner.nextDouble();
                scanner.nextLine();
                if (precioVenta > 0) {
                    break;
                } else {
                    System.out.println("El precio debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada invalida. Ingrese un numero valido.");
                scanner.next();
            }
        }
        
        do {
            System.out.print("Fecha de vencimiento: ");
            fechaVencimiento = scanner.nextLine().trim();
            if (fechaVencimiento.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (fechaVencimiento.isEmpty());
        
        System.out.print("Descripcion: ");
        descripcion = scanner.nextLine();
        
        while (true) {
            System.out.print("Stock: ");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
                scanner.nextLine();
                if (stock > 0) {
                    break;
                } else {
                    System.out.println("El Stock debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada invalida. Ingrese un numero valido.");
                scanner.next();
            }
        }
        
        Producto nuevoProducto = new Producto(nombre, registroSanitario, fabricante, condicionVenta, formaFarmaceutica, precioVenta, fechaVencimiento, descripcion, stock);
        inv.getProductos().insertarNodo(nuevoProducto);
    }
    
    private static void buscarProducto() throws Exception{
        System.out.println("\n------- Busqueda de producto -------");
        String rs;
        System.out.print("Ingrese registro sanitario del producto: ");rs = scanner.nextLine();
            
        Nodo<Producto> nodo = inv.getProductos().buscarElemento(rs);
        
        if(nodo != null){
            Producto prod = nodo.getDato();
            
            if(prod.faltaInformacion()){
                llenarInfo(prod);
            }else{
                System.out.println("Se encontro el siguiente producto: ");
                System.out.println(prod.toString());
            }
            
            System.out.println("Desea modificar el producto? (y/n)");
            System.out.print("Escriba: ");
            char opcion;
            opcion = scanner.nextLine().toLowerCase().charAt(0);
            if( opcion == 'y'){
                modificarProducto(prod);
            }
            else{
                System.out.println("Volviendo al menu...");
            }
        }
        else{
            System.out.println("No existe el producto");
        }
    }
    
    private static void modificarProducto(Producto prod){
        System.out.println("\n------- Modificacion del producto -------");
        
        System.out.println("Que campo desea modificar?");
        
        int opcion;
        do {
            System.out.println(" 1) Nombre");
            System.out.println(" 2) Descripcion");
            System.out.println(" 3) Precio de venta");
            System.out.println(" 4) Volver.. ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    prod.setNombre(nuevoNombre);
                    System.out.print("Cambio realizado con exito");
                    break;
                case 2:
                    System.out.print("Nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    prod.setDescripcion(nuevaDescripcion);
                    System.out.print("Cambio realizado con exito");
                    break;
                case 3:
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();
                    prod.setPrecioVenta(nuevoPrecio);
                    System.out.print("Cambio realizado con exito");
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }
    
    public static void llenarInfo(Producto prod){
        System.out.println("\n------- Informacion a llenar del producto -------");
        System.out.println("A este producto le falta informacion, rellene la informacion");
        
        String fabricante, condicionVenta = null, formaFarmaceutica, fechaVencimiento, descripcion;
        double precioVenta;
        
        do {
            System.out.print("Fabricante: ");
            fabricante = scanner.nextLine().trim();
            if (fabricante.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (fabricante.isEmpty());
        
        int opcion;
        do {
            
            System.out.println("Condicion de venta");
            System.out.println("1) Con receta medica");
            System.out.println("2) Con receta medica retenida");
            System.out.println("3) Sin receta medica");
            
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion){
                case 1 -> condicionVenta = "Con receta medica";
                case 2 -> condicionVenta = "Con receta medica retenida";
                case 3 -> condicionVenta = "Sin receta medica";
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while ( opcion < 1 || opcion > 3 );
        
        do {
            System.out.print("Forma farmaceutica: ");
            formaFarmaceutica = scanner.nextLine().trim();
            if (formaFarmaceutica.isEmpty()) {
                System.out.println("El campo no puede estar vacío. Vuelva a intentar");
            }
        } while (formaFarmaceutica.isEmpty());
        
        while (true) {
            System.out.print("Precio de venta en soles: ");
            if (scanner.hasNextDouble()) {
                precioVenta = scanner.nextDouble();
                scanner.nextLine();
                if (precioVenta > 0) {
                    break;
                } else {
                    System.out.println("El precio debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada invalida. Ingrese un numero valido.");
                scanner.next();
            }
        }
        
        do {
            System.out.print("Fecha de vencimiento: ");
            fechaVencimiento = scanner.nextLine().trim();
            if (fechaVencimiento.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (fechaVencimiento.isEmpty());
        
        System.out.print("Descripcion: ");
        descripcion = scanner.nextLine();
        
        prod.llenarInfoProducto(fabricante, condicionVenta, formaFarmaceutica, precioVenta, fechaVencimiento, descripcion);
        inv.getProductosConInformacionFaltante().retirarDato(prod.getCodigo());
    }
    
    private static void vender(){
        System.out.println("\n---------- Venta ----------");
        System.out.println("Rellenar la informacion");
         
        String idCliente;
        do {
            System.out.print("DNI o RUC del cliente: ");
            idCliente = scanner.nextLine().trim();
            if (idCliente.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (idCliente.isEmpty());
        
        Venta nuevaVenta = new Venta(idCliente);
        
        try{
            nuevaVenta.generarItems(inv);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            nuevaVenta.procesarVenta(inv);
        }catch(Exception e){
            System.out.println("No se genero la venta");
        }
        
    }
    
    private static void comprar(){
        System.out.println("\n---------- Compra ----------");
        System.out.println("Rellenar la informacion");
       
        String proveedor;
        do {
            System.out.print("Proveedor: ");
            proveedor = scanner.nextLine().trim();
            if (proveedor.isEmpty()) {
                System.out.println("El campo no puede estar vacio. Vuelva a intentar");
            }
        } while (proveedor.isEmpty());
        
        Compra nuevaCompra = new Compra(proveedor);
        nuevaCompra.generarItems(inv);
        nuevaCompra.procesarCompra(inv);
    }
    
    private static void mostrarVentas() throws Exception{
        System.out.println("\n---------- Registro de Ventas ----------");
        Nodo<Venta> actualVentas = inv.getRegistroVentas().getInicio();
        int opcion;
        boolean verificador;
        
        do{
            System.out.println(actualVentas.getDato().toString());
            Nodo<Item> actualItem = actualVentas.getDato().getItems().getInicio();
            while(actualItem != null){
                System.out.println(actualItem.getDato().toString());
                actualItem = actualItem.getSiguiente();
            }
            
            if(actualVentas.getAnterior() == null){
                System.out.println("Ingrese 1 si desea ir al siguiente");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                if(opcion == 1){
                    actualVentas = actualVentas.getSiguiente();
                    verificador = true;
                }
                else verificador = false;
                
            }
            else if(actualVentas.getSiguiente() == null){
                System.out.println("Ingrese 2 si desea ir al anterior");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                if(opcion == 2){
                    actualVentas = actualVentas.getAnterior();
                    verificador = true;
                }
                else verificador = false;
                
            }else{
                System.out.println("Ingrese 1 si desea ir al siguiente");
                System.out.println("Ingrese 2 si desea ir al anterior");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                switch (opcion) {
                    case 1 -> {
                        actualVentas = actualVentas.getSiguiente();
                        verificador = true;
                    }
                    case 2 -> {
                        actualVentas = actualVentas.getAnterior();
                        verificador = true;
                    }
                    default -> verificador = false;
                }
            }
            
        }while(verificador);
    }
    
    private static void mostrarCompras() throws Exception{
        System.out.println("\n---------- Registro de Compras ----------");
        Nodo<Compra> actualCompras = inv.getRegistroCompras().getInicio();
        int opcion;
        boolean verificador;
        do{
            System.out.println(actualCompras.getDato().toString());
            Nodo<Item> actualItem = actualCompras.getDato().getItems().getInicio();
            while(actualItem != null){
                System.out.println(actualItem.getDato().toString());
                actualItem = actualItem.getSiguiente();
            }
            
            if(actualCompras.getAnterior() == null){
                System.out.println("Ingrese 1 si desea ir al siguiente");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                if(opcion == 1){
                    actualCompras = actualCompras.getSiguiente();
                    verificador = true;
                }
                else verificador = false;
                
            }
            else if(actualCompras.getSiguiente() == null){
                System.out.println("Ingrese 2 si desea ir al anterior");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                if(opcion == 2){
                    actualCompras = actualCompras.getAnterior();
                    verificador = true;
                }
                else verificador = false;
                
            }else{
                System.out.println("Ingrese 1 si desea ir al siguiente");
                System.out.println("Ingrese 2 si desea ir al anterior");
                System.out.println("Ingrese cualquier otro numero para salir");
                opcion = leerEnteroPositivo("Opcion: ");
                switch (opcion) {
                    case 1 -> {
                        actualCompras = actualCompras.getSiguiente();
                        verificador = true;
                    }
                    case 2 -> {
                        actualCompras = actualCompras.getAnterior();
                        verificador = true;
                    }
                    default -> verificador = false;
                }
            }
            
        }while(verificador);
    }
    private static void mostrarProductosConInformacionRestante() throws Exception{
        System.out.println("\n---------- Productos con informacion incompleta ----------");
        Nodo <Producto> prodActual = inv.getProductosConInformacionFaltante().frente;
        boolean existeAlmenosuno = false;
        
        while(prodActual != null){
            System.out.println("Nombre: " + prodActual.getDato().getNombre() + 
                    "\nRegistro Sanitario: " + prodActual.getDato().getRegistroSanitario());
            System.out.println("################");
            prodActual = prodActual.getSiguiente();
            existeAlmenosuno=true;
        }
        
        if(existeAlmenosuno) System.out.println("Si desea rellenar la informacion busque el producto");
    }
    
    private static void mostrarProductosSinStock() throws Exception{
        System.out.println("\n---------- Productos sin Stock ----------");
        Nodo <Producto> prodActual = inv.getProductosSinStock().frente;
        while(prodActual != null){
            System.out.println("Nombre: " + prodActual.getDato().getNombre() + 
                    "\nRegistro Sanitario: " + prodActual.getDato().getRegistroSanitario());
            System.out.println("################");
            prodActual = prodActual.getSiguiente();
        }
    }
    
    private static int leerEnteroPositivo(String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("El número debe ser mayor que 0. Vuelva a intentar.");
                }
            } else {
                System.out.println("Entrada invalida. Ingrese un numero valido.");
                scanner.next();  
            }
        }
    }
    
}
