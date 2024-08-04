package utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * serialization получаем toString
 * deserealization элемент как строка и парсит
 */

public class LocalDateSerializer implements JsonSerializer<LocalDate> {
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context){
        return new JsonPrimitive(date.toString());
    }

}
