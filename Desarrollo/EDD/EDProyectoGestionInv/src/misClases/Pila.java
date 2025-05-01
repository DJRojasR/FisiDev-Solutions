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
public class Pila<T extends Identificable>{ // Es usada para imprimir NODOS del ARBOL
    Nodo <T> tope;
    
    public Pila(){
        this.tope = null;
    }
    public void apilar(Nodo <T> nodo){
        if(nodo == null){
            Nodo <T> nuevoNodoVacio = new Nodo<>(null);
            nuevoNodoVacio.setSiguiente(tope);
            tope = nuevoNodoVacio;
        }
        else{
            nodo.setSiguiente(tope);
            tope = nodo;
        }
    }
    
    public Nodo desapilar(){
        if(this.estaVacia()) throw new IllegalStateException("Pila vacia");
        Nodo dato = tope;
        tope = tope.getSiguiente();
        return dato;
    }
    
    public Nodo peek(){
        return this.tope;
    }
    public boolean estaVacia(){
        return tope == null;
    }
}
