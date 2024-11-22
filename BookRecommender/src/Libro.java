import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Libro {
    private String titolo;
    private String autore;
    private String descrizione;
    private String categoria;
    private String pubblicatore;
    private double prezzo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")    // Formatta la data una volta deserializzata
    private Date data;

    // Costruttore annotato con @JsonCreator
    @JsonCreator
    public Libro(   // Campi per la deserializzazione da Json a Libro
            @JsonProperty("titolo") String titolo,
            @JsonProperty("autore") String autore,
            @JsonProperty("descrizione") String descrizione,
            @JsonProperty("categoria") String categoria,
            @JsonProperty("pubblicatore") String pubblicatore,
            @JsonProperty("prezzo") double prezzo,
            @JsonProperty("data") Date data) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.pubblicatore = pubblicatore;
        this.prezzo = prezzo;
        this.data = data;
    }

    // Getters e setters
    public String getTitolo() { return this.titolo; }
    public String getAutore() { return this.autore; }
    public String getDescrizione() { return this.descrizione; }
    public String getCategoria() { return this.categoria; }
    public String getPubblicatore() { return this.pubblicatore; }
    public double getPrezzo() { return this.prezzo; }
    public Date getData() { return this.data; }

    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setAutore(String autore) { this.autore = autore; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPubblicatore(String pubblicatore) { this.pubblicatore = pubblicatore; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public void setData(Date data) { this.data = data; }

    @Override
    public String toString() {
        // Formattazione data toString
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(data);
        return "Titolo: " + titolo + "\nAutore: " + autore + "\nDescrizione: " + descrizione +
                "\nCategoria: " + categoria + "\nPubblicatore: " + pubblicatore + "\nPrezzo: " + prezzo +
                "\nData di pubblicazione: " + formattedDate;
    }
}
