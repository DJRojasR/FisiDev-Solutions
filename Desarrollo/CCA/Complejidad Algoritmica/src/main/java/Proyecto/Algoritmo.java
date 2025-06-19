package Proyecto;

public class Algoritmo {
    private String nombre;
    private String id;
    private String codigo;

    public Algoritmo(String nombre, String id, String codigo) {
        this.nombre = nombre;
        this.id = id;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public String getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }

    public String obtenerComplejidad() {
        return Complejidad.calcularFuncionTiempo(this);
    }
    public String obtenerTipo() {
        return Clasificacion.obtenerTipo(this);
    }

    public void mostrarAlgoritmo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Código:\n" + codigo);
        System.out.println("Complejidad: " + obtenerComplejidad());
        System.out.println("Tipo: " + obtenerTipo());
    }
}