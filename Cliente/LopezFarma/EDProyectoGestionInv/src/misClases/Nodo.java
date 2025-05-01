/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 * 
 * @param <T>
 */
public class Nodo <T extends Identificable> {
    private T dato;
    private Nodo <T> siguiente; //Derecha
    private Nodo <T> anterior; //Izquierda
    private Nodo <T> izquierda; // Se agregaron para evitar conflicto con la pila alterando los punteros
    private Nodo <T> derecha;
    
    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
        this.izquierda = null;
        this.derecha = null;
    }
    
    public T getDato(){
        return this.dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
    
    public void setDerecha(Nodo <T> derecha){
        this.derecha = derecha;
    }
    
    public void setIzquierda(Nodo <T> izquierda){
        this.izquierda = izquierda;
    }
    
    public Nodo getSiguiente(){
        return siguiente;
    }
    
     public Nodo getAnterior(){
        return anterior;
    }
     
     public Nodo getDerecha(){
        return derecha;
    }
    
     public Nodo getIzquierda(){
        return izquierda;
    }
    
    @Override
    public String toString(){
        return "Nodo{"+
                "valor = " + this.dato +
                ", Nodo siguiente = " + this.siguiente +
                ", Nodo anterior = " + this.anterior +
                "}";
    }
}
