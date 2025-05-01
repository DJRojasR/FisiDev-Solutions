/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package misClases;

/**
 *
 */

//Una interface permite implementar el mismo metodo de distintas formas
//En este caso se usa como un  "contrato" por lo que si una clase la implementa debe tener sus metodos como parte del contrato
//Esta caracteristica se usará para definir un tipo generico que si o si tenga el metodo getCodigo(), osea que siempre sea identificable por un codigo
public interface Identificable{ 
    String getCodigo();
}
