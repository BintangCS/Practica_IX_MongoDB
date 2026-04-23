package ui;

import controladores.CocktailController;
import org.bson.Document;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private CocktailController controller;
    private JTextArea areaResultados;
    private JTextField inputNombre;

    public VentanaPrincipal() {
        controller = new CocktailController();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Cocktail Manager");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Nombre Cóctel:"));
        inputNombre = new JTextField(15);
        panel.add(inputNombre);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaResultados);

        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 5, 5));
        
        JButton btnListar = new JButton("Listar Todos");
        JButton btnBuscarIng = new JButton("Buscar Ingrediente");
        JButton btnBorrar = new JButton("Borrar por Nombre");
        JButton btnVaso = new JButton("Cambiar Vaso");
        JButton btnTopVasos = new JButton("Top 5 Vasos");
        JButton btnCategorias = new JButton("Por Categoría");
        JButton btnComplejos = new JButton("Filtro Complejidad");

        btnListar.addActionListener(e -> mostrarLista(controller.listarTodos()));
        
        btnBuscarIng.addActionListener(e -> {
            String ing = JOptionPane.showInputDialog("Ingrediente a buscar:");
            if (ing != null) mostrarLista(controller.buscarIngrediente(ing));
        });

        btnBorrar.addActionListener(e -> {
            String res = controller.borrarCocktail(inputNombre.getText());
            JOptionPane.showMessageDialog(this, res);
        });

        btnVaso.addActionListener(e -> {
            String nuevoVaso = JOptionPane.showInputDialog("Nuevo tipo de vaso:");
            String res = controller.cambiarVaso(inputNombre.getText(), nuevoVaso);
            JOptionPane.showMessageDialog(this, res);
        });

        btnTopVasos.addActionListener(e -> {
            areaResultados.setText("--- TOP 5 VASOS ---\n");
            controller.TopVasos().forEach(d -> 
                areaResultados.append(d.get("_id") + ": " + d.get("cantidad") + "\n"));
        });

        btnCategorias.addActionListener(e -> {
            areaResultados.setText("--- CONTEO CATEGORÍAS ---\n");
            controller.ConteoCategorias().forEach(d -> 
                areaResultados.append(d.get("_id") + ": " + d.get("total") + "\n"));
        });

        btnComplejos.addActionListener(e -> {
            List<Document> lista = controller.filtrarPorComplejidad(100, true);
            areaResultados.setText("--- CÓCTELES CON INSTRUCCIONES LARGAS ---\n");
            lista.forEach(d -> areaResultados.append("- " + d.get("name") + "\n"));
        });

        panelBotones.add(btnListar);
        panelBotones.add(btnBuscarIng);
        panelBotones.add(btnVaso);
        panelBotones.add(btnBorrar);
        panelBotones.add(new JSeparator());
        panelBotones.add(btnTopVasos);
        panelBotones.add(btnCategorias);
        panelBotones.add(btnComplejos);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);
    }

    private void mostrarLista(List<Document> docs) {
        areaResultados.setText("");
        if (docs.isEmpty()) {
            areaResultados.setText("No se encontraron resultados.");
            return;
        }
        for (Document d : docs) {
            areaResultados.append(String.format(" %-20s | Cat: %-15s | Vaso: %s\n", 
                d.get("name"), d.get("category"), d.get("glassType")));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}