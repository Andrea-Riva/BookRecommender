package org.BookRecommender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Classe per utility JSON.
*/
public class JsonUtils {
    private ObjectMapper mapper;

    public JsonUtils() {
        this.mapper = new ObjectMapper();
        mapper.setDateFormat(new StdDateFormat());  
    }

    /**
     * Restituisce tutti i libri presenti nel dataset JSON.
     *
     * @return una collection di libri deserializzati dal file JSON.
     */
    public List<Libro> getLibri() throws IOException {
        JsonNode libriNode = readFromFile("src/main/java/org/BookRecommender/data/libri.json");
        List<Libro> libri = new ArrayList<>();
        for (JsonNode libroNode : libriNode) {
            Libro libro = new Libro(
                    libroNode.get("titolo").asText(),
                    libroNode.get("autore").asText(),
                    libroNode.get("descrizione").asText(),
                    libroNode.get("categoria").asText(),
                    libroNode.get("pubblicatore").asText(),
                    libroNode.get("prezzo").asDouble(),
                    libroNode.get("data").asText()
            );
            libri.add(libro);
        }
        return libri;
    }

    /**
     * Restituisce tutti gli utenti presenti nel dataset di JSON.
     *
     * @return una collection di utenti deserializzati dal file JSON.
     */
    public List<LoggedUser> getUtenti() throws IOException {
        JsonNode utentiNode = readFromFile("src/main/java/org/BookRecommender/data/utenti.json");
        List<LoggedUser> utenti = new ArrayList<>();
        for (JsonNode userNode : utentiNode) {
            LoggedUser user = new LoggedUser(
                    userNode.get("id").asInt(),
                    userNode.get("nome").asText(),
                    userNode.get("cognome").asText(),
                    userNode.get("codiceFiscale").asText(),
                    userNode.get("mail").asText(),
                    userNode.get("password").asText()
            );
            utenti.add(user);
        }
        return utenti;
    }

     /**
     * Restituisce tutti gli oggetti Libro presenti nel dataset di JSON
     *
     * @return una collection di Libri deserializzati dal file JSON
     */
    public List<Libreria> getLibrerie() throws IOException {
        String filePath = "src/main/java/org/BookRecommender/data/librerie.json";
        File file = new File(filePath);
        List<Libreria> librerie = new ArrayList<>();    
        if (file.exists()) {    
            librerie = mapper.readValue(file, new TypeReference<List<Libreria>>() {
            });
        }

        return librerie;
    }

    /**
     * Restituisce tutte le Recensioni presenti nel dataset di JSON.
     *
     * @return una collection di recensioni deserializzati dal file JSON.
     */
    public List<Recensione> getRecensioni() throws IOException {
        String filePath = "src/main/java/org/BookRecommender/data/recensioni.json";
        File file = new File(filePath);
        List<Recensione> reviews = new ArrayList<>();
        if(file.exists()) {
            reviews = mapper.readValue(file, new TypeReference<List<Recensione>>() {
            });
        }
        return reviews;
    }

    /**
     * Restituisce tutti i Consigli presenti nel dataset di JSON.
     *
     * @return una collection di Consigli deserializzati dal file JSON.
     */
    public List<Consiglio> getConsigli() throws IOException {
        String filePath = "src/main/java/org/BookRecommender/data/consigli.json";
        File file = new File(filePath);
        List<Consiglio> consigli = new ArrayList<>();
        if(file.exists()) { 
            consigli = mapper.readValue(file, new TypeReference<List<Consiglio>>() {});
        }

        return consigli;
    }

    /**
    * Metodo per creare un nodo Json per User.
    *
    * @param oggetto di tipo LoggedUser (user).
    * @return oggetto ObjectNode che rappresenta l'oggetto LoggedUser.
    */
    public ObjectNode createUserNode(LoggedUser user) {
        ObjectNode userNode = mapper.createObjectNode();
        userNode.put("id", user.getId());
        userNode.put("nome", user.getNome());
        userNode.put("cognome", user.getCognome());
        userNode.put("codiceFiscale", user.getCodiceFiscale());
        userNode.put("mail", user.getMail());
        userNode.put("password", user.getPassword());

        return userNode;
    }

    /**
    * Metodo per scrivere il nodo Json su file .
    *
    * @param JsonNode, filePath (String).
    */ 
    public void writeToFile(JsonNode node, String filePath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), node);
    }

     /**
    * Metodo per leggere un nodo Json da file.
    *
    * @param filePath (String).
    */
    public JsonNode readFromFile(String filePath) throws IOException {
        return mapper.readTree(new File(filePath));
    }
    
    /**
    * metodo per scrivere i nodi degli utenti su file 
    *
    * File path: {@code rc/main/java/org/BookRecommender/data/utenti.json}
    * @param JsonNode
    */
    public void writeUtentiNodes(JsonNode root) throws IOException {
        writeToFile(root, "src/main/java/org/BookRecommender/data/utenti.json");
    }

    /**
    * Metodo per scrivere i nodi degli oggetti Libreria su file.
    *
    * file path: {@code src/main/java/org/BookRecommender/data/librerie.json}.
    * @param JsonNode.
    */
    public void writeLibrerieNodes(JsonNode root) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/java/org/BookRecommender/data/librerie.json"), root);
    }

    /**
    * Metodo per generare un'id univoco per utente.
    *
    * - se il JsonNode root non è un'array o il file è vuoto @return 1;
    * - altrimenti itera root cercando il maxId e @return maxId + 1.
   
    */
    public int getUniqueId() throws IOException {
        JsonNode root = readFromFile("src/main/java/org/BookRecommender/data/utenti.json");
        if (!root.isArray() || root.isEmpty()) {
            return 1; 
        }

        int maxId = 0;
        for (JsonNode userNode : root) {
            int userId = userNode.get("id").asInt();
            if (userId > maxId) {
                maxId = userId;
            }
        }
        return maxId + 1; 
    }

    /**
    * Metodo per ottenere gli utenti come JsonNode, utile se si vuole effettuare una manipolazione diretta dei nodi.
    *
    * @return JsonNode from {@code src/main/java/org/BookRecommender/data/utenti.json}.
    */
    public JsonNode getUtentiAsJsonNode() throws IOException {
        return readFromFile("src/main/java/org/BookRecommender/data/utenti.json");
    }

    /**
    * Metodo per verificare se un oggetto Libro è presente nelle librerie di un certo utente.
    * Itera le librerie appartenenti al proprietario cercato aggiornando il valore a true in caso di successo.
    *
    * @param LoggedUser (proprietario della libreria), Steing (titolo del libro).
    * @return true se presente, false altrimenti.
    */
    public boolean isPresente(LoggedUser proprietario, String titolo) throws IOException {  
        boolean presente = false;
        List<Libreria> userLibs = new JsonUtils().getLibrerie();
        for (Libreria lib : userLibs) {  
            if (lib.getProprietario().getId() == proprietario.getId()) { 
                List<Libro> libriPresenti = lib.getLibri(); 
                for(Libro l : libriPresenti) {  
                    if (l.getTitolo().equals(titolo)) {
                        presente = true;
                        break;
                    }
                }
            }
        }
        return presente;
    }
}
