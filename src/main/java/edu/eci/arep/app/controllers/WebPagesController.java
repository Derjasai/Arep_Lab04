package edu.eci.arep.app.controllers;

import edu.eci.arep.app.anotaciones.Controller;
import edu.eci.arep.app.anotaciones.RequestMapping;
import edu.eci.arep.app.requesters.RequestFile;

@Controller
/**
 * cargar todas las páginas web conocidas guardadas en memoria
 */
public class WebPagesController {

    private static final RequestFile requestFile = RequestFile.getInstance();

    /**
     * Carga el HTML de la página deL index
     * @return Retorna el header y el body del HTML
     */
    @RequestMapping("/")
    public static String index(){
        requestFile.setPath("/index.html");
        requestFile.setType("text/html");
        requestFile.setCode("/200 OK");
        requestFile.setBody();

        return requestFile.getResponse();
    }

    /**
     * Carga el JS de la página deL index
     * @return Retorna el header y el body del JS
     */
    @RequestMapping("/index.js")
    public static String indexJS(){
        requestFile.setPath("/index.js");
        requestFile.setType("text/js");
        requestFile.setCode("/200 OK");
        requestFile.setBody();

        return requestFile.getResponse();
    }

    /**
     * Carga el CSS de la página deL index
     * @return Retorna el header y el body del CSS
     */
    @RequestMapping("/index.css")
    public static String indexCSS(){
        requestFile.setPath("/index.css");
        requestFile.setType("text/css");
        requestFile.setCode("/200 OK");
        requestFile.setBody();

        return requestFile.getResponse();
    }

    /**
     * Carga un mensaje precargado
     * @return Retorna el header y el body del mensaje
     */
    @RequestMapping("/hello")
    public static String hello(){
        return "HTTP/1.1 200 \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n"+
                "Greetings from Spring Boot!";
    }


}
