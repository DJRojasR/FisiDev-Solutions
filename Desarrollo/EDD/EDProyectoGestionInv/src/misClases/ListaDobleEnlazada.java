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
public class ListaDobleEnlazada<T extends Identificable> {// Usado para registrar las compras y ventas que se generan
    private Nodo <T> inicio;
    private Nodo <T> fin;
    
    public ListaDobleEnlazada(){
        this.inicio=null;
        this.fin = null;
    }
    
    public void insertarAlInicio(T dato){
        Nodo<T> nuevo = new Nodo<>(dato);
        if(inicio == null) inicio = fin = nuevo;
        else{
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
            inicio = nuevo;
        }
    }
    
    public void insertarAlFinal(T dato){
        Nodo<T> nuevo = new Nodo<>(dato);
        if(fin == null) inicio = fin = nuevo;
        else{
            nuevo.setAnterior(fin);
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }
    
    public void imprimir(){
        Nodo<T> actual = inicio;
        while(actual != null){
            System.out.print(" "+ actual.getDato().getCodigo());
            actual = actual.getSiguiente();
        }
    }
    
    public boolean estaVacia(){
        return inicio == null;
    }
    public Nodo<T> getInicio(){
        return this.inicio;
    }
    
    public Nodo<T> getFin(){
        return this.fin;
    }
}
