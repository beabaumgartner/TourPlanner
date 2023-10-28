package at.fhtw.tourplanner.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ApplicationFileParser {
    private static final Logger logger = LogManager.getLogger(ApplicationFileParser.class);
    public static String parseApiKey() {
        try {
            YAMLFactory yamlFactory = new YAMLFactory();
            ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
            File yamlFile = new File("src/main/resources/application.yml");
            JsonNode rootNode = objectMapper.readTree(yamlFile);
            return rootNode.path("api-key").asText();
        } catch (Exception e) {
            logger.error("ApplicationFileParser.parseApiKey() - " + e.getMessage());
        }
        return null;
    }
}
