import java.awt.*;
import javax.swing.*;

public class EstructurasSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicial());
    }
}

// ---------------- PANTALLA 1 ----------------
class PantallaInicial extends JFrame {
    public PantallaInicial() {
        setTitle("Estructuras de Datos");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Seleccione la estructura de datos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JLabel datos = new JLabel(
            "<html>"
          + "Alejandro Jimenez – Céd: 20-70-5135<br>"
          + "Alejandro Rodriguez – Céd: 20-70-8018"
          + "</html>",
          SwingConstants.CENTER);
        datos.setFont(new Font("Arial", Font.PLAIN, 12));
        add(datos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton botonPila = new JButton("PILA");
        botonPila.setFont(new Font("Arial", Font.PLAIN, 14));
        botonPila.addActionListener(e -> {
            dispose();
            new PantallaPila();
        });
        panelBotones.add(botonPila);

        JButton botonCola = new JButton("COLA");
        botonCola.setFont(new Font("Arial", Font.PLAIN, 14));
        botonCola.addActionListener(e -> {
            dispose();
            new PantallaCola();
        });
        panelBotones.add(botonCola);

        add(panelBotones, BorderLayout.SOUTH);

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

// ---------------- CLASE COLA (estática circular) ----------------
class Cola {
    private int[] cola;
    private int frente;
    private int size;
    private final int CAP = 6;

    public Cola() {
        cola = new int[CAP];
        frente = 0;
        size = 0;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    public boolean estaLlena() {
        return size == CAP;
    }

    public boolean enqueue(int dato) {
        if (estaLlena()) return false;
        int rear = (frente + size) % CAP;
        cola[rear] = dato;
        size++;
        return true;
    }

    public Integer dequeue() {
        if (estaVacia()) return null;
        int valor = cola[frente];
        frente = (frente + 1) % CAP;
        size--;
        return valor;
    }

    public int getFrenteIndex() {
        return estaVacia() ? -1 : frente;
    }

    public int getRearIndex() {
        if (estaVacia()) return -1;
        return (frente + size - 1) % CAP;
    }

    public int[] getDatos() {
        return cola.clone();
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return CAP;
    }
}

// ---------------- PANTALLA 2 PARA PILA ----------------
class PantallaPila extends JFrame {
    private Pila pila;
    private JTextArea areaMostrar;

    public PantallaPila() {
        pila = new Pila();

        setTitle("Operaciones con PILA");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Operaciones con PILA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        areaMostrar = new JTextArea();
        areaMostrar.setEditable(false);
        areaMostrar.setFont(new Font("Consolas", Font.PLAIN, 14));
        add(new JScrollPane(areaMostrar), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnInsertar = new JButton("Insertar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnRegresar);
        add(panelBotones, BorderLayout.SOUTH);

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
        if (entrada == null) return;

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

// ---------------- PANTALLA 2 PARA COLA ----------------
class PantallaCola extends JFrame {
    private Cola cola;
    private JTextArea areaMostrar;

    public PantallaCola() {
        cola = new Cola();

        setTitle("Operaciones con COLA");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Operaciones con COLA (tamaño máximo 6)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        areaMostrar = new JTextArea();
        areaMostrar.setEditable(false);
        areaMostrar.setFont(new Font("Consolas", Font.PLAIN, 14));
        add(new JScrollPane(areaMostrar), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnInsertar = new JButton("Insertar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnRegresar);
        add(panelBotones, BorderLayout.SOUTH);

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
        if (entrada == null) return;

        try {
            int num = Integer.parseInt(entrada);
            if (num < 1 || num > 99) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número entre 1 y 99", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cola.enqueue(num)) {
                JOptionPane.showMessageDialog(this, "La cola está llena", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Número insertado correctamente en COLA");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida, debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        Integer eliminado = cola.dequeue();
        if (eliminado == null) {
            JOptionPane.showMessageDialog(this, "La cola está vacía", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Se eliminó de la cola: " + eliminado);
        }
    }

private void mostrar() {
    StringBuilder sb = new StringBuilder();
    sb.append("POS\tVALOR\n");
    sb.append("----------------\n");
    int cap = cola.getCapacity();
    int size = cola.getSize();
    int front = cola.getFrenteIndex();
    int[] datos = cola.getDatos();  // obtengo copia del arreglo interno

    if (size == 0) {
        sb.append("La cola está vacía\n");
    } else {
        for (int j = 0; j < size; j++) {
            int idx = (front + j) % cap;
            sb.append(idx).append("\t").append(datos[idx]).append("\n");
        }
        sb.append("\n").append("Frente index: ").append(front)
          .append("   Final index: ").append( (front + size - 1) % cap );
    }

    areaMostrar.setText(sb.toString());
}

    // pequeña utilidad para mostrar -1 como "N/A"
    private String frontIdx(int idx) {
        return idx < 0 ? "N/A" : String.valueOf(idx);
    }
}