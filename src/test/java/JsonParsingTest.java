import model.AddressInner;
import model.JsonFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonParsingTest {

        private final ClassLoader cl = FileParsingTest.class.getClassLoader();
        JsonFile jsonFile = new JsonFile();

        @Test
        @DisplayName("Файл jsonFile.json корректно читается")
        void JsonParsingFileTest() throws Exception {
            boolean found = false;
            try (InputStream is = cl.getResourceAsStream("jsonFile.json")) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonFile jsonFile = objectMapper.readValue(is, JsonFile.class);

                assertEquals("Виолетта", jsonFile.getUserName()); //string
                assertEquals(12345, jsonFile.getUserId());  //int
                assertEquals(true, jsonFile.getIsActive()); //Boolean
                assertEquals(List.of("admin", "editor"), jsonFile.getRoles()); //массив
                assertEquals("Ташкент", jsonFile.getAddress().getCity());  //вложенность
                assertEquals("100000", jsonFile.getAddress().getZipCode());  //вложенность
                assertEquals(null, jsonFile.getLastLogin());
                found = true;

            }

        }


}

