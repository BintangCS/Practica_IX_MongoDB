import ui.VentanaPrincipal;

import javax.swing.*;

public static void main(String[] args) {
    conexion.DataImporter.importarJson("resources/cocteles.json");
    //conexion.DataImporter.importarJson("C:\\Users\\bcast\\Downloads\\Practica_IX_MongoDB-main\\Practica_IX_MongoDB-main\\Practica_IX_Bintang\\resources\\cocteles.json");

    SwingUtilities.invokeLater(() -> {
        new VentanaPrincipal().setVisible(true);
    });
}