/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 * @author just0
 * @param <T>
 */
public class Cola <T extends Identificable> { //Usado para la cola de llenado de informacion de productos nuevos
    Nodo <T> frente;
    
    public Cola(){
        this.frente = null;
    }
    
    public void encolar(T dato){
        Nodo nuevoNodo = new Nodo(dato);
        if(frente == null) frente = nuevoNodo;
        else{
            Nodo <T> nodoActual = this.frente;
            while(nodoActual.getSiguiente() != null){
            nodoActual = nodoActual.getSiguiente();
            }
            nodoActual.setSiguiente(nuevoNodo);
        }
    }
    public void retirarDato(String codigo){
        Nodo <T> nodoActual = this.frente;
        if(frente == null) return;
        if(frente.getDato().getCodigo().equals(codigo)) frente = frente.getSiguiente();
        else{
            while(nodoActual.getSiguiente() != null){
                
                if(nodoActual.getSiguiente().getDato().getCodigo().equals(codigo)){
                    nodoActual.setSiguiente(nodoActual.getSiguiente().getSiguiente());
                    return;
                }
                nodoActual = nodoActual.getSiguiente();
            
            }
            
        }
    }
    
    public T desencolar(){
        if(estaVacia()) throw new IllegalStateException("No existen más elementos en cola");
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        return dato;
    }
    
    public Nodo peek(){
        if(estaVacia()) throw new IllegalStateException("No existen más elementos en cola");
        return this.frente;
    }
    public boolean estaVacia(){
        return frente == null;
    }
    
}
