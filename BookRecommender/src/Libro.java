import java.util.Date;
/**
 * Classe per rappresentare un libro
 */
public class Libro {
    private String titolo;
    private String autore;
    private String descrizione;
    private String categoria;
    private String pubblicatore;
    private double prezzo;
    private Date data;

    public Libro(String titolo, String autore, String descrizione,
                 String categoria, String pubblicatore, double prezzo, Date data) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.pubblicatore = pubblicatore;
        this.prezzo = prezzo;
        this.data = data;
    }

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
}