package com.rmaafs.taller;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class BotMercadoLibre {

    public String precio = "";
    public String nombre = "";

    /**
     * EJEMPLO DE USO:
     *
     * BotMercadoLibre producto = new BotMercadoLibre("https://articulo.mercadolibre.com.mx/MLM-770912905-taladro-atornillador-electrico-20-v-inalambrico-klatter-_JM#reco_item_pos=4&reco_backend=promotions-sorted-by-score-mlm-B&reco_backend_type=low_level&reco_client=home_seller-promotions-recommendations&reco_id=6f0f653f-c17d-4702-9cff-29c1963e09c7&c_id=/home/promotions-recommendations/element&c_element_order=5&c_uid=c234058f-9133-4184-8cda-7b266f39b107");
     * System.out.println("Nombre: " + producto.nombre);
     * System.out.println("Precio: " + producto.precio);
     */

    public BotMercadoLibre(String url) {
        String precioClave = "\"offers\":{\"price\":";
        String nombreClave = "application/ld+json\">{\"name\":\"";

        String html = getHTML(url);//Obtenemos la respuesta HTML
        for (String line : html.split("\n")) {//Ciclamos todas las líneas de las respuestas
            if (line.contains(precioClave)) {//Si la línea contiene el texto donde está el precio...
                int indexStart = line.indexOf(precioClave) + precioClave.length();
                //"offers":{"price":13824.09
                this.precio = line.substring(indexStart, buscaCaracter(line, ',', indexStart));
            }

            if (line.contains(nombreClave)) {//Si la línea contiene el texto donde está el nombre...
                int indexStart = line.indexOf(nombreClave) + nombreClave.length();
                this.nombre = line.substring(indexStart, buscaCaracter(line, '"', indexStart));
            }

            if (!precio.equals("") && !nombre.equals("")) {
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
