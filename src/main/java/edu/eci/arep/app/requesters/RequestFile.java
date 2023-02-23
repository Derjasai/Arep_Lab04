package edu.eci.arep.app.requesters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase enfocada en cargar los archivos especificados en el path
 */
public class RequestFile {

    private static RequestFile instance;
    private String path;
    private String code;
    private String type;
    private String body;

    /**
     * Obtener la única instancia de la clase
     * @return La instancia de la clase
     */
    public static RequestFile getInstance() {
        if(instance == null){
            instance = new RequestFile();
        }
        return instance;
    }

    /**
     * Obtener el header y el body
     * @return El header y body
     */
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

    public void setBody(){
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        body = new String(fileContent);
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
