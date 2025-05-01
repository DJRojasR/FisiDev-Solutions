/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 * 
 */
public class Producto implements Identificable{
    private String nombre;
    private String registroSanitario; //Es un código único. Util para obtener bastante informacion acerca del producto, incluso podria automatizarse
    private String fabricante;
    private String condicionVenta; //Bajo receta medica u otro
    private String formaFarmaceutica; // Se puede decir es la familia del producto (tipo / presentacion) Ej. capsulas, tabletas, etc
    private double precioVenta;
    private String fechaVencimiento; //Deberia ser tipo Date**
    private String descripcion;
    private int stock;
    private boolean estaVencido = false;
    private boolean faltaInformacion;

    //Existen tres constructores para la clase
    public Producto(String n, String rs, String fabr, String cV, String fF, double pV, String fV, String d, int s){ //Constructor general
        this.nombre = n;
        this.registroSanitario = rs;
        this.fabricante = fabr;
        this.formaFarmaceutica = fF;
        this.condicionVenta = cV;
        this.precioVenta = pV;
        this.fechaVencimiento = fV;
        this.descripcion = d;
        this.stock = s;
        this.faltaInformacion = false;
    }
    /**
     * Constructor para clase "Compra". El sistema reconoce un nuevo producto en una compra y construye el producto
     * @param n
     * @param rs
     * @param cantidad 
     */
    public Producto(String n, String rs, int cantidad){
        this.nombre = n;
        this.registroSanitario = rs;
        this.stock = cantidad;
        this.faltaInformacion = true;
    }
    
    /**
     * Llenado de informacion de un nuevo producto posterior a la compra
     * @param fabr
     * @param cV
     * @param fF
     * @param pV
     * @param fV
     * @param d
     */
    public void llenarInfoProducto(String fabr, String cV, String fF, double pV, String fV, String d){
        this.fabricante = fabr;
        this.formaFarmaceutica = fF;
        this.condicionVenta = cV;
        this.precioVenta = pV;
        this.fechaVencimiento = fV;
        this.descripcion = d;
        this.faltaInformacion = false;
    }
    
    @Override
    public String getCodigo() {
        return registroSanitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }
    
    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean estaVencido() {
        return estaVencido;
    }

    public void setEstaVencido(boolean estaVencido) {
        this.estaVencido = estaVencido;
    }

    public boolean faltaInformacion() {
        return faltaInformacion;
    }

    public void setFaltaInformacion(boolean faltaInformacion) {
        this.faltaInformacion = faltaInformacion;
    }
    
    @Override
    public String toString(){
        return """
               Nombre: """ + this.nombre +
                "\nRegistro sanitario: " + this.registroSanitario +
                "\nFabricante: " + this.fabricante +
                "\nCondicion de venta: " + this.condicionVenta +
                "\nForma farmaceutica: " + this.formaFarmaceutica +
                "\nPrecio de venta: " + this.precioVenta +
                "\nFecha de vencimiento: " + this.fechaVencimiento +
                "\nStock: " + this.stock +
                "\nDescripcion: " + this.descripcion;
    }
}
