package edu.eci.arep.app;

import edu.eci.arep.app.anotaciones.Controller;
import edu.eci.arep.app.anotaciones.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Levantar un servicio web que va a correr por el puerto 35000
 */
public class HttpServer {

    private static HttpServer instance;
    private final HashMap<String, Method> services = new HashMap<>();
    private OutputStream outputStream = null;
    private final String packagePath = "edu.eci.arep.app.controllers";


    public static HttpServer getInstance() {
        if (instance == null){
            instance = new HttpServer();
        }
        return instance;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Inicia a correr el servidor web, rastrea todos los controllers y los añade en un hashmap de servicios
     * @throws IOException exception
     */
    public void run () throws IOException, ClassNotFoundException {
        List<Class<?>> classes = getClasses();
        for(Class<?> classIteration : classes){
            if(classIteration.isAnnotationPresent(Controller.class)){
                String className = classIteration.getName();
                Class<?> classProv = Class.forName(className);
                Method[] methods = classProv.getMethods();
                for(Method method : methods){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        System.out.println(method.getAnnotation(RequestMapping.class).value());
                        services.put(method.getAnnotation(RequestMapping.class).value(), method);
                    }
                }
            }
        }
        
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
            outputStream = clientSocket.getOutputStream();
            String inputLine;

            String outputLine = "";
            boolean first_line = true;
            String request = "/simple";
            //String verb = "GET";

            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                if (first_line) {
                    request = inputLine.split(" ")[1];
                    //verb = inputLine.split(" ")[0];
                    first_line = false;
                }
                //System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            if(!request.equals("/favicon.ico")) {
                try{
                    if (services.containsKey(request)) {
                        outputLine = (String) services.get(request).invoke(null);
                    } else {
                        outputLine = (String) services.get("/404").invoke(null);
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(request);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Obtiene las clases de un directorio específico
     * @return Lista de clases del directorio
     */
    private List<Class<?>> getClasses(){
        List<Class<?>> classes = new ArrayList<>();
        String path = packagePath.replace(".", "/");
        try{
            for(String classPath : getClassPaths()){
                File file = new File(classPath + "/" + path);
                getFilesClasses(file,classes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * Obtiene todos los archivos de extensión class y los guarda en una lista
     * @param file El directorio en donde se va a buscar
     * @param classes La lista en la cual se va a agregar las clases encontradas
     * @throws ClassNotFoundException En caso de no encontrar archivos
     */
    private void getFilesClasses(File file, List<Class<?>> classes) throws ClassNotFoundException {
        if(file.exists() && file.isDirectory()){
            for(File classFile : Objects.requireNonNull(file.listFiles())){
                if(classFile.isFile() && classFile.getName().endsWith(".class")){
                    String className = packagePath + "." + classFile.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }
    }

    /**
     * Obtiene los classpath y los agrega en una lista
     * @return Lista con los classpath
     */
    private List<String> getClassPaths() {
        String classPath = System.getProperty("java.class.path");
        String[] classPaths = classPath.split(System.getProperty("path.separator"));
        return new ArrayList<>(Arrays.asList(classPaths));
    }
}