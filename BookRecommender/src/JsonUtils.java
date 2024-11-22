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
    public List<Libro> deserializeLibri() throws IOException {
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
}