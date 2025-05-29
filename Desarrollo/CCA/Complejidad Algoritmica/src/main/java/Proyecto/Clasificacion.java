package Proyecto;

import java.util.regex.Pattern;

public class Clasificacion {

    public static String obtenerTipo(Algoritmo algoritmo) {
        String codigo = algoritmo.getCodigo().toLowerCase();

        if (esAlgoritmoDeOrdenamiento(codigo)) {
            return "Ordenamiento";
        }
        if (codigo.contains("search") || codigo.contains("find") || esBusquedaBinaria(codigo)) {
            return "Búsqueda";
        }
        if (esPermutacion(codigo)) {
            return "Permutaciones/Combinaciones";
        }
        if (codigo.contains("dynamic programming") || esProgramacionDinamica(codigo)) {
            return "Programación Dinámica";
        }
        if (contieneRecursion(codigo)) {
            return "Recursivo";
        }
        if (codigo.contains("for") || codigo.contains("while")) {
            return "Iterativo";
        }
        return "Desconocido";
    }

    private static boolean esAlgoritmoDeOrdenamiento(String codigo) {
        return codigo.contains("sort") || codigo.matches(".*(bubble|selection|insertion|quick|merge).*");
    }
    private static boolean esBusquedaBinaria(String codigo) {
        return codigo.contains("mid") && codigo.contains("left") && codigo.contains("right") && codigo.contains("/");
    }
    private static boolean esProgramacionDinamica(String codigo) {
        return codigo.contains("memo") || codigo.contains("table") || codigo.contains("dp");
    }
    private static boolean esPermutacion(String codigo) {
        return codigo.contains("intercambiar") || codigo.contains("backtrack") || codigo.contains("generarPermutaciones");
    }
    private static boolean contieneRecursion(String codigo) {
        return Pattern.compile("\\breturn\\b.*\\b\\w+\\(").matcher(codigo).find();
    }
}