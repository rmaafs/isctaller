package com.rmaafs.taller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PostMensaje {

    public PostMensaje(String pass, String nombre, String mensaje) {
        HashMap<String, String> values = new HashMap<>();
        values.put("pass", pass);
        values.put("nombre", nombre);
        values.put("mensaje", mensaje);
        String respuesta = peticion("http://m.rmaafs.com/taller/", values);
        System.out.println(respuesta);
    }

    /**
     * Función que hace las peticiones.
     * @param url URL
     * @param values Values que serán enviados cuando es por POST
     * @return Respuesta del servidor
     */
    private String peticion(String url, HashMap<String, String> values) {
        String content = "";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.addRequestProperty("nomeborres", "seguridad");//No borres esta línea
            //La línea anterior, lo que hace es decirle a mi servidor que se ejecutó desde el código que yo te pasé :)
            //En caso de que intentaran hacer petición de otro lado, no podrán porque solo tú sabes que tienes que mandar "nomeborres" y "seguridad"

            connection.setDoOutput(true);//Triggers POST.
            try {
                connection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            if (values != null) {
                StringBuilder params = new StringBuilder("");
                for (Map.Entry entry : values.entrySet()) {
                    if (params.length() > 0) {
                        params.append("&");
                    }
                    params.append(entry.getKey()).append("=").append(entry.getValue());
                }

                connection.getOutputStream().write(params.toString().getBytes("UTF-8"));
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content += inputLine;
            }
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }
}
