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
public class Arbol <T extends Identificable> { // Se usa extends pues si el tipo T fuese primitivo no se podria implementar metodos. Además delimitamos de esta manera los tipos a solo aquellos que tengan getCodigo() usando la interface Identificable
    private Nodo <T> raiz;
    
    public Arbol(){
        raiz = null;
    }
    
    
public Nodo buscarElemento(String codigo){
        Nodo <T> nodoActual = raiz;
        if(nodoActual == null) return null;
        while( nodoActual.getDato().getCodigo().compareTo(codigo) != 0 ){ //Usando T extends identificable se sabe qué raiz tendra un metodo getCodigo()!
            
            if( nodoActual.getDato().getCodigo().compareTo(codigo) > 0){
                nodoActual = nodoActual.getIzquierda();
            }
            else{
                nodoActual = nodoActual.getDerecha();
            }
            if(nodoActual == null){
                return null;
            }
        }
        return nodoActual;
    }

    public void insertarNodo(T dato){
        Nodo <T> nuevoNodo = new Nodo<>(dato);
        if ( raiz == null){
            raiz = nuevoNodo;
        }
        else{
            Nodo <T> nodoActual = raiz;
            Nodo <T> nodoPadre;
            
            while(true){
                nodoPadre = nodoActual;
                if(nodoActual.getDato().getCodigo().compareTo(dato.getCodigo()) == 0){
                    System.out.println("Ya existe");
                    return;
                }
                else if(nodoActual.getDato().getCodigo().compareTo(dato.getCodigo()) > 0){ // Sea str1.CompareTO(Str2), si str1 es mayor a str2 devolvera un entero positivo, si no un entero negativo. Si sale positivo el nuevo nodo es menor, entonces se inserta por izquierda
                    nodoActual = nodoActual.getIzquierda();
                    if(nodoActual == null){
                        nodoPadre.setIzquierda(nuevoNodo);
                        return;
                    }
                }
                else{
                  nodoActual = nodoActual.getDerecha();
                    if(nodoActual == null){
                        nodoPadre.setDerecha(nuevoNodo);
                        return;
                    }  
                }
            }
        }     
    }
    
    public void eliminarNodo(String codigo){
        
        Nodo <T> nodoActual = raiz;
        Nodo <T> nodoPadre = raiz;
        
        boolean esHijoIzquierdo = true;
        if(nodoActual == null) throw new IllegalStateException("Arbol vacio");
        
        while(nodoActual != null && nodoActual.getDato().getCodigo().compareTo(codigo) != 0 ){
            nodoPadre = nodoActual;
            if(nodoActual.getDato().getCodigo().compareTo(codigo) > 0){
                nodoActual = nodoActual.getIzquierda();
                esHijoIzquierdo = true;
            }
            else{
                nodoActual = nodoActual.getDerecha();
                esHijoIzquierdo = false;
            }
        }
        if(nodoActual == null) throw new IllegalStateException("No existe nodo");
        
        
        if(nodoActual.getDerecha() == null && nodoActual.getIzquierda() == null){ //Caso sin hijos
            if(nodoActual == raiz){
                raiz = null;
            }
            else if(esHijoIzquierdo){
                nodoPadre.setIzquierda(null);
            }
            else{
                nodoPadre.setDerecha(null);
            }
        }
        //Casos un hijo
        if(nodoActual.getIzquierda() == null){
            if(nodoActual == raiz){
                raiz = nodoActual.getDerecha();
            }
            else if(esHijoIzquierdo){
                nodoPadre.setIzquierda(nodoActual.getDerecha());
            }
            else{
                nodoPadre.setDerecha(nodoActual.getDerecha());
            }
        }
        else if(nodoActual.getSiguiente() == null){
            if(nodoActual == raiz){
                raiz = nodoActual.getIzquierda();
            }
            else if(esHijoIzquierdo){
                nodoPadre.setIzquierda(nodoActual.getIzquierda());
            }
            else{
                nodoPadre.setDerecha(nodoActual.getIzquierda());
            }
        }
        //Caso de 2 hijos 
        else{
            Nodo <T> reemplazo = obtenerMaximo(nodoActual.getIzquierda());
            nodoActual.setDato(reemplazo.getDato());
            eliminarNodo(reemplazo.getDato().getCodigo());
        }
    }
    
    public Nodo <T> obtenerMaximo(Nodo<T> nodo){
        while(nodo.getDerecha() != null){
            nodo = nodo.getDerecha();
        }
        return nodo;
    }
    
    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("Árbol vacío.");
            return;
        }

        Pila<T> pila = new Pila<>(); 
        pila.apilar(raiz);
        int espacios = 32; 
        boolean filaVacia = false;
        String separador = "-----------------------------------------------------------------";
        System.out.println(separador);

        while (!filaVacia) { 
            Pila<T> pilaLocal = new Pila<>();
            filaVacia = true;

            for (int j = 0; j < espacios; j++)
                System.out.print(" ");

            while (!pila.estaVacia()) { 
                Nodo<T> temp = pila.desapilar(); 

                if (temp != null && temp.getDato() != null) {
                    System.out.print(temp.getDato().getCodigo()); 
                    pilaLocal.apilar(temp.getIzquierda()); 
                    pilaLocal.apilar(temp.getDerecha());
                    if (temp.getIzquierda() != null || temp.getDerecha() != null)
                        filaVacia = false;
                } else {
                    System.out.print("__");
                    pilaLocal.apilar(new Nodo<>(null));
                    pilaLocal.apilar(new Nodo<>(null));
                }
                for (int j = 0; j < espacios * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            espacios /= 2;

            while (!pilaLocal.estaVacia()) 
                pila.apilar(pilaLocal.desapilar());
        }
        System.out.println(separador);
    }
}
