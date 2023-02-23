package edu.eci.arep.app.controllers;

import edu.eci.arep.app.anotaciones.Controller;
import edu.eci.arep.app.anotaciones.RequestMapping;
import edu.eci.arep.app.requesters.RequestImage;

import java.io.IOException;

@Controller
/**
 * Cargar las imagenes que se encuentren guardadas en memoria
 */
public class ImageController {

    private static final RequestImage requestImage = RequestImage.getInstance();

    /**
     * Carga la imagen de Hu Tao guardada en memoria
     * @return Encabezado y body con la imagen a visualizar
     */
    @RequestMapping("/huTao")
    public static String huTao(){
        requestImage.setPath("/HuTaoCute.png");
        requestImage.setType("image/png");
        requestImage.setCode("/200 OK");
        try {
            requestImage.setBody();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requestImage.getResponse();
    }

}
