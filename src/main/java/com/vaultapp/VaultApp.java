package com.vaultapp;

import java.util.Locale;

/**
 * Launcher class of the application.
 */
public class VaultApp {

    /**
     * Launches the aplication in CLI or graphic mode.
     * @param args mode to launch the application in (--cli for CLI or --gui for graphic)
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        String errMsg = "ERROR: argumentos no válidos\n\t" +
                "Uso: java -jar com.vaultapp.VaultApp.jar [modo]\n" +
                "Modos:\n\t" +
                "--cli: aplicación en línea de comandos\n\t" +
                "--gui: aplicación con cliente gráfico";

        if (args.length == 0) {
            MainGUI.startApp();
        } else if (args.length == 1 && args[0].equals("--cli")) {
            MainCLI.startApp();
        } else if (args.length == 1 && args[0].equals("--gui")) {
            MainGUI.startApp();
        } else {
            System.out.println(errMsg);
        }
    }
}
