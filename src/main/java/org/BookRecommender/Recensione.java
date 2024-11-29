package org.BookRecommender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recensione {
    private LoggedUser publisher;
    private Libro referredLibro;
    private int stile;
    private int contenuto;
    private int gradevolezza;
    private int originalità;
    private int edizione;
    private double votoFinale;

    @JsonCreator
    public Recensione(@JsonProperty("publisher") LoggedUser publisher,
                      @JsonProperty("referredLibro") Libro referredLibro,
                      @JsonProperty("stile") int stile,
                      @JsonProperty("contenuto") int contenuto,
                      @JsonProperty("gradevolezza") int gradevolezza,
                      @JsonProperty("originalità") int originalità,
                      @JsonProperty("edizione") int edizione) {    // Costruttore
        this.publisher = publisher;
        this.referredLibro = referredLibro;
        setStile(stile);
        setContenuto(contenuto);
        setGradevolezza(gradevolezza);
        setOriginalità(originalità);
        setEdizione(edizione);
        this.votoFinale = (double) (this.stile + this.contenuto + this.gradevolezza + this.originalità + this.edizione) / 5;    // Media dei voti
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

    public int getStile() {
        return this.stile;
    }

    public void setStile(int stile) {
        if (stile < 1) {
            stile = 1;
        }
        if (stile > 5) {
            stile = 5;
        }
        this.stile = stile;
    }

    public int getContenuto() {
        return this.contenuto;
    }

    public void setContenuto(int contenuto) {
        if (contenuto < 1) {
            contenuto = 1;  // Imposto il valore minimo
        }
        if (contenuto > 5) {
            contenuto = 5;  // Imposto il valore massimo
        }
        this.contenuto = contenuto;
    }

    public int getGradevolezza() {
        return this.gradevolezza;
    }

    public void setGradevolezza(int gradevolezza) {
        if (gradevolezza < 1) {
            gradevolezza = 1;  // Imposto il valore minimo
        }
        if (gradevolezza > 5) {
            gradevolezza = 5;  // Imposto il valore massimo
        }
        this.gradevolezza = gradevolezza;
    }

    public int getOriginalità() {
        return this.originalità;
    }

    public void setOriginalità(int originalità) {
        if (originalità < 1) {
            originalità = 1;  // Imposto il valore minimo
        }
        if (originalità > 5) {
            originalità = 5;  // Imposto il valore massimo
        }
        this.originalità = originalità;
    }

    public int getEdizione() {
        return this.edizione;
    }

    public void setEdizione(int edizione) {
        if (edizione < 1) {
            edizione = 1;  // Imposto il valore minimo
        }
        if (edizione > 5) {
            edizione = 5;  // Imposto il valore massimo
        }
        this.edizione = edizione;
    }

    public double getVotoFinale() {
        return this.votoFinale;
    }

    @Override
    public String toString() {
        return "Recensione pubblicata da: " + this.publisher.getNome() + " " + this.publisher.getCognome() + " per il libro " + this.referredLibro.getTitolo() +
                "\nStile: " + this.stile + ", Contenuto: " + this.contenuto + ", Gradevolezza: " + this.gradevolezza +
                ", Originalità: " + this.originalità + "\nVoto complessivo: " + this.votoFinale;
    }
}