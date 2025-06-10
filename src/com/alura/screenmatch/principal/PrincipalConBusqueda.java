package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorEnConversionDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Apertura para leer del teclado
        Scanner lectura = new Scanner(System.in);
        List<Titulo> titulos = new ArrayList<>();

        //Se usa la libreria Gson para parsear la informacion del json recibido a un record
        Gson gson = new GsonBuilder() //usando la libreria Gson se crea un objeto tipo GsonBuilder() llamado gson
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE) //se le indica la forma en la que debe parsear la informacion UPPER_CAMEL_CASE
                .setPrettyPrinting()
                .create(); //Finaliza la construccion del Gson en gson

        while(true){
            //Mensaje para pedir informacion al usuario
            System.out.println("Escriba le nombre de la pelicula...");
            var busqueda = lectura.nextLine(); //asignacion de entrada del usuario en forma de String a var busqueda

            //Break
            if(busqueda.equalsIgnoreCase("salir")){
                break;
            }

            String clave = System.getenv("OMDB_APIKEY"); //OCULTANDO EL APIKEY CON LA VARIABLE DEL SISTEMA OMDB_APIKEY
            String direccion =  "https://www.omdbapi.com/?t="
                    +busqueda.replace(" ","+")
                    +"&apikey="
                    +clave; //concatenacion de request al API


            try {
                HttpClient client = HttpClient.newHttpClient(); //se crea cliente para realizar acciones con el API
                HttpRequest request = HttpRequest.newBuilder() //inicia la construccion del request hacia el API
                        .uri(URI.create(direccion)) //asigna la direccion concatenada anteriormente para enviarla al API
                        .build(); //culmina la construccion del request

                HttpResponse<String> response = client  //se crea variable tipo HttpResponse de forma <String> llamada response
                        //que proviene del cliente creado anteriormente en HttpClient client = HttpClient.newHttpClient();
                        .send(request, HttpResponse.BodyHandlers.ofString());   //.send envia el http de forma sincrona y el programa espera por la respuesta
                //request es el creado anteriormente HttpRequest request = HttpRequest.newBuilder()
                //HttpResponse.BodyHandlers.ofString() aqui le decimos al programa como recibir la respuesta
                //ofString() le dice el programa que guarde toda la respuesta de forma plana en un response.body()


                String json = response.body();  //Se crea objeto String llamado json para asignarle el string de la respuesta del request
                System.out.println(json);   //Se imprime el string plano guardado en el String json

                //Se crea un record tipo TituloOmdb para obtener la informacion del json
                TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);    //Se crea un objeto tipo TituloOmbd es un record que recibe informacion del gson
                //con el metodo .fromJson(json, TituloOmdb.class) lee del json y lo guarda en la clase TituloOmdb
                System.out.println(miTituloOmdb); //Imprime la informacion del record miTituloOmdb obtenida a traves del json

//******************************************************************************************
//          CATCH
//          Manejo de escepciones en la ejecucion del programa

                //intenta correr parte del codigo
                Titulo miTitulo = new Titulo(miTituloOmdb);
                System.out.println("Titulo ya convertido: " + miTitulo);

                titulos.add(miTitulo);

            }catch(NumberFormatException e) { //si detecta errores en el intento anterior corre esta parte del codigo
                System.out.println("Ocurrio un error: ");
                System.out.println("     " + e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la direccion.");
            }catch (ErrorEnConversionDuracionException e){
                System.out.println("     " + e.getMessage());
            }catch (Exception e){
                System.out.println("Ocurrio un error inesperado");
            }
        }

        //se agrega el titulo ingresado a la lista 'titulos'
        System.out.println(titulos);

        //Se crea archivo 'titulos.json' para guardar los titulos ingresados en la lista 'titulos'
        FileWriter escritura = new FileWriter("titulos.json");
        escritura.write(gson.toJson(titulos)); //se convierte la lista 'titulos' a formato json
        escritura.close();

        System.out.println("Finalizo la ejecucion del programa.");

    }
}
