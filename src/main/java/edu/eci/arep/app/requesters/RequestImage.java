package edu.eci.arep.app.requesters;

import edu.eci.arep.app.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase encargada de cargar archivos de tipo imagen
 */
public class RequestImage {

    private static RequestImage instance;
    private String path;
    private String code;
    private String type;
    private String body = "";

    /**
     * Obtener la única instancia de la clase
     * @return La instancia de la clase
     */
    public static RequestImage getInstance() {
        if(instance == null){
            instance = new RequestImage();
        }
        return instance;
    }


    public String getResponse(){
        return getHeader() + getBody();
    }

    public String getHeader() {
        return "HTTP/1.1 "+getCode()+"\r\n" +
                "Content-type: "+getType()+"\r\n" +
                "\r\n";
    }

    public String getBody() {
        return body;
    }

    public void setBody() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(getPath()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpServer server = HttpServer.getInstance();
        DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        dataOutputStream.writeBytes(getHeader());
        dataOutputStream.write(byteArrayOutputStream.toByteArray());
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return "src/main/resources/"+path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
