import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    public User() {}

    public Libro searchLibroByTitolo(String titolo) throws IOException {
        List<Libro> dataset = new JsonUtils().deserializeLibri();   // Deserializza dataset
        for(Libro l : dataset) {    // Ciclo su tutta la lista
            if(l.getTitolo().equals(titolo)) return l;  // Se i titoli corrispondono
        }
        return null;    // Libro non trovato
    }

    public List<Libro> searchLibroByAuth(String auth) throws IOException {
        List<Libro> dataset = new JsonUtils().deserializeLibri();
        List<Libro> libriFound = new ArrayList<Libro>();    // To return
        for(Libro l : dataset) {
            if(l.getAutore().equals(auth)) libriFound.add(l);   // Se i titoli corrispondono aggiunge il Libro alla lista
        }
        return libriFound;
    }

    public Libro searchLibroByDataAuth(String auth, String data) throws IOException {
        List<Libro> dataset = new JsonUtils().deserializeLibri();
        for(Libro l : dataset) {
            if(l.getAutore().equals(auth) && l.getData().equals(data)) return l;
        }
        return null;
    }
}
