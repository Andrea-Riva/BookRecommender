import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private ObjectMapper mapper;

    public JsonUtils() {
        this.mapper = new ObjectMapper();
        mapper.setDateFormat(new StdDateFormat());  // Formato di data standard
    }

    // Leggere tutti i libri dal file JSON
    public List<Libro> getLibri() throws IOException {
        JsonNode libriNode = readFromFile("src/data/libri.json");
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

    // Leggere tutti gli utenti dal file JSON
    public List<LoggedUser> getUtenti() throws IOException {
        JsonNode utentiNode = readFromFile("src/data/utenti.json");
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

    // Leggere tutte le librerie dal file JSON
    public List<Libreria> getLibrerie() throws IOException {
        String filePath = "src/data/librerie.json";
        File file = new File(filePath);
        List<Libreria> librerie = new ArrayList<>();    // Librerie to return
        if (file.exists()) {    // Lettura di tutte le librerie
            librerie = mapper.readValue(file, new TypeReference<List<Libreria>>() {
            });
        }

        return librerie;
    }

    // Leggere tutte le recensioni dal file JSON
    public List<Recensione> getRecensioni() throws IOException {
        String filePath = "src/data/recensioni.json";
        File file = new File(filePath);
        List<Recensione> reviews = new ArrayList<>();
        if(file.exists()) { // Lettura di tutte le recensioni
            reviews = mapper.readValue(file, new TypeReference<List<Recensione>>() {
            });
        }
        return reviews;
    }

    // Creare un nodo JSON per un utente
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

    // Scrivere un nodo JSON su file
    public void writeToFile(JsonNode node, String filePath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), node);
    }

    // Leggere un file JSON in un JsonNode
    public JsonNode readFromFile(String filePath) throws IOException {
        return mapper.readTree(new File(filePath));
    }

    // Scrivere i nodi degli utenti su file
    public void writeUtentiNodes(JsonNode root) throws IOException {
        writeToFile(root, "src/data/utenti.json");
    }

    // Scrive i nodi delle librerie su file
    public void writeLibrerieNodes(JsonNode root) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/librerie.json"), root);
    }

    // Generare un Id univoco per gli utenti
    public int getUniqueId() throws IOException {
        JsonNode root = readFromFile("src/data/utenti.json");
        if (!root.isArray() || root.isEmpty()) {
            return 1; // Se il file è vuoto, partiamo con Id = 1
        }

        int maxId = 0;
        for (JsonNode userNode : root) {
            int userId = userNode.get("id").asInt();
            if (userId > maxId) {
                maxId = userId;
            }
        }
        return maxId + 1; // Restituisce un nuovo Id univoco
    }

    // Metodo per ottenere gli utenti come JsonNode (se si vuole manipolare i nodi direttamente)
    public JsonNode getUtentiAsJsonNode() throws IOException {
        return readFromFile("src/data/utenti.json");
    }
}
