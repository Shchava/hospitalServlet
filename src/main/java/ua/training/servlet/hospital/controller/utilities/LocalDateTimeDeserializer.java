package ua.training.servlet.hospital.controller.utilities;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if(jsonElement.isJsonNull() || jsonElement.getAsString().isEmpty()){
            return null;
        }
        return LocalDateTime.parse(jsonElement.toString().replaceAll("\"",""),DateTimeFormatter.ISO_DATE_TIME);
    }
}
