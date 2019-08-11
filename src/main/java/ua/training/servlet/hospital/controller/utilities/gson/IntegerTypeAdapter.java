package ua.training.servlet.hospital.controller.utilities.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IntegerTypeAdapter extends TypeAdapter<Integer> {

    @Override
    public Integer read(JsonReader jsonReader) throws IOException {
        if(jsonReader.peek() == JsonToken.NULL){
            jsonReader.nextNull();
            return null;
        }
        String stringValue = jsonReader.nextString();
        try{
            return Integer.valueOf(stringValue);
        }catch(NumberFormatException e){
            return null;
        }
    }

    @Override
    public void write(JsonWriter jsonWriter, Integer value) throws IOException {
        if (value == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(value);
    }
}
