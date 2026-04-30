package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;

public class JsonReader {

    public static String getProduct(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> data = mapper.readValue(
                    new File(filePath), Map.class);

            return data.get("product");

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON", e);
        }
    }
}