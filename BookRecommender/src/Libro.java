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

    // Getter
    public String getTitolo() {
        return this.titolo;
    }
    public String getAutore() {
        return this.autore;
    }
    public String getDescrizione() {
        return this.descrizione;
    }
    public String getCategoria() {
        return this.categoria;
    }
    public String getPubblicatore() {
        return this.pubblicatore;
    }
    public String getPrezzo() {
        return this.prezzo;
    }
    public String getData() {
        return this.data;
    }

    // Setter
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    // Setter per la descrizione
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // Setter per la categoria
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Setter per il pubblicatore
    public void setPubblicatore(String pubblicatore) {
        this.pubblicatore = pubblicatore;
    }

    // Setter per il prezzo
    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    // Setter per la data
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Titolo: " + this.titolo + "\nAutore: " + this.autore + "\nDescrizione: " + this.descrizione
                + "\nCategoria: " + this.categoria + "\nPubblicatore: " +  this.pubblicatore
                + "\nPrezzo: " + this.prezzo + "\nData pubblicazione: " + this.data;
    }
}
