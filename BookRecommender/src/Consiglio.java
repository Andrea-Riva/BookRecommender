import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    public void setConsigli(List<Libro> consigli) {  // Ci possono essere massimo 3 libri consigliati
        if (consigli.size() > 3) {   // Se i libri consigliati sono troppi
            consigli = consigli.subList(0, 3);  // Mantiene solo i primi 3 libri inseriti
        }
        this.consigli = consigli;
    }

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
