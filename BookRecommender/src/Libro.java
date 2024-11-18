import java.util.Date;
/**
* La classe permette di creare oggetti di tipo Libro necessari per i metodi di ricerca (User class) e creare Librerie di essi (Libreria class).
* Un oggetto di tipo Libro è contraddistinto dai campi: titolo, autore, descrizione, categoria, pubblicazione, prezzo e data.
* Il programma non permette la creazione degli oggetti da parte degli utenti ma solo di creare oggetti di tipo Libro utilizzando un file esterno
* libri.dati.csv, la creazione quindi è possibile utilizzando metodi di lettura su file esterni (BufferedReader) mantenendo l'ordine degli attributi
* dichiarati dalla classe Libro.
* La classe mette a disposizione un metodo toString personalizzato dal programmatore.
*/
public class Libro {
    private String titolo;
    private String autore;
    private String descrizione;
    private String categoria;
    private String pubblicatore;
    private String prezzo;
    private String data;

    public Libro(String titolo, String autore, String descrizione,
                 String categoria, String pubblicatore, String prezzo, String data) {   
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

    
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPubblicatore(String pubblicatore) {
        this.pubblicatore = pubblicatore;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public void setData(String data) {
        this.data = data;
    }
/**
* La visualizzazione della descrizione tramite il metodo toString (personalizzato) avviene mostrando gli attributi
* dell'oggetto su righe differenti (/n per ogni attributo).
*/
    @Override
    public String toString() {
        return "Titolo: " + this.titolo + "\nAutore: " + this.autore + "\nDescrizione: " + this.descrizione
                + "\nCategoria: " + this.categoria + "\nPubblicatore: " +  this.pubblicatore
                + "\nPrezzo: " + this.prezzo + "\nData pubblicazione: " + this.data;
    }
}
