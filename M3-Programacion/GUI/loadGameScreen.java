package GUI;
import classes.GlobalContext;
import classes.Planet;
import ddbb.PlanetStatsTable;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class loadGameScreen {

    private loadGameScreen() {

    }

    public loadGameScreen(ArrayList<String> arrayWithIdAndName, Planet planet) {
        // Create an object String[] from the ArrayList
        String[] options = new String[arrayWithIdAndName.size()];
        for (int i=0 ; i<options.length ; i++) {
            options[i] = arrayWithIdAndName.get(i);
        }

        // Create the list with options
        JList<String> list = new JList<>(options);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(list);

        // Getting the option from user
        int resultado = JOptionPane.showConfirmDialog(
            null,
            scroll,
            "Select save file",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // 
        if (resultado == JOptionPane.OK_OPTION && list.getSelectedValue() != null) {
            String seleccion = list.getSelectedValue();
            System.out.println("Seleccionaste: " + seleccion);

            // Get the id from the string
            String[] parts = list.getSelectedValue().split("\\s+"); // divide strings by black spaces
            int planet_id = Integer.parseInt(parts[0]);

            // Load the new PlanetStatsTable
            GlobalContext.planetStatsTable = new PlanetStatsTable(GlobalContext.database);
            GlobalContext.planetStatsTable.getRow(planet_id);

            // Update the Planet using the PlanetStatsTable
            GlobalContext.planetStatsTable.updatePlanetWithTable(planet);

        } else {
            // if user closes window, then we do the logic of "new game"
            System.out.println("No seleccionaste nada o cancelaste.");
            GlobalContext.planetStatsTable = new PlanetStatsTable(GlobalContext.database, planet);
            GlobalContext.planetStatsTable.insertRow();
        }
    }
}