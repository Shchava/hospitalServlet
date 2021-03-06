package ua.training.servlet.hospital.controller.utilities.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GsonFactory {
    private static GsonBuilder builder = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class,new LocalDateDeserializer())
            .registerTypeAdapter(LocalDateTime.class,new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class,new LocalDateTimeDeserializer())
            .registerTypeAdapter(int.class, new IntegerTypeAdapter())
            .registerTypeAdapter(Integer.class, new IntegerTypeAdapter());

    public static Gson create(){
        return builder.create();
    }
}
