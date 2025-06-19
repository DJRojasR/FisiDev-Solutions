package Proyecto;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

public class InterfazGrafica {

    private Repositorio repositorio;
    private DefaultListModel<String> listModel;
    private JList<String> listaAlgoritmos;

    public InterfazGrafica(Repositorio repositorio) {
        this.repositorio = repositorio;
        crearVentana();
    }

    private void crearVentana() {
        JFrame frame = new JFrame("Evaluador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuIngreso = new JMenu("Ingresar Algoritmos");
        JMenuItem itemIngreso = new JMenuItem("Agregar Algoritmo");
        itemIngreso.addActionListener(e -> mostrarFormularioIngreso());
        menuIngreso.add(itemIngreso);
        menuBar.add(menuIngreso);
        frame.setJMenuBar(menuBar);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        actualizarLista();
        listaAlgoritmos = new JList<>(listModel);
        listaAlgoritmos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaAlgoritmos);

        JButton detallesButton = new JButton("Mostrar Detalles");
        detallesButton.addActionListener(e -> mostrarDetallesAlgoritmo());
        JButton eliminarButton = new JButton("Eliminar Algoritmo");
        eliminarButton.addActionListener(e -> eliminarAlgoritmo());
        JButton compararButton = new JButton("Comparar Algoritmos");
        compararButton.addActionListener(e -> compararAlgoritmos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(detallesButton);
        panelBotones.add(eliminarButton);
        panelBotones.add(compararButton);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        frame.getContentPane().add(panelPrincipal);
        frame.setVisible(true);
    }

    private void mostrarFormularioIngreso() {
        JFrame ingresoFrame = new JFrame("Agregar Algoritmo");
        ingresoFrame.setSize(400, 300);
        ingresoFrame.setLocationRelativeTo(null);

        JTextField nombreField = new JTextField(20);
        JTextArea codigoArea = new JTextArea(10, 30);

        JButton agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            String codigo = codigoArea.getText();
            if (nombre.isEmpty() || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(ingresoFrame, "Completar todos los campos");
                return;
            }
            String id = repositorio.obtenerSiguienteId();
            Algoritmo algoritmo = new Algoritmo(nombre, id, codigo);
            repositorio.agregarAlgoritmo(algoritmo);
            actualizarLista();
            ingresoFrame.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre del Algoritmo:"));
        panel.add(nombreField);
        panel.add(new JLabel("Codigo del Algoritmo:"));
        panel.add(new JScrollPane(codigoArea));
        panel.add(agregarButton);

        ingresoFrame.add(panel);
        ingresoFrame.setVisible(true);
    }

    private void mostrarDetallesAlgoritmo() {
        String selectedValue = listaAlgoritmos.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un algoritmo del listado");
            return;
        }
        Algoritmo algoritmo = repositorio.buscarAlgoritmo(selectedValue.split(" - ")[0]);
        if (algoritmo != null) {
            String mensaje = String.format(
                "Nombre: %s\nID: %s\nCódigo:\n%s\nComplejidad: %s\nTipo: %s",
                algoritmo.getNombre(), algoritmo.getId(), algoritmo.getCodigo(),
                algoritmo.obtenerComplejidad(), algoritmo.obtenerTipo()
            );
            JOptionPane.showMessageDialog(null, mensaje, "Detalles del Algoritmo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarAlgoritmo() {
        String selectedValue = listaAlgoritmos.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un algoritmo a eliminar");
            return;
        }
        String id = selectedValue.split(" - ")[0];
        repositorio.eliminarAlgoritmo(id);
        actualizarLista();
        JOptionPane.showMessageDialog(null, "El algoritmo ha sido eliminado");
    }


    //Busqueda y IDs
    private void compararAlgoritmos() {
        int[] selectedIndices = listaAlgoritmos.getSelectedIndices();
        if (selectedIndices.length != 2) {
            JOptionPane.showMessageDialog(null, "Seleccione exactamente dos algoritmos para comparar");
            return;
        }

        String id1 = listModel.get(selectedIndices[0]).split(" - ")[0];
        String id2 = listModel.get(selectedIndices[1]).split(" - ")[0];

        Algoritmo algoritmo1 = repositorio.buscarAlgoritmo(id1);
        Algoritmo algoritmo2 = repositorio.buscarAlgoritmo(id2);

        if (algoritmo1 != null && algoritmo2 != null) {
            graficarFuncionesTiempo(List.of(algoritmo1, algoritmo2));
        } else {
            JOptionPane.showMessageDialog(null, "Error al cargar Algoritmos");
        }
    }

    public static void graficarFuncionesTiempo(List<Algoritmo> algoritmos) {
    XYSeriesCollection dataset = new XYSeriesCollection();

    // Grafico
    for (Algoritmo algoritmo : algoritmos) {
        XYSeries series = new XYSeries(algoritmo.getNombre());
        String complejidad = algoritmo.obtenerComplejidad();

        for (int n = 1; n <= 100; n++) { 
            double tiempoEstimado = calcularTiempoEstimado(complejidad, n);
            series.add(n, tiempoEstimado); 
        }
        dataset.addSeries(series);
    }

    JFreeChart chart = ChartFactory.createXYLineChart(
        "Comparacion de Complejidad de Algoritmos",
        "Tamaño de la Entrada (n)", 
        "Tiempo Relativo",         
        dataset
    );

    //Grafico y ejes
    XYPlot plot = chart.getXYPlot();
    NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
    NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    
    xAxis.setRange(0, 100); 
    yAxis.setRange(0, 100); 

    JFrame frame = new JFrame("Gráfico de Funciones");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.add(new ChartPanel(chart), BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
}

    private static double calcularTiempoEstimado(String complejidad, int n) {
    switch (complejidad) {
        case "O(1)": 
            return 1;
        case "O(log n)": 
            return n > 0 ? Math.log(n) : 0;
        case "O(n)": 
            return n;
        case "O(n log n)": 
            return n > 0 ? n * Math.log(n) : 0;
        case "O(n^2)": 
            return Math.pow(n, 2);
        case "O(n!)": 
            return factor(n); 
        case "O(2^n)": 
            return n <= 100 ? Math.pow(2, n) : 0;
        default: 
            return n;
        }
    }
    //Caso Factorial
    private static double factor(int n) {
        if (n == 0 || n == 1) return 1;

        double resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;     
        }
        return resultado;
    }
    
    private void actualizarLista() {
        listModel.clear();
        for (Algoritmo algoritmo : repositorio.obtenerAlgoritmos()) {
            listModel.addElement(algoritmo.getId() + " - " + algoritmo.getNombre());
        }
    }
    
    
    public static void main(String[] args) { 

      try {
        Repositorio repositorio = new Repositorio(); 
        SwingUtilities.invokeLater(() -> {
            new InterfazGrafica(repositorio); 
        });
    } catch (Exception e) {
        e.printStackTrace(); 
    }
   } 
}