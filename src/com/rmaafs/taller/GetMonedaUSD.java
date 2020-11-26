package com.rmaafs.taller;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class GetMonedaUSD {

    public String precio = "";

    /**
     * EJEMPLO DE USO:
     * BotMonedaUSD dollar = new BotMonedaUSD();
     * System.out.println("1 Dolar es igual a MXN $" + dollar.precio + " pesos mexicanos.");
     */

    public GetMonedaUSD() {
        String url = "https://www.google.com/search?q=1+USD+A+MXN&oq=1+USD+A+MXN&aqs=chrome..69i57.5024j0j1&sourceid=chrome&ie=UTF-8";

        String precioClave = "\" data-precision=\"2\" data-value=\"";

        String html = getHTML(url);//Obtenemos la respuesta HTML
        for (String line : html.split("\n")) {//Ciclamos todas las líneas de las respuestas
            if (line.contains(precioClave)) {//Si la línea contiene el texto donde está el precio...
                int indexStart = line.indexOf(precioClave) + precioClave.length();
                // data-precision="2" data-value="20.09575"
                this.precio = line.substring(indexStart, buscaCaracter(line, '"', indexStart));
            }

            if (!precio.equals("")) {
                break;
            }
        }
    }

    /**
     * Función que buscará apartir de un index, el index del carácter enviado.
     * @param line Línea completa donde se buscará el carácter
     * @param caracter Carácter a buscar
     * @param index Index de donde se empezará a buscar en "line"
     * @return Retornará el index donde se encuentra el carácter a buscar.
     */
    private int buscaCaracter(String line, char caracter, int index) {
        for (int i = index; i < line.length(); i++) {
            if (line.charAt(i) == caracter) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Función que hace la petición GET a una URL
     * @param url Página a hacer la petición
     * @return Retornará el código HTML con la respuesta de la petición.
     */
    private String getHTML(String url) {
        String content = null;
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
            connection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }
}
