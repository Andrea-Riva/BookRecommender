import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Dichiara i campi per l'oggetto LoggedUser.
 * Fornisce metodi riservati ad utenti registrati (oggetti di tipo LoggedUser).
 * Ogni utente registrato è contraddistinto dai campi di tipo String id, nome, cognome, codiceFiscale
 * mail e password.
 */
public class LoggedUser extends User {
    private String id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String mail;
    private String password;

    public LoggedUser(String id, String nome, String cognome, String codiceFiscale, String mail, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.mail = mail;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Il metodo permette di salvare sul file "librerie.dati.csv"
     * Vengono inizializzati due ArrayList di tipo <Libro> constructCollectionLibri (utilizzato per definire la dimensione della Libreria
     * e per la costruzione della Libreria per il return) e <String> existingLibri (per la scrittura della libreria su file esterno).
     * Viene creato un oggetto di tipo Libro temporaneo (libroTemp) utilizzando il metodo di ricerca per titolo [cercaLibroByTitolo(titoloLibro)]
     * Se titoloLibro compare nel DataSet di libri disponibili (libri.dati.csv) viene aggiunto all'ArrayList<String> existingLibri e all'ArrayList
     * constructCollectionLibri;
     * Altrimenti se libroTem risulta null (Object.isNull) viene visualizzato ub messaggio che informa l'utente dell'esito negativo della ricerca
     * con conseguente annullamento dell'aggiunta nella libreria.
     *
     * @param nomeLibreria          nome della libreria e un ArrayList di String collectionTitoliLibri.
     * @param collectionTitoliLibri lista dei nomi dei libri che l'utente vuole inserire nella sua libreria
     */
    public Libreria registraLibreria(String nomeLibreria, ArrayList<String> collectionTitoliLibri) throws IOException {
        ArrayList<Libro> constructCollectionLibri = new ArrayList<>();
        ArrayList<String> existingLibri = new ArrayList<>();

        for (String titoloLibro : collectionTitoliLibri) {
            Libro libroTemp = new User().cercaLibroByTitolo(titoloLibro);
            if (Objects.isNull(libroTemp)) {
                System.out.println("Il libro " + titoloLibro + " non è stato trovato e non sarà aggiunto alla libreria");
            } else {
                existingLibri.add(titoloLibro);
                constructCollectionLibri.add(new User().cercaLibroByTitolo(titoloLibro));
            }
        }
        // Write sul file librerie.dati
/**
 * Viene salvata la directory di esportazione in un oggetto di tipo String (filePath) ed inizializzato il BufferedWriter.
 * Viene inizializzata una Stringa vuota titoliLibri, vengono scanditi gli oggetti String dell'ArrayList existingLibri e
 * concatenati alla stringa vuota creata in precedenza (totoloLibri), gli elementi vengono divisi ad ogni iterazione dalla
 * sequenza di caratteri "; ".
 * La stringa salvata nel file librerie.dati.csv è composta dal formato [id utente; nome libreria; dimensione della
 * collection di libri creata; titolo dei libri], per effettuare ciò viene creato un oggetto String toWrite dove vengono
 * concatenati: Id dell'oggetto utente che esegue il metodo (this.getId) + nomeLibreria (scelto arbitrariamente all'invocazione
 * del metodo + constructCollectionLibri.size() (dimensione della collection in cui vengono inseriti i libri) + titoloLibri.
 * Scrittura sul file tramite bw.write(toWtite) e chiusura del BufferedWriter con bw.close().
 */
        String filePath = "src/data/librerie.dati.csv";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        String titoloLibri = "";
        for (String titolo : existingLibri) {    // Memorizza in una stringa tutti i nomi dei libri esistenti inseriti dall'utente
            System.out.println(titolo);
            titoloLibri += titolo;
            titoloLibri += "; ";
        }
        titoloLibri = titoloLibri.substring(0, titoloLibri.length() - 2);   // Rimuove l'ultimo punto e virgola per la formattazione nel file csv
        String toWrite = this.getId() + "; " + nomeLibreria + "; " + titoloLibri + "\n";

        bw.write(toWrite);
        bw.close();

        // Costruzione Libreria
        return new Libreria(this, nomeLibreria, constructCollectionLibri);
    }

    /**
     * Il metodo scrive sul file ValutazioniLibri.dati.csv l'utente che ha pubblicato la recensione, il titolo del libro e tutte le valutazioni in questo formato:
     * ID Utente; titolo del libro; voto stile; voto contenuto;  voto gradevolezza; voto originalità; voto edizione; voto complessivo
     *
     * @param titoloLibro  il titolo del libro il quale si vuole valutare
     * @param stile        Valutazione sullo stile di scrittura del libro
     * @param contenuto    Valutazione sui contenuti del libro
     * @param gradevolezza Valutazione sul gradimento nella lettura del libro
     * @param originalità  Valutazione sulla originalità del libro
     * @param edizione     Valutazione sulla qualità dell’edizione del libro
     * @param votoFinale   Valutazione complessiva del libro
     */

    public void inserisciValutazioneLibro(String titoloLibro, String stile, String contenuto, String gradevolezza, String originalità, String edizione, String votoFinale, String note) throws IOException {
        if (Objects.isNull(new User().cercaLibroByTitolo(titoloLibro))) {    // Se il libro non esiste
            throw new IOException("Il titolo del libro non è stato trovato"); // Solleva eccezione
        }
        String filePath = "src/data/ValutazioniLibri.dati.csv";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));

        String toWrite = this.getId() + "; " + titoloLibro + "; " + stile + "; " + contenuto + "; " + gradevolezza +
                "; " + originalità + "; " + edizione + "; " + votoFinale + "; " + note + "\n";
        System.out.println(toWrite);

        bw.write(toWrite);  // Scrive la recensione sul file ValutazioneLibri.dati
        bw.close();
    }

    /**
     * Permette di inserire dei titoli suggeriti rispetto ad un libro principale; i dati vengono memorizzati in ConsigliLibri.dati.csv nel seguente formato:
     * userID; Libro principale; {Titolo suggerito}I; {Titolo suggerito}II; ... ; {Titolo suggerito}N
     * @param mainLib String che rapppresenta il titolo del libro principale
     * @param libriSuggeriti Collection di String che rappresentano l'insieme dei titoli suggeriti dall'utente
     * @throws IOException  Eccezione sollevata quando il libro principale non esiste
     */

    public void inserisciSuggerimentoLibro(String mainLib, ArrayList<String> libriSuggeriti) throws IOException {
        if (Objects.isNull(new User().cercaLibroByTitolo(mainLib))) {    // Se il libro non esiste
            throw new IOException("Il titolo del libro non è stato trovato"); // Solleva eccezione
        }
        String filePath = "src/data/ConsigliLibri.dati.csv";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        String titoliSuggeriti = "";
        for(String titolo : libriSuggeriti) {
            if (Objects.isNull(new User().cercaLibroByTitolo(mainLib))) {    // Se il libro non esiste
                System.out.println("Il libro " + titolo + " non è stato trovato e non sarà aggiunto ai suggerimenti");
            } else {    // Se il libro esiste
                titoliSuggeriti += titolo + "; ";   // Aggiunge il titolo alla stringa
            }
        }
        titoliSuggeriti = titoliSuggeriti.substring(0, titoliSuggeriti.length() - 2);   // Formattazione stringa
        String toWrite = this.getId() + "; " + mainLib + "; " + titoliSuggeriti + "\n";
        bw.write(toWrite);
        bw.close();
    }

    /**
     * La classe mette a disposizone un metodo toString che descrive l'oggetto nell'ordine Id, nome e cognome, mail e codice fiscale.
     */

    @Override
    public String toString() {
        return "ID: " + this.id + "\nNome e cognome: " + this.nome + " " + this.cognome +
                "\nMail: " + this.mail + "\nCodice Fiscale: " + this.codiceFiscale;  // Non eseguiamo il toString della password per ovvi motivi di sicurezza
    }
}