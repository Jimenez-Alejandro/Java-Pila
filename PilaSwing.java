import java.awt.*;
import javax.swing.*;

public class PilaSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicial());
    }
}

// ---------------- PANTALLA 1 ----------------
class PantallaInicial extends JFrame {
    public PantallaInicial() {
        setTitle("Estructuras de Datos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Seleccione la estructura de datos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JButton botonPila = new JButton("PILA");
        botonPila.setFont(new Font("Arial", Font.PLAIN, 14));
        botonPila.addActionListener(e -> {
            dispose();
            new PantallaPila();
        });

        add(botonPila, BorderLayout.CENTER);
        setVisible(true);
    }
}

// ---------------- CLASE PILA ----------------
class Pila {
    private int[] pila;
    private int cima;
    private final int MAX = 6;

    public Pila() {
        pila = new int[MAX];
        cima = -1;
    }

    public boolean estaVacia() {
        return cima == -1;
    }

    public boolean estaLlena() {
        return cima == MAX - 1;
    }

    public boolean push(int dato) {
        if (estaLlena()) return false;
        pila[++cima] = dato;
        return true;
    }

    public Integer pop() {
        if (estaVacia()) return null;
        return pila[cima--];
    }

    public int getCima() {
        return cima;
    }

    public int[] getDatos() {
        return pila.clone();
    }
}

// ---------------- PANTALLA 2 ----------------
class PantallaPila extends JFrame {
    private Pila pila;
    private JTextArea areaMostrar;

    public PantallaPila() {
        pila = new Pila();

        setTitle("Operaciones con Pila");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Operaciones con PILA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        // Área de visualización
        areaMostrar = new JTextArea();
        areaMostrar.setEditable(false);
        areaMostrar.setFont(new Font("Consolas", Font.PLAIN, 14));
        add(new JScrollPane(areaMostrar), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10));

        JButton btnInsertar = new JButton("Insertar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Acciones de botones ---
        btnInsertar.addActionListener(e -> insertar());
        btnEliminar.addActionListener(e -> eliminar());
        btnMostrar.addActionListener(e -> mostrar());
        btnRegresar.addActionListener(e -> {
            dispose();
            new PantallaInicial();
        });

        setVisible(true);
    }

    private void insertar() {
        String entrada = JOptionPane.showInputDialog(this, "Ingrese un número (1-99):");
        if (entrada == null) return; // Cancelar

        try {
            int num = Integer.parseInt(entrada);
            if (num < 1 || num > 99) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número entre 1 y 99", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pila.push(num)) {
                JOptionPane.showMessageDialog(this, "La pila está llena", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Número insertado correctamente");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida, debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        Integer eliminado = pila.pop();
        if (eliminado == null) {
            JOptionPane.showMessageDialog(this, "La pila está vacía", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Se eliminó el número: " + eliminado);
        }
    }

    private void mostrar() {
        StringBuilder sb = new StringBuilder();
        int[] datos = pila.getDatos();

        sb.append("ÍNDICE\tVALOR\n");
        sb.append("----------------\n");
        for (int i = datos.length - 1; i >= 0; i--) {
            sb.append(i).append("\t");
            if (i <= pila.getCima()) {
                sb.append(datos[i]);
                if (i == pila.getCima()) sb.append("  <-- CIMA");
            }
            sb.append("\n");
        }

        areaMostrar.setText(sb.toString());
    }
}