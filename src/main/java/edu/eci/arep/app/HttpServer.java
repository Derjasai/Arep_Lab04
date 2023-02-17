package edu.eci.arep.app;

import java.net.*;
import java.io.*;
import java.util.HashMap;



/**
 * Levantar un servicio web que va a correr por el puerto 35000
 */
public class HttpServer {

    private static HttpServer instance;

    public static HttpServer getInstance() {
        if (instance == null){
            instance = new HttpServer();
        }
        return instance;
    }

    /**
     * Metodo para iniciar el programa
     * @param args main
     * @throws IOException exception
     */
    public void run (String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine,title="";

            String outputLine = "";
            boolean first_line = true;
            String request = "/simple";
            String verb = "GET";

            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                if(inputLine.contains("info?title=")){
                    String[] prov = inputLine.split("title=");
                    title = (prov[1].split("HTTP")[0]).replace(" ", "");
                }
                if (first_line) {
                    request = inputLine.split(" ")[1];
                    verb = inputLine.split(" ")[0];
                    first_line = false;
                }
                //System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if (request.startsWith("/apps/")) {
                }
                //outputLine = executeService(request.substring(5));
                //outputLine = jsonSimple();
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Entregar el index de la página principal
     * @return index en formato de String del HTML del inicio de la Página
     */
    private static String index(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Buscador de peliculas</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Buscar una pelicula</h1>\n" +
                "<form action=\"/hello\">\n" +
                "    <label for=\"name\">Titulo de la pelicula a buscar:</label><br>\n" +
                "    <input type=\"text\" id=\"name\" name=\"name\" value=\"The Avengers\"><br><br>\n" +
                "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "</form>\n" +
                "<div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "<script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/info?title=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "</body>\n" +
                "</html>";
    }
}