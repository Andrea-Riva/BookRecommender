package org.BookRecommender.Model;

import org.BookRecommender.Libreria;
import org.BookRecommender.Libro;

import java.util.ArrayList;

public class LibreriaModel {
    public static Libreria libreria;
    public static ArrayList<Libro> libriToAdd = new ArrayList<>();   // Quando viene creata una nuova libreria, List.clear()
    public static int rowGridPanel = 0; // Row dove si posiziona il libro trovato nel panel, quando viene creata la libreria = 0
}
