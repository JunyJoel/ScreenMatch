package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.Pelicula;
import com.alura.screenmatch.modelos.Serie;
import com.alura.screenmatch.modelos.Titulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrincipalConListas {
    public static void main(String[] args){

        Pelicula miPelicula = new Pelicula("Encanto", 2021);
        miPelicula.evalua(9);
        Pelicula otraPelicula = new Pelicula("Avatar", 2023);
        otraPelicula.evalua(6);
        var peliculaDeBruno = new Pelicula("The Lord Of The Rings", 2001);
        peliculaDeBruno.evalua(10);

        Serie lost = new Serie("Lost", 2000);

        ArrayList<Titulo> lista = new ArrayList<>();
        lista.add(miPelicula);
        lista.add(otraPelicula);
        lista.add(peliculaDeBruno);
        lista.add(lost);

        for(Titulo item: lista){
            System.out.println(item.getNombre());
            if (item instanceof Pelicula pelicula && pelicula.getClasificacion() > 2 ){
                System.out.println(((Pelicula) item).getClasificacion());
            }
        }

//************************************************************************************

        ArrayList<String> listaDeArtistas = new ArrayList<>();
        listaDeArtistas.add("Penelope Cruz");
        listaDeArtistas.add("Antonio Banderas");
        listaDeArtistas.add("Ricardo Darin");
        System.out.println(listaDeArtistas);

        Collections.sort(listaDeArtistas);
        System.out.println(listaDeArtistas);

        Collections.sort(lista);
        System.out.println("\n Peliculas y series ordenadas por nombre: \n" + lista);

        lista.sort(Comparator.comparing(Titulo::getFechaDeLanzamiento));
        System.out.println("\n Peliculas y series ordenadas por fecha de lanzamiento: \n" + lista);

//************************************************************************************


//************************************************************************************


//************************************************************************************

    }
}
