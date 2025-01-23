package org.BookRecommender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
* Rappresenta un oggetto Consiglio
*
* Questa classe utilizza le annotazioni di Jackson per la deserializzazione JSON.
* - {@code @JsonCreator} viene utilizzato per specificare il costruttore da usare nella deserializzazione. <br>
* - {@code @JsonProperty} viene utilizzato per mappare i nomi dei campi JSON agli attributi della classe.
*/
public class Consiglio {
    private LoggedUser publisher;
    private Libro referredLibro;
    private List<Libro> consigli;

    @JsonCreator
    public Consiglio(@JsonProperty("publisher") LoggedUser publisher,
                     @JsonProperty("referredLibro") Libro referredLibro,
                     @JsonProperty("consigli") List<Libro> consigli) {
        this.publisher = publisher;
        this.referredLibro = referredLibro;
        setConsigli(consigli);
    }

    public LoggedUser getPublisher() {
        return this.publisher;
    }

    public void setPublisher(LoggedUser publisher) {
        this.publisher = publisher;
    }

    public Libro getReferredLibro() {
        return this.referredLibro;
    }

    public void setReferredLibro(Libro referredLibro) {
        this.referredLibro = referredLibro;
    }

    public List<Libro> getConsigli() {
        return this.consigli;
    }
    /**
    * metodo setConsigli
    * @param List<Libro> consigli
    * Il metodo implementa una verifica (massimo 3 oggetti Libro nella lista consigli);
    * Se gli oggetti nella lista sono > 3, sublist (0, 3).
    */
    public void setConsigli(List<Libro> consigli) { 
        if (consigli.size() > 3) {   
            consigli = consigli.subList(0, 3);  
        }
        this.consigli = consigli;
    }

    /**
    * Override metodo toString
    */
    @Override
    public String toString() {
        String libriConsigliati = "";
        for(Libro libro : this.getConsigli()) {
            libriConsigliati += libro.getTitolo() + "\n";
        }
        return "Consigli suggeriti da " + this.publisher.getNome() + " " + this.publisher.getCognome() +
                "\nLibro principale: " + this.getReferredLibro().getTitolo() + "\nLibri consigliati:\n" + libriConsigliati;
    }
}
