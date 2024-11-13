import java.util.Date;

public class Libro {
    // Campi
    private String titolo;
    private String autore;
    private String descrizione;
    private String categoria;
    private String pubblicatore;
    private String prezzo;
    private String data;

    // Metodi
    public Libro(String titolo, String autore, String descrizione,
                 String categoria, String pubblicatore, String prezzo, String data) {   // Costruttore
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.pubblicatore = pubblicatore;
        this.prezzo = prezzo;
        this.data = data;
    }

    public String getTitolo() {
        return this.titolo;
    }

    @Override
    public String toString() {
        return "Titolo: " + this.titolo + "\nAutore: " + this.autore + "\nDescrizione: " + this.descrizione
                + "\nCategoria: " + this.categoria + "\nPubblicatore: " +  this.pubblicatore
                + "\nPrezzo: " + this.prezzo + "\nData pubblicazione: " + this.data;
    }
}
