package GUI;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class loadGameScreen {

    public static void main(String[] args) {
        // 1. Creamos las opciones que se van a mostrar
        String[] opciones = {
            "1 - Planeta Rojo",
            "2 - Estación Omega",
            "3 - Base Alpha",
            "4 - Colonia X",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
            "5 - Nexo Delta",
        };

        // 2. Creamos una lista con esas opciones
        JList<String> lista = new JList<>(opciones);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // solo una opción
        JScrollPane scroll = new JScrollPane(lista); // por si hay muchas opciones

        // 3. Mostramos la ventana (esto bloquea el programa hasta que el usuario elija)
        int resultado = JOptionPane.showConfirmDialog(
            null,
            scroll,
            "Selecciona una opción",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // 4. Comprobamos si el usuario ha elegido algo
        if (resultado == JOptionPane.OK_OPTION && lista.getSelectedValue() != null) {
            String seleccion = lista.getSelectedValue();
            System.out.println("Seleccionaste: " + seleccion);
        } else {
            System.out.println("No seleccionaste nada o cancelaste.");
        }

        // Aquí continúa el programa normalmente
    }
}