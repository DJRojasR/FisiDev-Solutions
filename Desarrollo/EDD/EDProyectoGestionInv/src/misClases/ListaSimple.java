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
public class ListaSimple<T extends Identificable> { //Usado para los conjuntos de ITEMS en la clase compra y clase venta
    private Nodo <T> inicio;
    
    public ListaSimple(){
        this.inicio=null;
    }
    
    public void insertarAlInicio(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        if(inicio == null) inicio = nuevo;
        else{
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
    }
    
    public void insertarAlFinal(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        if(inicio == null) inicio = nuevo;
        else{
            Nodo<T> actual = inicio;
            while( actual.getSiguiente() != null){
               actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }
    public boolean estaVacia(){
        return inicio == null;
    }
    public void imprimir(){
        Nodo<T> actual = inicio;
        while(actual != null){
            System.out.print(" "+ actual.getDato().getCodigo());
            actual = actual.getSiguiente();
        }
    }
    
    public Nodo<T> getInicio(){
        return this.inicio;
    }
}
