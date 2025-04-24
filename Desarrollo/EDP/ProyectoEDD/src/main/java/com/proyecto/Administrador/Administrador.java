package com.proyecto.Administrador;

import com.proyectoSuperUsuario.SuperUsuario;
import java.io.Serializable;

/**
 *
 * @author ALE
 */
public class Administrador extends SuperUsuario implements Serializable {

    private static final long serialVersionUID = 1L; //para la persistencia
    private double salario;

    public Administrador(String nombres, String usuario, String contra,int edad, double salario) {
        super(nombres, usuario, contra,edad);
        setEstado(true);
        this.salario = salario;
    }

    public Administrador() {
    }
    

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return super.toString() + ", Salario: " + salario;
    }

}
