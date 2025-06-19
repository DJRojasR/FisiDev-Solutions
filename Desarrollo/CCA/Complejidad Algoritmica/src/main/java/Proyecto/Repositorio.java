package Proyecto;

import java.io.*;
import java.util.ArrayList;

public class Repositorio {
    private ArrayList<Algoritmo> listaAlgoritmos = new ArrayList<>();
    private final String directoryPath = "algoritmos";

    public Repositorio() {
        crearDirectorio();
        cargarAlgoritmos();
    }

    private void crearDirectorio() {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void agregarAlgoritmo(Algoritmo algoritmo) {
        listaAlgoritmos.add(algoritmo);
        guardarAlgoritmo(algoritmo);
    }

    public Algoritmo buscarAlgoritmo(String id) {
        for (Algoritmo algoritmo : listaAlgoritmos) {
            if (algoritmo.getId().equals(id)) {
                return algoritmo;
            }
        }
        return null;
    }

    public void eliminarAlgoritmo(String id) {
        Algoritmo algoritmo = buscarAlgoritmo(id);
        if (algoritmo != null) {
            listaAlgoritmos.remove(algoritmo);
            eliminarArchivo(id);
        }
    }

    private void guardarAlgoritmo(Algoritmo algoritmo) {
        String filePath = directoryPath + "/" + algoritmo.getId() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(algoritmo.getId() + ";;" + algoritmo.getNombre() + ";;" + algoritmo.getCodigo().replace("\n", "\\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarAlgoritmos() {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] parts = line.split(";;");
                        if (parts.length == 3) {
                            Algoritmo algoritmo = new Algoritmo(parts[1], parts[0], parts[2].replace("\\n", "\n"));
                            listaAlgoritmos.add(algoritmo);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void eliminarArchivo(String id) {
        String filePath = directoryPath + "/" + id + ".txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public ArrayList<Algoritmo> obtenerAlgoritmos() {
        return listaAlgoritmos;
    }

    public String obtenerSiguienteId() {
    int maxId = listaAlgoritmos.stream()
        .mapToInt(a -> Integer.parseInt(a.getId()))
        .max()
        .orElse(0);
    return String.valueOf(maxId + 1);
    }
}