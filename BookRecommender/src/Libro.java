import java.util.Date;

public class Libro {
    // Params
    private String titolo;
    private String autore;
    private String descrizione;
    private String categoria;
    private String pubblicatore;
    private int prezzo;
    private Date dataPubblicazione;

    // Constructor
    public Libro(String titolo, String autore, String descrizione, String categoria, String pubblicatore, int prezzo, Date dataPubblicazione) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.pubblicatore = pubblicatore;
        this.prezzo = prezzo;
        this.dataPubblicazione = dataPubblicazione;
    }

    // Getter e Setter
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getPubblicatore() { return pubblicatore; }
    public void setPubblicatore(String pubblicatore) { this. pubblicatore = pubblicatore; }
    public int getPrezzo() { return prezzo; }
    public void setPrezzo() { this.prezzo = prezzo; }
    public Date getDataPubblicazione() { return this.dataPubblicazione; }
    public void setDataPubblicazione(Date dataPubblicazione) { this.dataPubblicazione = dataPubblicazione; }

    // Methods
}
