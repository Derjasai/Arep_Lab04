package edu.eci.arep.app.controllers;

import edu.eci.arep.app.anotaciones.Controller;
import edu.eci.arep.app.anotaciones.RequestMapping;
import edu.eci.arep.app.requesters.RequestFile;

@Controller
/**
 * Clase encargada de cargar los archivos necesarios para visualizar la página de error 404
 */
public class NotFoundController {

    private static final RequestFile requestFile = RequestFile.getInstance();

    /**
     * Carga el HTML de la página de 404
     * @return Retorna el header y el body del HTML
     */
    @RequestMapping("/404")
    public static String html404(){
        requestFile.setPath("/404.html");
        requestFile.setType("text/html");
        requestFile.setCode("/404 Not Found");
        requestFile.setBody();

        return requestFile.getResponse();
    }

    /**
     * Carga el JS de la página de 404
     * @return Retorna el header y el body del JS
     */
    @RequestMapping("/404.js")
    public static String js404(){
        requestFile.setPath("/404.js");
        requestFile.setType("text/js");
        requestFile.setCode("/404 Not Found");
        requestFile.setBody();

        return requestFile.getResponse();
    }

    /**
     * Carga el CSS de la página de 404
     * @return Retorna el header y el body del CSS
     */
    @RequestMapping("/404.css")
    public static String css404(){
        requestFile.setPath("/404.css");
        requestFile.setType("text/css");
        requestFile.setCode("/404 Not Found");
        requestFile.setBody();

        return requestFile.getResponse();
    }
    
}
