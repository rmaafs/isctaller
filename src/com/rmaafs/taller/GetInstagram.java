package com.rmaafs.taller;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class GetInstagram {

    public String nombre = "";
    public String cuenta = "";
    public String followers = "";
    public String following = "";

    /**
     * EJEMPLO DE USO:
     * GetInstagram cuenta = new GetInstagram("https://www.instagram.com/relmaps/?hl=mx");
     * System.out.println("Nombre: " + cuenta.nombre);
     * System.out.println("Cuenta: " + cuenta.cuenta);
     * System.out.println("Followers: " + cuenta.followers);
     * System.out.println("Following: " + cuenta.following);
     */

    public GetInstagram(String url) {
        String nombreClave = "\",\"name\":\"";
        String cuentaClave = "\",\"alternateName\":\"@";
        String followersClave = "\",\"userInteractionCount\":\"";
        String followingClave = "\"edge_follow\":{\"count\":";

        String html = getHTML(url);//Obtenemos la respuesta HTML
        for (String line : html.split("\n")) {//Ciclamos todas las líneas de la respuesta
            if (line.contains(nombreClave)) {//Si la línea contiene el texto donde está el nombre...
                int indexStart = line.indexOf(nombreClave) + nombreClave.length();
                //","name":"Rodrigo M. Atilano"
                this.nombre = line.substring(indexStart, buscaCaracter(line, '"', indexStart));
            }

            if (line.contains(cuentaClave)) {//Si la línea contiene el texto donde está la cuenta...
                int indexStart = line.indexOf(cuentaClave) + cuentaClave.length();
                this.cuenta = line.substring(indexStart, buscaCaracter(line, '"', indexStart));
            }

            if (line.contains(followersClave)) {//Si la línea contiene el texto donde está los followers...
                int indexStart = line.indexOf(followersClave) + followersClave.length();
                this.followers = line.substring(indexStart, buscaCaracter(line, '"', indexStart));
            }

            if (line.contains(followingClave)) {//Si la línea contiene el texto donde está los following...
                int indexStart = line.indexOf(followingClave) + followingClave.length();
                this.following = line.substring(indexStart, buscaCaracter(line, '}', indexStart));
            }

            if (!nombre.equals("") && !cuenta.equals("") && !followers.equals("") && !following.equals("")) {
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
