import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonUtils {
    /**
     * Permette di deserializzare tutti i dati presenti nel dataset in JSON in una lista di libri
     * @return una collection con tutti i libri presenti nel dataset
     * @throws IOException
     */
    public List<Libro> getLibri() throws IOException {
        String jsonLibri =
                FileUtils.readFileToString(new File("src/data/libri.json"), StandardCharsets.UTF_8); // Leggi il file JSON come stringa

        // Crea una nuova istanza di ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Imposta il formato della data, compatibile con il formato "yyyy-MM-dd"
        mapper.setDateFormat(new StdDateFormat()); // Usa il formato di data standard

        // Configura per ignorare le proprietà sconosciute
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Usa il TypeReference per mappare il JSON alla lista di libri
        TypeReference<List<Libro>> libroTypeReference = new TypeReference<List<Libro>>() {};

        // Deserializza il JSON nella lista di oggetti Libro
        return mapper.readValue(jsonLibri, libroTypeReference);
    }

    /**
     * Restituisce il root node degli utenti
     * @return JSON Root node
     * @throws IOException
     */
    public JsonNode getUtentiAsJsonNode() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File utentiJson = new File("src/data/utenti.json");
        return mapper.readTree(utentiJson); // Struttura del JSON
    }

    /**
     * Partendo da un oggetto LoggedUser, crea un nodo Json da esso
     * @param user L'utente registrato
     * @return root JsonNode
     */
    public JsonNode createUserNode(LoggedUser user) {  // Crea un nodo per un oggetto LoggedUser
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode()
                .put("id", user.getId())
                .put("nome", user.getNome())
                .put("cognome", user.getCognome())
                .put("codiceFiscale", user.getCodiceFiscale())
                .put("mail", user.getMail())
                .put("password", user.getPassword());
    }

    /**
     * Scrive in formato JSON tutti i nodi che hanno come root node il parametro del metodo sul file degli utenti
     * @param root Il root node degli utenti
     * @throws IOException
     */
    public void writeUtentiNodes(JsonNode root) throws IOException {    // Scrive il nuovo nodo LoggedUser
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/utenti.json"), root);
    }

    /**
     * Genera un Id univoco che è costruito prendendo l'Id massimo (dunque l'ultimo Id creato) e gli aggiunge 1
     * @return Un Id univoco
     * @throws IOException
     */
    public int getUniqueId() throws IOException {
        JsonNode root = getUtentiAsJsonNode();
        if(!root.isArray() || root.isEmpty()) { // Se il file è vuoto, gli ID partiranno da 1
            return 1;
        }
        // Se il file contiene già utenti, trova l'ID massimo esistente
        int maxId = 0;
        for(JsonNode userNode : root) {
            int newId = userNode.get("id").asInt(); // Id dell'utente corrente
            if(newId > maxId) maxId = newId;    // Confronta Id
        }
        return maxId + 1;   // Restituisce un Id univoco
    }
}