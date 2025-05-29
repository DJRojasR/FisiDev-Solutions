package Proyecto;

import java.util.regex.Pattern;

public class Complejidad {

    public static String calcularFuncionTiempo(Algoritmo algoritmo) {
        String codigo = algoritmo.getCodigo().toLowerCase();

        //Casos 
        if (esRecursionFactorial(codigo)) {
            return "O(n!)";
        }
        if (codigo.contains("fibonacci") && codigo.contains("fibonacci(n - 1)") && codigo.contains("fibonacci(n - 2)")) {
            return "O(2^n)";
        }
        int nivelBucles = contarNivelBucles(codigo);
        boolean tieneRecursion = contieneRecursion(codigo);
        //Casos Generales
        if (nivelBucles == 0 && !tieneRecursion) {
            return "O(1)";
        }
        if (esBusquedaBinaria(codigo)) {
            return "O(log n)";
        }
        if (nivelBucles == 1 && !tieneRecursion) {
            return "O(n)";
        }
        if (nivelBucles == 2 && !tieneRecursion) {
            return "O(n^2)";
        }
        if (nivelBucles > 2 && !tieneRecursion) {
            return "O(n^" + nivelBucles + ")";
        }

        // Recursion
        if (esRecursionLineal(codigo)) {
            return "O(n)";
        }
        if (esRecursionBinaria(codigo)) {
            return "O(2^n)";
        }
        if (esDivideYVenceras(codigo)) {
            return "O(n log n)";
        }

        return "Complejidad desconocida";
    }

    private static boolean esRecursionFactorial(String codigo) {
        return Pattern.compile("return\\s+\\w+\\s*\\*\\s*\\w+\\(").matcher(codigo).find();
    }
    private static int contarNivelBucles(String codigo) {
        int nivelMaximo = 0, nivelActual = 0;
        for (String linea : codigo.split("\\n")) {
            linea = linea.trim();
            if (linea.startsWith("for") || linea.startsWith("while")) {
                nivelActual++;
                nivelMaximo = Math.max(nivelMaximo, nivelActual);
            }
            if (linea.contains("}")) {
                nivelActual = Math.max(0, nivelActual - 1);
            }
        }
        return nivelMaximo;
    }

    private static boolean contieneRecursion(String codigo) {
        return Pattern.compile("\\breturn\\b.*\\b\\w+\\(").matcher(codigo).find();
    }
    private static boolean esRecursionLineal(String codigo) {
        return contieneRecursion(codigo) && !esRecursionBinaria(codigo);
    }
    private static boolean esRecursionBinaria(String codigo) {
        long count = codigo.lines()
                .filter(linea -> linea.contains("return") && linea.contains("("))
                .count();
        return count == 2; // Dos llamadas recursivas
    }
    private static boolean esDivideYVenceras(String codigo) {
        return contieneRecursion(codigo) && 
               (codigo.contains("merge") || codigo.contains("partition") || 
                codigo.contains("divide"));
    }
    private static boolean esBusquedaBinaria(String codigo) {
        return codigo.contains("mid") && 
               codigo.contains("left") && 
               codigo.contains("right") && 
               codigo.contains("/");
    }
}