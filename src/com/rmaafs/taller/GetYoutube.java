package com.rmaafs.taller;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class GetYoutube {

    public String nombre = "";
    public String vistas = "";
    public String dislikes = "";
    public String likes = "";

    /**
     * EJEMPLO DE USO:
     * GetYoutube video = new GetYoutube("https://www.youtube.com/watch?v=YcKpb7yybhY");
     * System.out.println("Nombre: " + video.nombre);
     * System.out.println("Vistas: " + video.vistas);
     * System.out.println("Likes: " + video.likes);
     * System.out.println("Dislikes: " + video.dislikes);
     */

    public GetYoutube(String url) {
        String nombreClave = "\",\\\"title\\\":\\\"";
        String vistasClave = ",\\\"viewCount\\\":\\\"";
        String dislikesClave = "\"DISLIKE\"},\"defaultText\":{\"accessibility\":{\"accessibilityData\":{\"label\":\"";
        String likesClave = "\"LIKE\"},\"defaultText\":{\"accessibility\":{\"accessibilityData\":{\"label\":\"";

        String html = getHTML(url);//Obtenemos la respuesta HTML
        for (String line : html.split("\n")) {//Ciclamos todas las líneas de la respuesta
            if (line.contains(nombreClave)) {//Si la línea contiene el texto donde está el nombre...
                int indexStart = line.indexOf(nombreClave) + nombreClave.length();
                //",\"title\":\"ESTOY AGARRANDO
                this.nombre = line.substring(indexStart, buscaCaracter(line, '\\', indexStart));
            }

            if (line.contains(vistasClave)) {//Si la línea contiene el texto donde está las vistas...
                int indexStart = line.indexOf(vistasClave) + vistasClave.length();
                this.vistas = line.substring(indexStart, buscaCaracter(line, '\\', indexStart));
            }

            if (line.contains(dislikesClave)) {//Si la línea contiene el texto donde está los dislikes...
                int indexStart = line.indexOf(dislikesClave) + dislikesClave.length();
                //Usa el siguiente carácter (No es espacio, está entre las comillas) " "
                this.dislikes = line.substring(indexStart, buscaCaracter(line, ' ', indexStart));
            }

            if (line.contains(likesClave)) {//Si la línea contiene el texto donde está los likes...
                int indexStart = line.indexOf(likesClave) + likesClave.length();
                //Usa el siguiente carácter (No es espacio, está entre las comillas) " "
                this.likes = line.substring(indexStart, buscaCaracter(line, ' ', indexStart));
            }

            if (!nombre.equals("") && !vistas.equals("") && !dislikes.equals("") && !likes.equals("")) {
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
