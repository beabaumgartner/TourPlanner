package at.fhtw.utils;

import at.fhtw.exceptions.FailedToParseJsonFileException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonFileHandler {
    public static String readJsonStringFromFile(String filename) {
        try {
            File file = new File(filename);
            InputStream inputStream = new FileInputStream(file);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            return jsonString;
        } catch (IOException e) {
            // e.printStackTrace();
            throw new FailedToParseJsonFileException("JsonFileHandler.readJsonStringFromFile() - failed to read json file: " + filename);
        }
    }
    public static void saveJsonStringToFile(String filename, String string) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            Writer out = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            out.write(string);
            out.close();
        } catch (IOException e) {
            // e.printStackTrace();
            throw new FailedToParseJsonFileException("JsonFileHandler.saveJsonStringToFile() - failed to save to json file: " + filename);
        }
    }
}
